package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.Shogun;
import com.robson.pride.registries.SoundsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class MusicCore {

    public static void musicCore(Player player) {
        if (player != null) {
            Music music = getBattleMusicToPlay(player);
            if (music != null) {
                if (!Minecraft.getInstance().getMusicManager().isPlayingMusic(music)) {
                Minecraft.getInstance().getMusicManager().stopPlaying();
                Minecraft.getInstance().getMusicManager().startPlaying(music);
                }
            }
        }
    }
    public static Music getBattleMusicToPlay(Player player){
        if (player != null){
            if (!player.level().isClientSide) {
                for (Entity ent : player.level().getEntities(player, new AABB(player.getX() - 25, player.getY() - 20, player.getZ() - 25, player.getX() + 25, player.getY() + 30, player.getZ() + 25))) {
                    if (ent != null) {
                        if (TargetUtil.getTarget(ent) == player) {
                            if (ent instanceof Shogun) {
                                return new Music(SoundsRegister.SHOGUN.getHolder().get(), 1, 1, true);
                            }
                        }
                    }
                }
            }
        }
        return getMusicToPlay(player);
    }
    public static Music getMusicToPlay(Player player){
        if (player != null){

        }
        return null;
    }
}
