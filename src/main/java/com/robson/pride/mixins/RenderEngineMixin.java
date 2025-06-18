package com.robson.pride.mixins;

import com.robson.pride.api.data.WeaponData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.client.events.engine.RenderEngine;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Map;

@Mixin(RenderEngine.class)
@OnlyIn(Dist.CLIENT)
public abstract class RenderEngineMixin {

    @Shadow @Final private Map<Item, RenderItemBase> itemRendererMapByInstance;

    @Shadow protected abstract RenderItemBase findMatchingRendererByClass(Class<?> clazz);

    @Shadow public abstract void init();

}
