package com.robson.pride.api.utils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class TagCheckUtils {
    public static boolean itemsTagCheck(ItemStack item, String tagname) {
        return item.is(ItemTags.create(new ResourceLocation("pride:" + tagname)));
    }

    public static boolean entityTagCheck(Entity ent, String tagname) {
        return ent.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("pride:" + tagname)));
    }
}