package com.robson.pride.api.mechanics.mikiricounter;

import com.robson.pride.api.mechanics.perilous.PerilousType;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MikiriType {

    private static ConcurrentHashMap<Entity, List<MikiriType>> mikiriTypeMap = new ConcurrentHashMap<>();

    public void onMikiri(Entity dmgent, Entity ent) {

    }


    public static boolean canMikiri(Entity ent, PerilousType perilousType) {
        if (ent != null && perilousType != null && mikiriTypeMap.get(ent) != null) {
            return mikiriTypeMap.get(ent).contains(perilousType.getMikiriType());
        }
        return false;
    }
}
