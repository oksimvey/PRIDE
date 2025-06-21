package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.PridePressKey;
import com.robson.pride.api.skillcore.SkillCore;
import net.minecraft.world.entity.player.Player;

public interface OnLeftClick {

    PridePressKey KEY = new PridePressKey((byte) 20) {

        @Override
        public void onLongPress(Player player) {
            SkillCore.onSkillExecute(player);
        }
    };
}
