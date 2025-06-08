package com.robson.pride.api.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;

public class TagsUtils {

    public static boolean toggleBoolean(CompoundTag tag, String tagname) {
        if (tag != null) {
            if (tag.getBoolean(tagname)) {
                tag.putBoolean(tagname, false);
                return true;
            }
            tag.putBoolean(tagname, true);
        }
        return false;
    }

    public interface ClientPlayerTagsAcessor {
        ConcurrentHashMap<Player, CompoundTag> playerTags = new ConcurrentHashMap<>();
    }
}
