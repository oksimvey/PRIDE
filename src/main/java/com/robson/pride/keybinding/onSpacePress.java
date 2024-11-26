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
                AttributeUtils.addModifiertoAnim(player, "epicfight:impact", 3f, 1500, AttributeModifier.Operation.ADDITION);
                LocalPlayerPatch localPlayerPatch = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, LocalPlayerPatch.class);
                player.setInvulnerable(true);
                localPlayerPatch.rotateTo(target, 1, true);
                AnimUtils.playAnim(player, "wom:biped/combat/enderblaster_onehand_dash", 0f);
                TimerUtil.schedule(()-> StaminaUtils.StaminaConsume(target, 6), 750, TimeUnit.MILLISECONDS);
                TimerUtil.schedule(()->{player.setInvulnerable(false);localPlayerPatch.rotateTo(target, 1, true);localPlayerPatch.setLockOn(true);}, 2000, TimeUnit.MILLISECONDS);
            }
        }
    }
}
