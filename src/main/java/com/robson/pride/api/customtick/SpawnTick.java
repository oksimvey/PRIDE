package com.robson.pride.api.customtick;

import com.robson.pride.api.biomesettings.EntityTypeSpawnSetting;
import com.robson.pride.api.utils.MathUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.util.Random;

import static com.robson.pride.api.biomesettings.BiomeSettingsManager.biomeSettingsMap;

public class SpawnTick {

    public static void trySpawn(Player player) {
        String biome = player.level().getBiome(player.blockPosition()).unwrapKey().get().location().toString();
        if (biomeSettingsMap.get(biome) != null) {
            AABB spawnarea = MathUtils.createAABBAroundEnt(player, 50);
            for (EntityTypeSpawnSetting spawnSetting : biomeSettingsMap.get(biome).getEntitiesSpawnSettings()) {
                if (spawnSetting.getWeight() >= new Random().nextInt(101)) {
                    byte entinArea = 0;
                    for (Entity ent : player.level().getEntities(player, spawnarea)) {
                        if (ent != null && ent.getType() == spawnSetting.getEntityType()) {
                            entinArea++;
                        }
                    }
                    if (entinArea < spawnSetting.getMaxEntities()) {
                        BlockPos pos = getRandomSurfacePos(player);
                        if (player.level().getBiome(pos).unwrapKey().get().location().toString().equals(biome)) {
                            Entity entity = spawnSetting.getEntityType().create(player.level());
                            if (entity != null) {
                                entity.setPos(pos.getX(), pos.getY(), pos.getZ());
                                player.level().addFreshEntity(entity);
                            }
                        }
                    }
                }
            }
        }
    }

    public static BlockPos getRandomSurfacePos(Player player) {
        int offsetX = new Random().nextInt(25) - new Random().nextInt(25);
        int offsetZ = new Random().nextInt(25) - new Random().nextInt(25);
        if (offsetX < 10 && offsetX > -10) {
            if (offsetX < 0) {
                offsetX += 10;
            } else offsetX -= 10;
        }
        if (offsetZ < 10 && offsetZ > -10) {
            if (offsetZ < 0) {
                offsetZ += 10;
            } else offsetZ -= 10;
        }
        return player.blockPosition().offset(offsetX, 0, offsetZ);
    }
}
