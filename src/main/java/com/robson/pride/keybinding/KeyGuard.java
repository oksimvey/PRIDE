package com.robson.pride.keybinding;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.player.ClientData;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.keybinding.BasicKey;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class KeyGuard extends BasicKey {

    @Override
    public void onPress(Player player) {
        SkillDataManager.addSkill(player, SkillDataManager.GUARD);
        ClientData data = ClientDataManager.CLIENT_DATA_MANAGER.get(player);
        if (data != null && data.getProgressionData() != null){
            data.getProgressionData().addXP(ClientSavedData.Dexterity, 10);
            player.sendSystemMessage(Component.literal("lvl = " + data.getProgressionData().getLevel(ClientSavedData.Dexterity)));
            player.sendSystemMessage(Component.literal( data.getProgressionData().getXP(ClientSavedData.Dexterity) + "/" +
                    data.getProgressionData().getMaxXP(ClientSavedData.Dexterity) + " XP"));
        }
    }

    @Override
    protected void onReleaseAction(Player player){
        SkillDataManager.removeSkill(player, SkillDataManager.GUARD);
    }
}
