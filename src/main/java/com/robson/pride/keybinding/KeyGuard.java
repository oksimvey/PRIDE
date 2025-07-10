package com.robson.pride.keybinding;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class KeyGuard extends BasicKey {

    @Override
    public void onPress(Player player) {
        SkillDataManager.addSkill(player, SkillDataManager.GUARD);
        Entity target = TargetUtil.getTarget(player);
        if (target != null){
            player.sendSystemMessage(Component.literal("targeting" + target.getName().getString()));
            long memory = target.getPersistentData().sizeInBytes();
            player.sendSystemMessage(Component.literal("entity memory consumption: " + memory + " bytes"));
        }
     }

    @Override
    protected void onReleaseAction(Player player){
        SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
    }
}
