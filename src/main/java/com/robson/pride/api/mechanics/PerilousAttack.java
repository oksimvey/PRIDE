package com.robson.pride.api.mechanics;

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

    public static void playPerilous(Entity ent) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            PlaySoundUtils.playSoundByString(livingEntityPatch.getTarget(), "pride:perilous", 3, 1);
        }
        perilousParticle(ent);
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
        } else {

        }
    }
}
