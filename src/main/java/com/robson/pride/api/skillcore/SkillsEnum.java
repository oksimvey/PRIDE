package com.robson.pride.api.skillcore;

import com.robson.pride.skills.weaponarts.DarknessCut;
import com.robson.pride.skills.weaponarts.FlameSlashSkill;

public enum SkillsEnum implements SkillCore.WeaponSkill {

    Darkness_Cut(new DarknessCut()),
    Flame_Slash(new FlameSlashSkill());

    final WeaponSkillBase skillBase;
    final int id;

    SkillsEnum(WeaponSkillBase skillBase) {
        this.skillBase = skillBase;
        this.id = SkillCore.WeaponSkill.ENUM_MANAGER.assign(this);
    }

    @Override
    public WeaponSkillBase skill() {
        return this.skillBase;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
