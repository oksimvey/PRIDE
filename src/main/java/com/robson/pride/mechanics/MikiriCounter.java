package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.concurrent.TimeUnit;

public class MikiriCounter {

    public static void setMikiri(Entity ent, String MikiriType,int delay, int window){
        TimerUtil.schedule(()->  ent.getPersistentData().putString("Mikiri", MikiriType), delay, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> ent.getPersistentData().putString("Mikiri", ""), window, TimeUnit.MILLISECONDS);
    }

    public static void onPierceMikiri(Entity ent, Entity ddmgent) {
            TargetUtil.rotateToTarget(ddmgent);
        TimerUtil.schedule(() -> TargetUtil.rotateToTarget(ddmgent), 125, TimeUnit.MILLISECONDS);
            TeleportUtils.teleportEntityRelativeToEntity(ent, ddmgent, 0, ddmgent.getBbHeight() * 0.25);
            AnimUtils.playAnim(ent, "pride:biped/skill/mikiri_step", 0);
            TimerUtil.schedule(() -> AnimUtils.playAnim(ddmgent, "pride:biped/skill/mikiri_two_hand", 0), 50, TimeUnit.MILLISECONDS);
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
        projectile.setOwner(ent);
    TeleportUtils.teleportEntityRelativeToEntity(projectile, ent, 0, 0.5);
    projectile.setDeltaMovement(0, 0 ,1);
    }

    public static void onSpellMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }
}
