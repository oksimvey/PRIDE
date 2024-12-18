package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Parry {

    public static void onParry(Entity ent, Entity ddmgent) {
        if (ent != null) {
            String BlockType = ItemStackUtils.checkBlockType(ent);
            if (Objects.equals(BlockType, "mainhandshield")||Objects.equals(BlockType, "offhandshield")){
                onShieldParry(ent, ddmgent, BlockType);
            }
            if (Objects.equals(BlockType, "weapon")){
                onWeaponParry(ent, ddmgent);
            }
        }
    }

    public static void onShieldParry(Entity ent, Entity ddmgent, String BlockType) {
        StaminaUtils.consumeStamina(ddmgent, 4);
       PlaySoundUtils.playSoundByString(ent, "pride:shieldparry", 0.5f, 1f);
        if (Objects.equals(BlockType, "mainhandshield")) {
            AnimUtils.playAnimByString(ent, "pride:biped/combat/shield_parry1", 0.05F);
        }
        if (Objects.equals(BlockType, "offhandshield")) {
            AnimUtils.playAnimByString(ent, "pride:biped/combat/shield_parry2", 0.05F);
        }
    }

    public static void onWeaponParry(Entity ent, Entity ddmgent) {
        float amount = 1f;
        StaminaUtils.consumeStamina(ddmgent, 3);
        if (ddmgent instanceof LivingEntity livingEntity){
            amount = AttributeUtils.getAttributeValue(livingEntity, "epicfight:impact");
        }
        StaminaUtils.addStamina(ent, 0.1f * amount);
    }

    public static void ParryWindow(Entity ent) {
        if (ent != null) {
            if (!(ent.getPersistentData().getBoolean("isParrying"))) {
                ent.getPersistentData().putBoolean("isParrying", true);
                TimerUtil.schedule(() -> ent.getPersistentData().putBoolean("isParrying", false), 350, TimeUnit.MILLISECONDS);
            }
        }
    }
}