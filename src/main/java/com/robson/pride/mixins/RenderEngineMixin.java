package com.robson.pride.mixins;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.client.events.engine.RenderEngine;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;

import java.util.Map;

@Mixin(RenderEngine.class)
@OnlyIn(Dist.CLIENT)
public abstract class RenderEngineMixin {

    @Shadow @Final private Map<Item, RenderItemBase> itemRendererMapByInstance;

    @Shadow protected abstract RenderItemBase findMatchingRendererByClass(Class<?> clazz);

    @Shadow public abstract void init();

}
