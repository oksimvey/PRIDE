package com.robson.pride.api.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public interface ClientPlayerTagsAcessor {

    ConcurrentHashMap<Player, CompoundTag> playerTags = new ConcurrentHashMap<>();

}
