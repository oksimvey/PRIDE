package com.robson.pride.keybinding;

import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.utils.CameraUtils.correctCamera;

public class onQPress {

    public static boolean cantDodgeForward(Player player) {
        for (Entity ent : player.level().getEntities(player, MathUtils.createAABBAroundEnt(player, 5))) {
            if (ent != null) {
                if (PerilousAttack.checkPerilous(ent)) {
                    return true;
                } else if (ent instanceof Projectile arrow) {
                    return arrow.getOwner() != player;
                } else if (MikiriCounter.isDodgeCounterableSpell(ent)) {
                    return true;
                }
            }
        }
        return false;
    }
}
