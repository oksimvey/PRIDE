package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.LivingEntity;

public class Tornado extends WeaponSkillBase {
    public Tornado() {
        super("Rare", "Wind", 25, 5, 3000);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {

    }

    @Override
    public void onehandExecute(LivingEntity ent) {

    }
}
