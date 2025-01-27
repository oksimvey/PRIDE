package com.robson.pride.api.mechanics;

import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.Shogun;
import com.robson.pride.registries.SoundsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;

public class MusicCore {

    private static int musicPriority = 0;

    public static void musicCore(Player player) {
        if (player != null) {
            Music music = getBattleMusicToPlay(player);
            if (music != null) {
                if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(music) && musicPriority < getMusicPriority(player)) {
                    musicPriority = getMusicPriority(player);
                    Minecraft.getInstance().getMusicManager().stopPlaying();
                    Minecraft.getInstance().getMusicManager().startPlaying(music);
                }
            }
            else Minecraft.getInstance().getMusicManager().stopPlaying();
        }
    }

    public static Music getBattleMusicToPlay(Player player) {
        if (player != null) {
            if (!player.level().isClientSide) {
                for (Entity ent : player.level().getEntities(player, new AABB(player.getX() - 25, player.getY() - 20, player.getZ() - 25, player.getX() + 25, player.getY() + 30, player.getZ() + 25))) {
                    if (ent != null) {
                        if (TargetUtil.getTarget(ent) == player) {
                            PrideMobPatchReloader.PrideMobPatchProvider provider = (PrideMobPatchReloader.PrideMobPatchProvider) PrideMobPatchReloader.ADVANCED_MOB_PATCH_PROVIDERS.get(ent.getType());
                            if (provider != null){
                                if (provider.getMusic() != null){
                                    Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(provider.getMusic()));
                                    return new Music(holder, 1, 1, true);
                                }
                            }
                        }
                    }
                }
            }
        }
        return getMusicToPlay(player);
    }

    public static int getMusicPriority(Player player) {
        if (player != null) {
            if (!player.level().isClientSide) {
                for (Entity ent : player.level().getEntities(player, new AABB(player.getX() - 25, player.getY() - 20, player.getZ() - 25, player.getX() + 25, player.getY() + 30, player.getZ() + 25))) {
                    if (ent != null) {
                        if (TargetUtil.getTarget(ent) == player) {
                            PrideMobPatchReloader.PrideMobPatchProvider provider = (PrideMobPatchReloader.PrideMobPatchProvider) PrideMobPatchReloader.ADVANCED_MOB_PATCH_PROVIDERS.get(ent.getType());
                            if (provider != null){
                                if (provider.getMusicPriority() != null){
                                    return provider.getMusicPriority();
                                }
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static Music getMusicToPlay(Player player) {
        if (player != null) {
        }
        return null;
    }
}
