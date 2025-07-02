package com.robson.pride.keybinding;

import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;

public class KeyMount extends BasicKey {

    @Override
    public void onPress(Player player) {
        ClientSavedData data = ClientDataManager.CLIENT_DATA_MANAGER.get(player).getProgressionData();
        if (data != null) {
            if (player.getVehicle() != null && player.getVehicle() instanceof PlayerRideableJumping) {
                data.setMount(player.getVehicle().getUUID().toString());
                player.displayClientMessage(Component.literal("mount has been set"), true);
            }
            else if (player.level() instanceof ServerLevel serverlevel){
                Entity mount = serverlevel.getEntity(UUID.fromString(data.getMount()));
                if (mount != null) {
                   mount.teleportTo(player.getX(), player.getY(), player.getZ());
                }
            }
        }
    }
}
