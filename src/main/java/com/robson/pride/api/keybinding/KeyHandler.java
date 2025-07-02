package com.robson.pride.api.keybinding;


import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.keybinding.*;
import com.robson.pride.registries.KeyRegister;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ShieldItem;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;

public class KeyHandler {

    private final KeySpecial special;

    private final KeySwapHand swapHand;

    private final KeyGuard guard;

    private final KeyMenu menu;

    private final KeyDodge dodge;

    private final KeyMount mount;

    public KeyHandler() {
        this.special = new KeySpecial();
        this.swapHand = new KeySwapHand();
        this.guard = new KeyGuard();
        this.menu = new KeyMenu();
        this.dodge = new KeyDodge();
        this.mount = new KeyMount();
    }

    public void tick(Player player) {
        if (player != null && Minecraft.getInstance().screen == null) {
            handleKeyInput(player, KeyRegister.MOUNT,this.mount);
            handleKeyInput(player, KeyRegister.MENU, this.menu);
            if (ServerDataManager.getWeaponData(player.getMainHandItem()) != null) {
                handleKeyInput(player, EpicFightKeyMappings.ATTACK, this.special);
                handleKeyInput(player, KeyRegister.SWAP_HAND, this.swapHand);
                handleKeyInput(player, EpicFightKeyMappings.GUARD, this.guard);
            }
            else if (player.getUseItem().getItem() instanceof ShieldItem){
                handleKeyInput(player, EpicFightKeyMappings.GUARD, this.guard);
            }
            if (StaminaUtils.getStamina(player) >= 3){
                handleKeyInput(player, KeyRegister.DODGE, this.dodge);
            }
        }
    }


    static void handleKeyInput(Player player, KeyMapping key, BasicKey keyAction){
        if (ControllEngine.isKeyDown(key)) {
            keyAction.onPressTick(player);
            return;
        }
        if (keyAction.isPressed()){
            keyAction.onRelease(player);
        }
    }

    public static byte getInputForDodge(){
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
