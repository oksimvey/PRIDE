package com.robson.pride.api.utils;

import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class ItemColiderUtil {

    public static void getWeaponColider(LivingEntity ent){
        if (ent != null){
            CapabilityItem item = EpicFightCapabilities.getItemStackCapability(ent.getMainHandItem());
        }
    }
}
