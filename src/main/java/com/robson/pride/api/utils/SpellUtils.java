package com.robson.pride.api.utils;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.world.entity.LivingEntity;

public class SpellUtils {



    public static void castSpell(LivingEntity ent, AbstractSpell spell, int spelllvl, int castduration) {
        if (ent != null) {
            MagicData magicData = MagicData.getPlayerMagicData(ent);
            if (magicData != null) {
                spell.onCast(ent.level(), spelllvl, ent, CastSource.NONE, magicData);
                spell.onServerCastComplete(ent.level(), spelllvl, ent, magicData, false);
            }
        }
    }
    public static void castMikiriSpell(LivingEntity ent, AbstractSpell spell, int spelllvl){
        if (ent != null){
            MagicData magicData = MagicData.getPlayerMagicData(ent);
            if (magicData != null){
                magicData.initiateCast(spell, spelllvl, 0, magicData.getCastSource(), "mainhand");
            }
        }
    }
}
