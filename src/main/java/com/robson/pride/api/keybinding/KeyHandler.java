package com.robson.pride.api.keybinding;


import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.keybinding.KeyDodge;
import com.robson.pride.keybinding.KeySwapHand;
import com.robson.pride.keybinding.KeySpecial;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

public interface KeyHandler {

    static void tick(Player player) {
        if (player != null) {
            if (DataManager.getWeaponData(player.getMainHandItem()) != null) {
                handleKeyInput(player, KeyRegister.SPECIAL, KeySpecial.KEY);
                handleKeyInput(player, KeyRegister.SWAP_HAND, KeySwapHand.KEY);
            }
            if (StaminaUtils.getStamina(player) >= 3){
                handleKeyInput(player, KeyRegister.DODGE, KeyDodge.KEY);
            }
        }
    }

    static void handleKeyInput(Player player, KeyMapping key, BasicKey keyAction){
        if (ControllEngine.isKeyDown(key)){
            keyAction.onPressTick(player);
            return;
        }
        if (keyAction.isPressed()) {
            keyAction.onRelease(player);
        }
    }

    static byte getInputForDodge(){
        Options options  = Minecraft.getInstance().options;
        if (options.keyUp.isDown()) {
            return 1;
        }
        if (options.keyRight.isDown()) {
            return 2;
        }
        if (options.keyLeft.isDown()) {
            return 3;
        }
        return 4;
    }
}
