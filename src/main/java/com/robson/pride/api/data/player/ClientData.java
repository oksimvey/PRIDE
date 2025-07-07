package com.robson.pride.api.data.player;

import com.robson.pride.api.keybinding.KeyHandler;
import com.robson.pride.api.musiccore.PrideMusicManager;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ClientData {

    private ClientSavedData progressionData;

    private KeyHandler keyHandler;

    private PrideMusicManager musicManager;

    public PrideMusicManager getMusicManager(){
        return this.musicManager;
    }

    public static ClientData createDefault(Player player){
        ClientData data = new ClientData();
        if (player instanceof ServerPlayer player1) {
            CompoundTag tag = ClientDataManager.readPlayerDat(player1);
            if (tag.getByteArray("lvl").length == 0 || tag.getIntArray("xp").length == 0) {
                tag.putByteArray("lvl", new byte[]{1, 1, 1, 1, 1});
                tag.putByteArray("skills", new byte[]{});
                tag.putIntArray("xp", new int[]{0, 0, 0, 0, 0});
                tag.putString("mount", "");
            }
            ClientSavedData datap = ClientSavedData.fromNBT(tag);
            data.setProgressionData(datap);
            data.setMusicManager(new PrideMusicManager((byte) 0, Minecraft.getInstance().getMusicManager()));
            data.setKeyHandler(new KeyHandler());
        }
        return data;
    }

    public void setKeyHandler(KeyHandler keyHandler){
        this.keyHandler = keyHandler;
    }


    public void tick(Player player){
        this.keyHandler.tick(player);
    }


    public void setProgressionData(ClientSavedData progressionData){
        this.progressionData = progressionData;
    }

    public void setMusicManager(PrideMusicManager musicManager){
        this.musicManager = musicManager;
    }

    public ClientSavedData getProgressionData() {
        return progressionData;
    }
}
