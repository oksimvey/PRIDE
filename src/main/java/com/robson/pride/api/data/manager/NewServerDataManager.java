package com.robson.pride.api.data.manager;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.data.types.entity.MobTypeData;
import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.types.skill.WeaponSkillData;

import java.util.concurrent.TimeUnit;

public class NewServerDataManager {

    private static Cache<Short, GenericData> ITEMS_DATA_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private static Cache<Short, GenericData> EQUIPMENT_DATA_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private static Cache<Short, WeaponData> WEAPON_DATA_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private static Cache<Short, WeaponSkillData> WEAPON_SKILL_DATA_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    private static Cache<Short, MobTypeData> MOB_DATA_CACHE = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();


    public static WeaponData getWeaponData(Short id){
        if (WEAPON_DATA_CACHE.asMap().get(id) == null){
            WEAPON_DATA_CACHE.put(id, ServerDataFileReader.streamID(id));
        }
       return WEAPON_DATA_CACHE.getIfPresent(id);
    }

}
