package com.robson.pride.api.keybinding;

import net.minecraft.world.entity.player.Player;

public abstract class BasicKey {

    private boolean isPressed = false;

    public void onPressTick(Player player) {
        if (!this.isPressed) {
            this.onPress(player);
            this.isPressed = true;
        }
    }

    public boolean isPressed() {
        return this.isPressed;
    }

    public void onRelease(Player player) {
        this.isPressed = false;
    }

    public abstract void onPress(Player player);

}
