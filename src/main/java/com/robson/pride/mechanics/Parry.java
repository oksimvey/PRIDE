package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Parry {

    public static void onParry(Entity ent, Entity ddmgent) {
        if (ent != null) {
            String BlockType = TagCheckUtils.checkBlockType((LivingEntity) ent);
            if (Objects.equals(BlockType, "mainhandshield")||Objects.equals(BlockType, "offhandshield")){
                onShieldParry(ent, ddmgent, BlockType);
            }
            if (Objects.equals(BlockType, "weapon")){
                onWeaponParry(ent, ddmgent);
            }
        }
    }

    public static void onShieldParry(Entity ent, Entity ddmgent, String BlockType) {
        StaminaUtils.StaminaConsume(ddmgent, 4);
       PlaySoundUtils.playSound(ent, "pride:shieldparry", 0.5f, 1f);
        if (Objects.equals(BlockType, "mainhandshield")) {
            AnimUtils.playAnim(ent, "pride:biped/combat/shield_parry1", 0.05F);
        }
        if (Objects.equals(BlockType, "offhandshield")) {
            AnimUtils.playAnim(ent, "pride:biped/combat/shield_parry2", 0.05F);
        }
    }

    public static void onWeaponParry(Entity ent, Entity ddmgent) {
        StaminaUtils.StaminaConsume(ddmgent, 3);
        if (ent instanceof Player) {
            StaminaUtils.StaminaAdd(ent, 0.1f * AttributeUtils.getAttributeValue((LivingEntity) ddmgent, "epicfight:impact"));
        }
    }

    public static void ParryWindow(Entity ent) {
        if (ent != null) {
            if (!(ent.getPersistentData().getBoolean("isParrying"))) {
                ent.getPersistentData().putBoolean("isParrying", true);
                TimerUtil.schedule(() -> ent.getPersistentData().putBoolean("isParrying", false), 325, TimeUnit.MILLISECONDS);
            }
        }
    }
}