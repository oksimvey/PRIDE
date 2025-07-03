package com.robson.pride.keybinding;

import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.keybinding.BasicKey;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.FollowEntityGoal;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.api.utils.math.BlockUtils;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

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
                AnimUtils.playAnim(player, AnimationsRegister.SHORT_WHISTLE, 0);
                PlaySoundUtils.playSoundByString(player, "pride:short_whistle", 1, 1);
                Entity mount = serverlevel.getEntity(UUID.fromString(data.getMount()));
                if (mount instanceof PathfinderMob mountl && !mountl.level().isClientSide) {
                    if (mountl.distanceTo(player) > 20) {
                        Vec3 vec3 = BlockUtils.randomDistantPos(player.position(), 15, 20);
                        int tries = 0;
                        int height = (int) Math.ceil(mountl.getBbHeight());
                        int radius = (int) Math.ceil(mountl.getBbWidth());
                        while (tries < 10 && !BlockUtils.isValidVec3(player.level(), BlockUtils.getValidVec3Height(player.level(), vec3, height), radius)){
                            vec3 = BlockUtils.randomDistantPos(player.position(), 15, 20);
                            tries++;
                        }
                        if (BlockUtils.isValidVec3(player.level(), BlockUtils.getValidVec3Height(player.level(), vec3, height), radius)) {
                            mountl.moveTo(vec3);
                        }
                    }
                    mountl.goalSelector.addGoal(1, new FollowEntityGoal(mountl, player, 1, true));
                }
            }
        }
    }
}
