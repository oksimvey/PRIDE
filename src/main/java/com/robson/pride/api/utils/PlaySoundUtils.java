package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundUtils {

    public static void playSound(Entity ent, SoundEvent sound, float volume, float pitch) {
        if (ent != null) {
            if (!ent.level().isClientSide()) {
                ServerLevel world = (ServerLevel) ent.level();
                if (world != null) {
                    world.playSound(null, BlockPos.containing(ent.getX(), ent.getY(), ent.getZ()), sound, SoundSource.NEUTRAL, volume, pitch);
                }
            }
        }
    }

    public static void playSoundByString(Entity ent, String soundid, float volume, float pitch) {
        if (ent != null) {
            playSound(ent, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundid)), volume, pitch);
        }
    }

    public static void playNonRegisteredSound(Entity ent, String soundid, float volume, float pitch) {
        Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(soundid)));
        if (Minecraft.getInstance().level != null)
            Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, holder.get(), SoundSource.NEUTRAL, volume, pitch);
    }

    public static Music getMusicByString(String musicid) {
        return new Music(Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(musicid))), 1, 1, true);
    }
}
