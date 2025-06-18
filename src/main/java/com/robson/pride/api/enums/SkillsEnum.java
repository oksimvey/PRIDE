package com.robson.pride.api.enums;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;
import com.robson.pride.skills.weaponarts.DarknessCut;
import com.robson.pride.skills.weaponarts.FlameSlashSkill;
import com.robson.pride.skills.weaponarts.HeavensStrike;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public enum SkillsEnum implements SkillCore.SkillEnum {

    Darkness_Cut(new DarknessCut()),
    Flame_Slash(new FlameSlashSkill()),
    Longsword_Skill(new LongSwordWeaponSkill()),
    Heavens_Strike(new HeavensStrike());
    final WeaponSkillBase data;
    final int id;

    SkillsEnum(WeaponSkillBase data) {
        this.id = SkillCore.SkillEnum.ENUM_MANAGER.assign(this);
        this.data = data;
    }

    public static WeaponSkillBase get(String id){
        SkillsEnum skillsEnum;
        try {
            skillsEnum = SkillsEnum.valueOf(id);
            return skillsEnum.getWeaponSkill();
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }


    @Override
    public WeaponSkillBase getWeaponSkill() {
        return this.data;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
