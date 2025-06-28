package com.robson.pride.keybinding;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.player.ClientData;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientProgressionData;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;

public class KeyGuard extends BasicKey {

    @Override
    public void onPress(Player player) {
        SkillDataManager.addSkill(player, SkillDataManager.GUARD);
        ClientData data = ClientDataManager.CLIENT_DATA_MANAGER.get(player);
        List<PrideVec3f> coords = ArmatureUtils.getJointInterpolatedMovement(Minecraft.getInstance().player, player, 3, Armatures.BIPED.get().toolR);
        for (PrideVec3f coord : coords) {
            player.sendSystemMessage(Component.literal("x = " + coord.x() + " y = " + coord.y() + " z = " + coord.z()));
        }
        if (data != null && data.getProgressionData() != null){
            data.getProgressionData().addXP(ClientProgressionData.Dexterity, 10);
            player.sendSystemMessage(Component.literal("lvl = " + data.getProgressionData().getLevel(ClientProgressionData.Dexterity)));
            player.sendSystemMessage(Component.literal( data.getProgressionData().getXP(ClientProgressionData.Dexterity) + "/" +
                    data.getProgressionData().getMaxXP(ClientProgressionData.Dexterity) + " XP"));
        }
    }

    @Override
    protected void onReleaseAction(Player player){
        SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
    }
}
