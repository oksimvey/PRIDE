package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.entities.special.Shooter;
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
import io.redspace.ironsspellbooks.spells.ice.FrostwaveSpell;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.gameasset.Armatures;

import java.util.concurrent.TimeUnit;

public class MikiriCounter {

    public static void setMikiri(Entity ent, String MikiriType, int delay, int window) {
        TimerUtil.schedule(() -> {
            if (MikiriType.equals("Dodge")) {
                ent.getPersistentData().putBoolean("mikiri_dodge", true);
            }
            if (MikiriType.equals("Jump")) {
                ent.getPersistentData().putBoolean("mikiri_sweep", true);
            }
        }, delay, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> {
            if (MikiriType.equals("Dodge")) {
                ent.getPersistentData().putBoolean("mikiri_dodge", false);
            }
            if (MikiriType.equals("Jump")) {
                ent.getPersistentData().putBoolean("mikiri_sweep", false);
            }
        }, window, TimeUnit.MILLISECONDS);
    }

    public static void onPierceMikiri(Entity ent, Entity ddmgent, String pierce_type) {
        String animation;
        if (ent instanceof Player) {
            CameraUtils.lockCamera(Minecraft.getInstance().player);
        }
        if (ddmgent instanceof Player) {
            animation = "pride:biped/skill/perilous_";
        } else animation = "pride:biped/skill/mob_perilous_";
        AnimUtils.cancelMotion(ddmgent);
        AnimUtils.rotateToEntity(ddmgent, ent);
        TimerUtil.schedule(() -> AnimUtils.rotateToEntity(ddmgent, ent), 125, TimeUnit.MILLISECONDS);
        TeleportUtils.teleportEntityToEntityJoint(ent, ddmgent, Armatures.BIPED.toolR, 0, 0, ddmgent.getBbHeight() * 0.25);
        AnimUtils.playAnimByString(ent, "pride:biped/skill/mikiri_step", 0);
        TimerUtil.schedule(() -> ent.setPos(ent.getX(), ddmgent.getY(), ent.getZ()), 15, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> AnimUtils.playAnimByString(ddmgent, animation + pierce_type, 0), 50, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> {
            StaminaUtils.consumeStamina(ddmgent, 9);
            PlaySoundUtils.playSoundByString(ent, "pride:shieldparry", 0.5f, 1f);
        }, 150, TimeUnit.MILLISECONDS);
    }

    public static void onSweepMikiri(Entity ent, Entity ddmgent) {
        ent.setInvulnerable(true);
        AnimUtils.playAnimByString(ent, "pride:biped/skill/mikiri_jump", 0f);
        TimerUtil.schedule(() -> StaminaUtils.consumeStamina(ddmgent, 6), 500, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> ent.setInvulnerable(false), 1000, TimeUnit.MILLISECONDS);
    }

    public static void onArrowMikiri(Entity ent, Projectile projectile, LivingAttackEvent event) {
        event.setCanceled(true);
        projectile.teleportTo(0, 999999999, 0);
        Entity target = TargetUtil.getTarget(ent);
        if (target != null) {
            Shooter shooter = Shooter.summonShooter(ent, target, false);
            TimerUtil.schedule(() -> ProjectileUtil.shootProjectileFromShooter(shooter, projectile, ent, 3, false), 450, TimeUnit.MILLISECONDS);
        } else
            TimerUtil.schedule(() -> ProjectileUtil.shootProjectileFromEnt(projectile, ent, 3), 450, TimeUnit.MILLISECONDS);
        AnimUtils.playAnim(ent, AnimationsRegister.PROJECTILE_COUNTER, 0);
    }

    public static void onSpellMikiri(SpellDamageEvent event, AbstractSpell spell) {
        if (event != null && event.getSpellDamageSource().spell() != null && event.getEntity() != null) {
            event.setCanceled(true);
            Entity entity = event.getSpellDamageSource().getDirectEntity().getType().create(event.getEntity().level());
            event.getSpellDamageSource().getDirectEntity().remove(Entity.RemovalReason.DISCARDED);
            event.getEntity().level().addFreshEntity(entity);
            Vec3 delta = MathUtils.rotate2DVector(event.getEntity().getLookAngle().scale(0.5), 90);
            teleportProjectileToEntityHand(event.getEntity(), entity, delta);
            TimerUtil.schedule(() -> {
                entity.remove(Entity.RemovalReason.DISCARDED);
                SpellUtils.castSpell(event.getEntity(), spell, 3);
            }, 450, TimeUnit.MILLISECONDS);
            AnimUtils.playAnim(event.getEntity(), AnimationsRegister.PROJECTILE_COUNTER, 0);
        }
    }

    public static void teleportProjectileToEntityHand(Entity owner, Entity projectile, Vec3 delta) {
        if (owner != null && projectile != null && delta != null) {
            projectile.setDeltaMovement(delta.x, 0.1, delta.z);
            Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, owner, Armatures.BIPED.handL);
            if (vec3 != null) {
                projectile.moveTo(vec3);
                TimerUtil.schedule(() -> teleportProjectileToEntityHand(owner, projectile, delta), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static boolean canMobMikiri(Entity ent, Entity ddmgent, String periloustype) {
        if (ent != null && ddmgent != null) {
            if (ent instanceof Player) {
                return switch (periloustype) {
                    case "Dodge" -> ent.getPersistentData().getBoolean("mikiri_dodge");
                    case "Jump" -> ent.getPersistentData().getBoolean("mikiri_sweep");
                    default -> false;
                };
            }
            return ProgressionUtils.getTotalLevel(ent) >= MathUtils.getRandomInt(ProgressionUtils.getTotalLevel(ddmgent));
        }
        return false;
    }

    public static boolean isDodgeCounterableSpell(Entity spell) {
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

    public static boolean isJumpCounterableSpell(AbstractSpell spell) {
        return spell instanceof HeatSurgeSpell ||
                spell instanceof FrostwaveSpell;
    }
}
