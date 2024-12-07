package com.robson.pride.api.utils;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.LivingEntity;

public class SpellUtils {

    public static void castSpell(LivingEntity ent, AbstractSpell spell, int spelllvl, int castduration) {
        if (ent != null) {
            if (MagicData.getPlayerMagicData(ent) != null) {
                MagicData.getPlayerMagicData(ent).initiateCast(spell, spelllvl, castduration, MagicData.getPlayerMagicData(ent).getCastSource(), "mainhand");
            }
        }
    }
}
