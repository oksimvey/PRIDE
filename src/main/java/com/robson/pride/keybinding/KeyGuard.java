package com.robson.pride.keybinding;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.gameasset.Armatures;

public class KeyGuard extends BasicKey {

    @Override
    public void onPress(Player player) {
        SkillDataManager.addSkill(player, SkillDataManager.GUARD);
        Entity target = TargetUtil.getTarget(player);
        ArmatureUtils.traceEntityOnEntityJoint(
                player,
                target,
                Armatures.BIPED.get().toolR,
                Armatures.BIPED.get().rootJoint,
                true,
                true,
                100,
                100
        );
     }

    @Override
    protected void onReleaseAction(Player player){
        SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
    }
}
