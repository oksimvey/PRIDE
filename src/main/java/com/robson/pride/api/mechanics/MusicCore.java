package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.ClientPlayerTagsAcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class MusicCore {

    public static ConcurrentHashMap<Player, MusicManager> musicManagerMap = new ConcurrentHashMap<>();

    public static void musicCore(Minecraft client) {
        if (client.player != null){
            CompoundTag playertags = ClientPlayerTagsAcessor.playerTags.get(client.player);
            if (playertags != null){
                if (playertags.contains("pride_mob_music")){

                }
                else deserializeBiomeMusic(client);
            }
        }
    }

    public static void deserializeBiomeMusic(Minecraft client){

    }
}
