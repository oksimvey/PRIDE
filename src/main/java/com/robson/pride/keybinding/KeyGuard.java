package com.robson.pride.keybinding;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.keybinding.BasicKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface KeyGuard {

    BasicKey KEY = new BasicKey() {

        public void onPressTick(Player player){
            super.onPressTick(player);
            if (!this.isPressed()){
                onRelease(player);
            }
        }

        public void onRelease(Player player) {
            super.onRelease(player);
            SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
            player.sendSystemMessage(Component.literal("key released"));
        }

        @Override
        public void onPress(Player player) {
            SkillDataManager.addSkill(player, SkillDataManager.GUARD);
            player.sendSystemMessage(Component.literal("key pressed"));
        }
    };
}
