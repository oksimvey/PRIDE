package com.robson.pride.keybinding;

import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KeyGuard extends BasicKey {

    @Override
    public void onPress(Player player) {
        SkillDataManager.addSkill(player, SkillDataManager.GUARD);
        if (TargetUtil.getTarget(player) instanceof LivingEntity living){
            RenderingCore.TEST.add(living);
        }
     }

    @Override
    protected void onReleaseAction(Player player){
        SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
    }
}
