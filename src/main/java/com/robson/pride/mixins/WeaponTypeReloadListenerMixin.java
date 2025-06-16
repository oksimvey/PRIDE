package com.robson.pride.mixins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;

import java.util.Map;
import java.util.function.Function;

@Mixin(value = WeaponTypeReloadListener.class, remap = false)
public interface WeaponTypeReloadListenerMixin {

    @Accessor
    static Map<ResourceLocation, Function<Item, CapabilityItem.Builder>> getPRESETS() {
        throw new RuntimeException();
    }

}
