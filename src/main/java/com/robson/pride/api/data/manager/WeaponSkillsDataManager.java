package com.robson.pride.api.data.manager;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponarts.*;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

public interface WeaponSkillsDataManager {

    byte DARKNESS_CUT = 1;

    byte HEAVENS_STRIKE = 2;

    byte FLAME_SLASH = 3;

    byte TORNADO = 4;

    byte GROUND_STOMP = 5;

    byte KICK = 6;

    byte LONGSWORD_PIERCE = 7;




    static WeaponSkillBase getByID(int id){
        return switch (id){

            case DARKNESS_CUT -> DarknessCut.DATA;

            case HEAVENS_STRIKE -> HeavensStrike.DATA;

            case FLAME_SLASH -> FlameSlashSkill.DATA;

            case TORNADO -> Tornado.DATA;

            case GROUND_STOMP -> GroundStomp.DATA;

            case KICK -> Kick.DATA;

            case LONGSWORD_PIERCE -> LongSwordWeaponSkill.DATA;

            default -> null;
        };
    }
}
