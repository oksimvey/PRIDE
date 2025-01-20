package com.robson.pride.api.utils;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.minecraft.world.entity.LivingEntity;

public class ManaUtils {

    public static float getMana(LivingEntity ent) {
        if (ent != null) {
            if (MagicData.getPlayerMagicData(ent) != null) {
                return MagicData.getPlayerMagicData(ent).getMana();
            }
        }
        return 0;
    }

    public static void setMana(LivingEntity ent, float amount) {
        if (ent != null) {
            if (MagicData.getPlayerMagicData(ent) != null) {
                MagicData.getPlayerMagicData(ent).setMana(amount);
            }
        }
    }

    public static void addMana(LivingEntity ent, float amount) {
        setMana(ent, (getMana(ent) + amount));
    }

    public static void consumeMana(LivingEntity ent, float amount) {
        setMana(ent, (getMana(ent) - amount));
    }
}
