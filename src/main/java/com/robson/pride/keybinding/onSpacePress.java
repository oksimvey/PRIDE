package com.robson.pride.keybinding;

import com.robson.pride.api.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class onSpacePress {
    public static void onPress(Player player){
        Entity target = TargetUtil.getTarget(player);
        if (target != null){
            if (Objects.equals(target.getPersistentData().getString("Perilous"), "Sweep") && Objects.equals(player.getPersistentData().getString("Mikiri"), "Jump")){
                LocalPlayerPatch localPlayerPatch = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, LocalPlayerPatch.class);
                player.setInvulnerable(true);
                localPlayerPatch.rotateTo(target, 1, true);
                AnimUtils.playAnim(player, "pride:biped/skill/mikiri_jump", 0f);
                TimerUtil.schedule(()-> StaminaUtils.StaminaConsume(target, 6), 750, TimeUnit.MILLISECONDS);
                TimerUtil.schedule(()->player.setInvulnerable(false), 2000, TimeUnit.MILLISECONDS);
            }
        }
    }
}
