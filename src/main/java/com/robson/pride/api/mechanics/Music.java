package com.robson.pride.api.mechanics;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.registries.ForgeRegistries;

public class Music {



    public static void musicCore(LocalPlayer player){
        if (player != null) {
            if (!Minecraft.getInstance().getSoundManager().isActive(new SimpleSoundInstance(
                    getMusicToPlay(player),
                    SoundSource.MUSIC,
                    1.0F,
                    1.0F,
                    player.level().random,
                    false,
                    0,
                    SoundInstance.Attenuation.NONE,
                    0.0D, 0.0D, 0.0D,
                    true   ))){

            }
        }
    }

    public static void changeMusic(ResourceLocation music){
        Holder<SoundEvent> soundEventHolder = ForgeRegistries.SOUND_EVENTS.getHolder(music).get();
        musicFadeOut(Minecraft.getInstance());
        musicFadeIn(Minecraft.getInstance(), soundEventHolder);
    }

    public static void musicFadeIn(Minecraft minecraft, Holder<SoundEvent> music) {
        minecraft.getMusicManager().startPlaying(
                new net.minecraft.sounds.Music(
                        music,
                        0,
                        0,
                        true
                ));
    }

    public static void musicFadeOut(Minecraft minecraft){
       minecraft.getSoundManager().updateSourceVolume(SoundSource.MUSIC, 0);
    }

    public static void loopFadeOut(Minecraft minecraft){

    }

    public static ResourceLocation getMusicToPlay(LocalPlayer player){
        if (player != null){
        }
        return new ResourceLocation("s");
    }
}
