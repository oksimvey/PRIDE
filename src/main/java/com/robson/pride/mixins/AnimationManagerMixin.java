package com.robson.pride.mixins;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.robson.pride.api.data.utils.DynamicDataBase;
import com.robson.pride.api.data.utils.DynamicStaticMap;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Mixin(AnimationManager.class)
public interface AnimationManagerMixin {

    @Accessor("animationById")
     static Map<Integer, AnimationManager.AnimationAccessor<? extends StaticAnimation>> getAnimationById() {
        throw new RuntimeException();
    }

    @Accessor("animationByName")
    static Map<ResourceLocation, AnimationManager.AnimationAccessor<? extends StaticAnimation>> getAnimationByName() {
        throw new RuntimeException();
    }

    @Accessor("animations")
   static Map<AnimationManager.AnimationAccessor<? extends StaticAnimation>, StaticAnimation> getAnimations()
        {
            throw new RuntimeException();
        }


}
