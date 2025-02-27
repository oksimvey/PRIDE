package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.TargetUtil;
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

    public static boolean canBackStab(Entity ent, Entity target) {
        if (ent != null && target != null) {
            if (TargetUtil.getTarget(target) == null) {
                return false;
            }
        }
        return false;
    }

    public static boolean checkForStealth(Entity ent, Entity target){
        if  (ent != null && target != null){
            Vec3 entlookangle = ent.getLookAngle();
            Vec3 targetflipedlookangle = target.getLookAngle().reverse();
            float xdiff = (float) (entlookangle.x - targetflipedlookangle.x);
            float zdiff = (float) (entlookangle.z - targetflipedlookangle.z);
            return xdiff < 0.15 && xdiff >-0.15 && zdiff < 0.15 && zdiff >-0.15;
        }
        return false;
    }
}
