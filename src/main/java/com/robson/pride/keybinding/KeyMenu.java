package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.gui.ProgressionGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class KeyMenu extends BasicKey {

    @Override
    public void onPress(Player player) {
        Minecraft.getInstance().setScreen(new ProgressionGUI(player, Minecraft.getInstance()));
    }
}
