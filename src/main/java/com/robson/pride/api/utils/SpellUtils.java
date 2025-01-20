package com.robson.pride.api.utils;

import com.robson.pride.entities.special.Shooter;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.TimeUnit;

public class SpellUtils {

    public static void castSpell(LivingEntity ent, AbstractSpell spell, int spelllvl, int castduration) {
        if (ent != null) {
            MagicData magicData = MagicData.getPlayerMagicData(ent);
            if (magicData != null) {
                spell.onCast(ent.level(), spelllvl, ent, CastSource.COMMAND, magicData);
                spell.onServerCastComplete(ent.level(), spelllvl, ent, magicData, false);
            }
        }
    }

    public static void castSpellFromShooter(Shooter shooter, AbstractSpell spell, int spellLevel) {
        castSpell(shooter, spell, spellLevel, 0);
        TimerUtil.schedule(() -> shooter.remove(Entity.RemovalReason.DISCARDED), 50, TimeUnit.MILLISECONDS);
    }
}
