package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponarts.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeaponArtRegister {

    public static Map<String, WeaponSkillBase> WeaponArts =new LinkedHashMap<>();

    public static void registerWeaponArts(){
        WeaponArts.put("Darkness Cut", new DarknessCut());
        WeaponArts.put("Heaven's Strike", new HeavensStrike());
        WeaponArts.put("Flame Slash", new FlameSlashSkill());
        WeaponArts.put("Tornado", new Tornado());
        WeaponArts.put("Ground Stomp", new GroundStomp());
        WeaponArts.put("Kick", new Kick());
    }
}
