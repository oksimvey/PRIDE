package com.robson.pride.api.keybinding;

import net.minecraft.world.entity.player.Player;

public abstract class LongPressKey extends BasicKey {

    private byte presscounter = 0;

    private final byte counterforstart;

    protected boolean longPressTriggered = false;

    public LongPressKey(byte counterforstart) {
        this.counterforstart = counterforstart;
    }

    @Override
    public void onPressTick(Player player){
        super.onPressTick(player);
        if (this.isPressed() && !longPressTriggered) {
            this.presscounter++;
            if (this.presscounter >= this.counterforstart) {
                this.onLongPress(player);
                this.longPressTriggered = true;
            }
        }
    }

    @Override
    public void onRelease(Player player){
        super.onRelease(player);
        this.presscounter = 0;
        this.longPressTriggered = false;
    }

    public abstract void onLongPress(Player player);

}
