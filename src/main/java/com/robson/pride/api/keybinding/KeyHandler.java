package com.robson.pride.api.keybinding;


import com.robson.pride.keybinding.OnLeftClick;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.client.events.engine.ControllEngine;

public interface KeyHandler {

    static void tick(Player player) {
        if (player != null) {
            handleKeyInput(player, KeyRegister.SPECIAL, OnLeftClick.KEY);
        }
    }

    static void handleKeyInput(Player player, KeyMapping key, PrideBasicKey keyAction){
        if (ControllEngine.isKeyDown(key)){
            keyAction.onPressTick(player);
            return;
        }
        if (keyAction.isPressed()) {
            keyAction.onRelease();
        }
    }
}
