package com.robson.pride.skills.special;

import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.Entity;
import reascer.wom.gameasset.WOMAnimations;

public class KillerAuraSkill {

    public static void skillStart(Entity ent) {
        if (ent != null) {
            AnimUtils.playAnim(ent, WOMAnimations.TORMENT_BERSERK_CONVERT, 0);
        }
    }


}
