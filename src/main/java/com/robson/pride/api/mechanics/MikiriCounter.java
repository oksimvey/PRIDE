package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.entity.spells.ExtendedWitherSkull;
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrb;
import io.redspace.ironsspellbooks.entity.spells.ball_lightning.BallLightning;
import io.redspace.ironsspellbooks.entity.spells.creeper_head.CreeperHeadProjectile;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.entity.spells.firebolt.FireboltProjectile;
import io.redspace.ironsspellbooks.entity.spells.guiding_bolt.GuidingBoltProjectile;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_missile.MagicMissileProjectile;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireBomb;
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrow;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostVisualEntity;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity;
import io.redspace.ironsspellbooks.spells.fire.HeatSurgeSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.Armatures;

import java.util.concurrent.TimeUnit;

public class MikiriCounter {

    public static void setMikiri(Entity ent, String MikiriType,int delay, int window){
        TimerUtil.schedule(()->{
            if(MikiriType.equals("Dodge")){
                ent.getPersistentData().putBoolean("mikiri_dodge", true);
            }
            if(MikiriType.equals("Jump")){
                ent.getPersistentData().putBoolean("mikiri_sweep", true);
            }
        }, delay, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->{
            if(MikiriType.equals("Dodge")){
                ent.getPersistentData().putBoolean("mikiri_dodge", false);
            }
            if(MikiriType.equals("Jump")){
                ent.getPersistentData().putBoolean("mikiri_sweep", false);
            }
        }, window, TimeUnit.MILLISECONDS);
    }

    public static void onPierceMikiri(Entity ent, Entity ddmgent, String pierce_type) {
        String animation;
        if (ent instanceof Player) {
            CameraUtils.lockCamera(Minecraft.getInstance().player);
        }
        if (ddmgent instanceof Player){
            animation = "pride:biped/skill/perilous_";
        }
        else animation = "pride:biped/skill/mob_perilous_";
        AnimUtils.cancelMotion(ddmgent);
        AnimUtils.rotateToEntity(ddmgent, ent);
        TimerUtil.schedule(() -> AnimUtils.rotateToEntity(ddmgent, ent), 125, TimeUnit.MILLISECONDS);
        TeleportUtils.teleportEntityToEntityJoint(ent, ddmgent, Armatures.BIPED.toolR, 0, 0, ddmgent.getBbHeight() * 0.25);
        AnimUtils.playAnimByString(ent, "pride:biped/skill/mikiri_step", 0);
        TimerUtil.schedule(()->ent.setPos(ent.getX(), ddmgent.getY(), ent.getZ()), 15, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> AnimUtils.playAnimByString(ddmgent, animation + pierce_type, 0), 50, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> {
            StaminaUtils.consumeStamina(ddmgent, 9);
            PlaySoundUtils.playSoundByString(ent, "pride:shieldparry", 0.5f, 1f);
        }, 150, TimeUnit.MILLISECONDS);
    }

    public static void onSweepMikiri(Entity ent, Entity ddmgent){
        ent.setInvulnerable(true);
        AnimUtils.playAnimByString(ent, "pride:biped/skill/mikiri_jump", 0f);
        TimerUtil.schedule(()-> StaminaUtils.consumeStamina(ddmgent, 6), 500, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->ent.setInvulnerable(false), 1000, TimeUnit.MILLISECONDS);
    }

    public static void onArrowMikiri(Entity ent, Projectile projectile, LivingAttackEvent event){
    event.setCanceled(true);
    projectile.remove(Entity.RemovalReason.DISCARDED);
    AnimUtils.playAnim(ent, AnimationsRegister.PROJECTILE_COUNTER, 0);
    Entity projectile1 = projectile.getType().create(ent.level());
        ent.level().addFreshEntity(projectile1);
        projectile1.setPos(ent.getLookAngle().add(0, ent.getBbHeight(), 0).add(ent.position()));
        projectile1.noPhysics = true;
        projectile1.verticalCollisionBelow = false;
        teleportProjectileToEntityHand(ent, projectile1);
        TimerUtil.schedule(()-> {
            projectile1.remove(Entity.RemovalReason.DISCARDED);
    ProjectileUtil.shootProjectileFromEnt((Projectile) projectile.getType().create(ent.level()),ent, (float) MathUtils.getTotalDistance(projectile.getDeltaMovement().x, projectile.getDeltaMovement().y, projectile.getDeltaMovement().z));
    }, 1000, TimeUnit.MILLISECONDS);
    }

    public static void onSpellMikiri(SpellDamageEvent event){
       if (event != null && event.getSpellDamageSource().spell() != null && event.getEntity() != null){
           event.getSpellDamageSource().getDirectEntity().remove(Entity.RemovalReason.DISCARDED);
           AnimUtils.playAnim(event.getEntity(), AnimationsRegister.PROJECTILE_COUNTER, 0);
          TimerUtil.schedule(()-> SpellUtils.castSpell(event.getEntity(), event.getSpellDamageSource().spell(), 3, 0), 500,TimeUnit.MILLISECONDS);
           event.setCanceled(true);
       }
    }

    public static void teleportProjectileToEntityHand(Entity owner, Entity projectile){
        if (owner != null && projectile != null){
            Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, owner, Armatures.BIPED.toolL);
            if (vec3 != null){
                projectile.teleportTo(vec3.x, vec3.y, vec3.z);
                TimerUtil.schedule(()->teleportProjectileToEntityHand(owner, projectile), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static boolean isDodgeCounterableSpell(Entity spell){
        return spell instanceof ExtendedWitherSkull ||
                spell instanceof MagicArrowProjectile ||
                spell instanceof MagicMissileProjectile ||
                spell instanceof CreeperHeadProjectile ||
                spell instanceof MagicFireball ||
                spell instanceof FireboltProjectile ||
                spell instanceof FireBomb ||
                spell instanceof GuidingBoltProjectile ||
                spell instanceof WispEntity ||
                spell instanceof IcicleProjectile ||
                spell instanceof RayOfFrostVisualEntity ||
                spell instanceof LightningLanceProjectile ||
                spell instanceof BallLightning ||
                spell instanceof AcidOrb ||
                spell instanceof PoisonArrow;
    }

    public static boolean isJumpCounterableSpell(AbstractSpell spell){
        return spell instanceof HeatSurgeSpell;
    }
}
