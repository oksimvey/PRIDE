package com.robson.pride.api.mechanics;

import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.ParticleRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PerilousAttack {

    public static List<String> periloustypes = Arrays.asList("pierce_two_hand", "pierce_dual_wield", "pierce_one_hand", "sweep", "total");

    public static boolean checkPerilous(Entity ent) {
        if (ent != null) {
            return periloustypes.contains(ent.getPersistentData().getString("Perilous"));
        }
        return false;
    }

    public static void onPerilous(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        String Perilous = ddmgent.getPersistentData().getString("Perilous");
        if (Objects.equals(Perilous, "pierce_two_hand") || Objects.equals(Perilous, "pierce_dual_wield") || Objects.equals(Perilous, "pierce_one_hand")) {
            if (MikiriCounter.canMobMikiri(ent, ddmgent, "Dodge")) {
                MikiriCounter.onPierceMikiri(ent, ddmgent, Perilous);
                event.setCanceled(true);
            } else PerilousSucess(ent, event);
        } else if (Perilous.equals("sweep")) {
            if (MikiriCounter.canMobMikiri(ent, ddmgent, "Jump")) {
                event.setCanceled(true);
            } else {
                PerilousSucess(ent, event);
            }
        } else {
            PerilousSucess(ent, event);
        }
    }

    public static void playPerilous(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            PlaySoundUtils.playSoundByString(livingEntityPatch.getTarget(), "pride:perilous", 3, 1);
        }
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

    public static void PerilousSucess(Entity ent, LivingAttackEvent event) {
        if (ent instanceof Player player) {
            player.stopUsingItem();
        }
        else {
            AdvancedCustomHumanoidMobPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, AdvancedCustomHumanoidMobPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.setBlocking(false);
                livingEntityPatch.setParried(false);
            }
        }
    }
}
