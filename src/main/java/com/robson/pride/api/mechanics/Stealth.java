package com.robson.pride.api.mechanics;

import com.robson.pride.registries.ParticleRegister;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class Stealth {

    public static void renderCriticalParticle(LocalPlayer player, Entity target) {
        if (player != null && target != null) {
            if (canBackStab(player, target) || target.getPersistentData().getBoolean("isVulnerable")) {
                player.level().addParticle(ParticleRegister.VULNERABLE.get(), target.getX(), target.getY() + target.getBbHeight() * 1.25, target.getZ(), 0, 0, 0);
            }
        }
    }

    public static boolean canBackStab(Entity viewer, Entity target) {

        return false;
    }
}
