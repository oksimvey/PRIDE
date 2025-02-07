package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.ClientPlayerTagsAcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;

public class MusicCore {

    public static void musicCore(Minecraft client) {
        if (client.player != null){
            CompoundTag playertags = ClientPlayerTagsAcessor.playerTags.get(client.player);
            if (playertags != null){
                if (playertags.contains("pride_mob_music")){
                    deserializeMobMusic(client, playertags.getString("pride_mob_music"));
                }
                else deserializeBiomeMusic(client);
            }
        }
    }

    public static void deserializeMobMusic(Minecraft client, String music){

    }

    public static void deserializeBiomeMusic(Minecraft client){

    }
}
