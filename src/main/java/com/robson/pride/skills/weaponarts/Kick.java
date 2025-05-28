package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.LivingEntity;

public class Kick extends WeaponSkillBase {
    public Kick() {
        super("Common", "Neutral", 4, 1, 5000);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {

    }

    @Override
    public void onehandExecute(LivingEntity ent) {

    }
}
