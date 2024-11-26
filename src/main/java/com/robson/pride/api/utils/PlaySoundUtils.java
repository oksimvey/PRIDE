package com.robson.pride.api.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaySoundUtils {
    public static void playSound(Entity ent, String soundid, float volume, float pitch){
        ent.level().playSound(null, BlockPos.containing( ent.getX(), ent.getY(), ent.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(soundid)), SoundSource.NEUTRAL, volume, pitch);
    }
}
