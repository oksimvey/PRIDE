package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.LongPressKey;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.events.OnAttackStartEvent;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import static com.robson.pride.epicfight.styles.SheatProvider.unsheat;

public class KeySpecial extends LongPressKey {

    public KeySpecial() {
        super((byte) 10);
    }

    @Override
    public void onPress(Player player) {
        unsheat(player);
        OnAttackStartEvent.onAttackStart(EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class));
    }

    @Override
    public void onLongPress(Player player) {
        SkillCore.onSkillExecute(player);
    }
}
