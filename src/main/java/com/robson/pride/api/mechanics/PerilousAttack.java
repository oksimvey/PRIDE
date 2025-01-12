package com.robson.pride.api.mechanics;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PerilousAttack {

    public static boolean checkPerilous(Entity ent) {
        if (ent != null) {
            String Perilous = ent.getPersistentData().getString("Perilous");
            return Objects.equals(Perilous, "pierce_two_hand") || Objects.equals(Perilous, "pierce_dual_wield") || Objects.equals(Perilous, "pierce_one_hand") || Objects.equals(Perilous, "sweep")  || Objects.equals(Perilous, "total");
        }
        return false;
    }

    public static void onPerilous(Entity ent, Entity ddmgent, LivingAttackEvent event){
        String Perilous = ddmgent.getPersistentData().getString("Perilous");
        if (Objects.equals(Perilous, "total")) {
            PerilousSucess(ent, event);
        } else if (Objects.equals(Perilous, "pierce_two_hand") || Objects.equals(Perilous, "pierce_dual_wield") || Objects.equals(Perilous, "pierce_one_hand")) {
                if (ent.getPersistentData().getBoolean("mikiri_dodge")) {
                    MikiriCounter.onPierceMikiri(ent, ddmgent, Perilous);
                    event.setCanceled(true);
                }
                else PerilousSucess(ent, event);
        }
         else if (Objects.equals(Perilous, "sweep") && ent.getPersistentData().getBoolean("mikiri_sweep")) {
            event.setCanceled(true);
        } else {
            PerilousSucess(ent, event);
        }
    }

    public static void setPerilous(Entity ent, String PerilousType, int PerilousTime){
        ent.getPersistentData().putString("Perilous", PerilousType);
        Entity target = TargetUtil.getTarget(ent);
        if (target instanceof Player) {
            playPerilous(target);
            perilousParticle(target);

        }
        disablePerilous(ent, PerilousTime);
    }

    public static void playPerilous(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            PlaySoundUtils.playSoundByString(livingEntityPatch.getTarget(), "pride:perilous", 3, 1);
        }
    }

    public static void disablePerilous(Entity ent, int delay){
        TimerUtil.schedule(()-> ent.getPersistentData().putString("Perilous",  ""), delay, TimeUnit.MILLISECONDS);
    }

    public static void perilousParticle(Entity player) {
        Particle particle = Minecraft.getInstance().particleEngine.createParticle(
                ParticleRegister.PERILOUS.get(),
                player.getX(),
                player.getY() + 2.25,
                player.getZ(),
                0, 0, 0
        );

        final int[] delay = {1};

        TimerUtil.schedule(new Runnable() {
            @Override
            public void run() {
                if (particle.isAlive()) {
                    particle.setPos(player.getX(), player.getY() + 2.25, player.getZ());
                    particle.scale(1.025f);
                }

                if (delay[0] < 500) {
                    delay[0] += 1;
                }
                TimerUtil.schedule(this, delay[0], TimeUnit.MILLISECONDS);
            }
        }, delay[0], TimeUnit.MILLISECONDS);
    }

    public static void PerilousSucess(Entity ent, LivingAttackEvent event){
        if (ent instanceof Player player) {
            player.stopUsingItem();
            }
         else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.setParry(false);
                livingEntityPatch.setBlocking(false);
            }
        }
    }
}
