package com.robson.pride.mixins;

import com.google.common.collect.ImmutableMap;
import com.robson.pride.api.data.utils.DynamicDataBase;
import com.robson.pride.api.data.utils.DynamicDataParameter;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(value = AnimationManager.class, remap = false)
public class AnimationManagerMixin {



}
