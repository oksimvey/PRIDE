package com.robson.pride.api.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class EquipUtils {
    public static ItemStack locateItem(String item) {
        ItemStack itemstack = new ItemStack(Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)))).copy();
        itemstack.setCount(1);
        return itemstack;
    }

    public static void equipMainHand(LivingEntity ent, ItemStack item) {
        if (ent != null) {
            ent.setItemInHand(InteractionHand.MAIN_HAND, item);
        }
    }

    public static void equipMainHandByString(LivingEntity ent, String item) {
        equipMainHand(ent, locateItem(item));
    }

    public static void equipOffHand(LivingEntity ent, ItemStack item) {
        if (ent != null) {
            ent.setItemInHand(InteractionHand.OFF_HAND, item);
        }
    }

    public static void equipOffHandByString(LivingEntity ent, String item) {
        equipOffHand(ent, locateItem(item));
    }

    public static void equipArmorSet(LivingEntity ent, ItemStack head, ItemStack chest, ItemStack legs, ItemStack feet) {
        if (ent != null) {
            ent.setItemSlot(EquipmentSlot.HEAD, head);
            ent.setItemSlot(EquipmentSlot.CHEST, chest);
            ent.setItemSlot(EquipmentSlot.LEGS, legs);
            ent.setItemSlot(EquipmentSlot.FEET, feet);
        }
    }

    public static void equipArmorSetByString(LivingEntity ent, String head, String chest, String legs, String feet) {
        equipArmorSet(ent, locateItem(head), locateItem(chest), locateItem(legs), locateItem(feet));
    }
}