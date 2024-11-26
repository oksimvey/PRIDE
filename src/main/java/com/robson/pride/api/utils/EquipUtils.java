package com.robson.pride.api.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class EquipUtils {
    public static ItemStack locateItem(String item){
        ItemStack itemstack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)))).copy();
        itemstack.setCount(1);
        return itemstack;
    }
    public static void equipMainHand(LivingEntity ent, String item){
        ent.setItemInHand(InteractionHand.MAIN_HAND, locateItem(item));
    }
    public static void equipOffHand(LivingEntity ent, String item){
        ent.setItemInHand(InteractionHand.OFF_HAND, locateItem(item));
    }
    public static void equipArmorSet(LivingEntity ent, String head, String chest, String legs, String feet) {
        ent.setItemSlot(EquipmentSlot.HEAD, locateItem(head));
        ent.setItemSlot(EquipmentSlot.CHEST, locateItem(chest));
        ent.setItemSlot(EquipmentSlot.LEGS, locateItem(legs));
        ent.setItemSlot(EquipmentSlot.FEET, locateItem(feet));
    }
}