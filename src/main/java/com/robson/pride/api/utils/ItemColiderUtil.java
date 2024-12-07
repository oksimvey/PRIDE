package com.robson.pride.api.utils;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.OBBCollider;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class ItemColiderUtil {

    public static void getWeaponColider(LivingEntity ent){
        if (ent != null){
            CapabilityItem item = EpicFightCapabilities.getItemStackCapability(ent.getMainHandItem());
            Collider collider = item.getWeaponCollider();
        }
    }
}
