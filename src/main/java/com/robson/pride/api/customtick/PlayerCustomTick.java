package com.robson.pride.api.customtick;

import com.robson.pride.api.cam.DynamicCam;
import com.robson.pride.api.musiccore.MusicCore;
import com.robson.pride.api.skillcore.CooldownManager;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.robson.pride.api.musiccore.PrideMusicManager.playerMusicManagerThread;

public class PlayerCustomTick {

    public static ConcurrentHashMap<Player, List<Entity>> targeting_entities = new ConcurrentHashMap<>();

    public static void onTick(Player player) {
        if (playerMusicManagerThread.get(player) != null) {
            CooldownManager.timeCooldowns(player);
            if (!Minecraft.getInstance().isPaused()) {
                if (player.tickCount % 10 == 0) {
                    SheatProvider.provideSheat(player);
                    MusicCore.musicCore(player);
                    playerTargetingEntitiesSet(player);
                    DynamicCam.dynamicCamTick(player);
                    if (targeting_entities.get(player) != null) {
                        playerTargetingEntitiesCheck(player);
                    }
                }
            }
        }
    }

    public static void playerTargetingEntitiesSet(Player player) {
        for (Entity ent : player.level().getEntities(player, MathUtils.createAABBAroundEnt(player, 50))) {
            if (ent != null && TargetUtil.getTarget(ent) == player) {
                List<Entity> targeting = targeting_entities.getOrDefault(player, new ArrayList<>());
                targeting.add(ent);
                targeting_entities.put(player, targeting);
            }
        }
    }

    public static void playerTargetingEntitiesCheck(Player player) {
        targeting_entities.get(player).removeIf(ent -> ent == null || !ent.isAlive() || TargetUtil.getTarget(ent) != player);
    }
}
