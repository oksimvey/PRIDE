package com.robson.pride.api.data.manager;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponarts.*;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public interface WeaponSkillsDataManager extends DataManager<WeaponSkillBase> {

    byte DARKNESS_CUT = 0;

    byte HEAVENS_STRIKE = 1;

    byte FLAME_SLASH = 2;

    byte TORNADO = 3;

    byte GROUND_STOMP = 4;


    WeaponSkillsDataManager INSTANCE = id -> switch (id) {

        case DarknessCut.ID -> DarknessCut.DATA;

        case HeavensStrike.ID -> HeavensStrike.DATA;

        case FlameSlashSkill.ID -> FlameSlashSkill.DATA;

        case Tornado.ID -> Tornado.DATA;

        case GroundStomp.ID -> GroundStomp.DATA;

        case Kick.ID -> Kick.DATA;

        case LongSwordWeaponSkill.ID -> LongSwordWeaponSkill.DATA;

        default -> null;
    };
}
