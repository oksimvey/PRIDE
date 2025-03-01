package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.EquipUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.TimeUnit;

public class Eating {

    public static void robTargetItem(Entity ent, Entity target, InteractionHand hand) {
        if (ent instanceof LivingEntity living && target instanceof LivingEntity targetl) {
            ItemStack itemStack = null;
            if (targetl.getMainHandItem().isEdible()) {
                itemStack = targetl.getMainHandItem();
                targetl.getMainHandItem().shrink(targetl.getMainHandItem().getCount() - 1);
            }
            if (targetl.getOffhandItem().isEdible()) {
                itemStack = targetl.getOffhandItem();
                targetl.getOffhandItem().setCount(targetl.getOffhandItem().getCount() - 1);
            }
        }
    }


    public static void mobEat(Entity ent, InteractionHand hand, float amount) {
        if (ent instanceof LivingEntity living) {
            if (hand == InteractionHand.MAIN_HAND) {
                EquipUtils.equipMainHandByString(living, living.getPersistentData().getString("itemtoequip"));
                TimerUtil.schedule(() -> {
                    if (living != null) {
                        FoodProperties foodProperties = living.getMainHandItem().getItem().getFoodProperties();
                        if (foodProperties != null) {
                            living.heal(foodProperties.getNutrition());
                            for (var effectPair : foodProperties.getEffects()) {
                                MobEffectInstance effectInstance = effectPair.getFirst();
                                living.addEffect(new MobEffectInstance(
                                        effectInstance.getEffect(),
                                        effectInstance.getDuration(),
                                        effectInstance.getAmplifier()
                                ));
                            }
                        }
                    }
                }, 500, TimeUnit.MILLISECONDS);
                TimerUtil.schedule(() -> {
                    if (living != null) {
                        EquipUtils.equipMainHandByString(living, living.getPersistentData().getString("previous_item"));
                    }
                }, 1250, TimeUnit.MILLISECONDS);
            }
            if (hand == InteractionHand.OFF_HAND) {
                EquipUtils.equipOffHandByString(living, living.getPersistentData().getString("itemtoequip"));
                TimerUtil.schedule(() -> {
                    if (living != null) {
                        living.getOffhandItem().finishUsingItem(living.level(), living);
                        ;
                    }
                }, 500, TimeUnit.MILLISECONDS);
                TimerUtil.schedule(() -> {
                    if (living != null) {
                        EquipUtils.equipOffHandByString(living, living.getPersistentData().getString("previous_item"));
                    }
                }, 1250, TimeUnit.MILLISECONDS);
            }
        }
    }
}
