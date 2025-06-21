package com.robson.pride.api.keybinding;

import net.minecraft.world.entity.player.Player;

public abstract class PridePressKey extends PrideBasicKey {

    private byte presscounter = 0;

    private final byte counterforstart;

    boolean longPressTriggered = false;

    public PridePressKey(byte counterforstart) {
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
    public void onRelease(){
        super.onRelease();
        this.presscounter = 0;
        this.longPressTriggered = false;
    }


    @Override
    public void onPress(Player player){}

    public abstract void onLongPress(Player player);

}
