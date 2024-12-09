package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.concurrent.TimeUnit;

public class MikiriCounter {

    public static void setMikiri(Entity ent, String MikiriType,int delay, int window){
        TimerUtil.schedule(()->  ent.getPersistentData().putString("Mikiri", MikiriType), delay, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> ent.getPersistentData().putString("Mikiri", ""), window, TimeUnit.MILLISECONDS);
    }

    public static void onPierceMikiri(Entity ent, Entity ddmgent, String pierce_type) {
        String animation;
        if (ddmgent instanceof Player){
            animation = "pride:biped/skill/perilous_";
        }
        else animation = "pride:biped/skill/mob_perilous_";
        AnimUtils.cancelMotion(ddmgent);
        AnimUtils.rotateToEntity(ddmgent, ent);
        TimerUtil.schedule(() -> AnimUtils.rotateToEntity(ddmgent, ent), 125, TimeUnit.MILLISECONDS);
        TeleportUtils.teleportEntityRelativeToEntity(ent, ddmgent, 0, ddmgent.getBbHeight() * 0.25);
        AnimUtils.playAnim(ent, "pride:biped/skill/mikiri_step", 0);
        TimerUtil.schedule(()->ent.setPos(ent.getX(), ddmgent.getY(), ent.getZ()), 15, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> AnimUtils.playAnim(ddmgent, animation + pierce_type, 0), 50, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> {
            StaminaUtils.consumeStamina(ddmgent, 9);
            PlaySoundUtils.playSound(ent, "pride:shieldparry", 0.5f, 1f);
        }, 150, TimeUnit.MILLISECONDS);
    }

    public static void onKickMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }

    public static void onSweepMikiri(Entity ent, Entity ddmgent){
        ent.setInvulnerable(true);
        AnimUtils.playAnim(ent, "pride:biped/skill/mikiri_jump", 0f);
        TimerUtil.schedule(()-> StaminaUtils.consumeStamina(ddmgent, 6), 500, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->ent.setInvulnerable(false), 1000, TimeUnit.MILLISECONDS);
    }

    public static void onArrowMikiri(Entity ent, Projectile projectile, ProjectileImpactEvent event){
    event.setCanceled(true);
        ProjectileUtil.shootProjectileFromEnt(projectile, ent, 3);
    }

    public static void onSpellMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }
}
