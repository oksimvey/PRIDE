package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
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

    public static void onArrowMikiri(Entity ent, Projectile projectile, ProjectileImpactEvent event){
    event.setCanceled(true);
        ProjectileUtil.shootProjectileFromEnt(projectile, ent, 3);
    }

    public static void onSpellMikiri(SpellDamageEvent event){
       if (event != null && event.getSpellDamageSource().spell() != null && event.getEntity() != null){
           event.getSpellDamageSource().getDirectEntity().remove(Entity.RemovalReason.DISCARDED);
           AnimUtils.playAnim(event.getEntity(), AnimationsRegister.PROJECTILE_COUNTER, 0);
          TimerUtil.schedule(()-> SpellUtils.castSpell(event.getEntity(), event.getSpellDamageSource().spell(), 3, 0), 500,TimeUnit.MILLISECONDS);
           event.setCanceled(true);
       }
    }
}
