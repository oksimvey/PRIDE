package com.robson.pride.api.customtick;

import com.robson.pride.api.cam.DynamicCam;
import com.robson.pride.api.client.AutoBattleMode;
import com.robson.pride.api.musiccore.MusicCore;
import com.robson.pride.api.musiccore.PrideMusicManager;
import com.robson.pride.api.skillcore.CooldownManager;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.epicfight.styles.SheatProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.musiccore.PrideMusicManager.playerMusicManagerThread;

public class CustomTickManager {

    public static ConcurrentHashMap<Player, List<Entity>> targeting_entities = new ConcurrentHashMap<>();

    public static void startTick(Player player) {
        stopTick(player);
        playerMusicManagerThread.put(player, new PrideMusicManager((byte) 0, Minecraft.getInstance().getMusicManager()));
        loopTick(player);
    }

    public static void stopTick(Player player) {
        playerMusicManagerThread.remove(player);
    }

    public static void onTick(Player player) {
        if (playerMusicManagerThread.get(player) != null) {
            loopTick(player);
            CooldownManager.timeCooldowns(player);
            if (!Minecraft.getInstance().isPaused()) {
                if (player.tickCount % 10 == 0) {
                    SheatProvider.provideSheat(player);
                    MusicCore.musicCore(player);
                    playerTargetingEntitiesSet(player);
                    DynamicCam.dynamicCamTick(player);
                    AutoBattleMode.autoSwitch(EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class));
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

    public static void startRespawnTick(Player player) {
        stopTick(player);
        TimerUtil.schedule(() -> startTick(player), 52, TimeUnit.MILLISECONDS);
    }

    public static void loopTick(Player player) {
        TimerUtil.schedule(() -> onTick(player), 50, TimeUnit.MILLISECONDS);
    }
}
