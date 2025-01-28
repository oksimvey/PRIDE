package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponarts.*;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

import java.util.*;

public class WeaponSkillRegister {

    public static Map<String, WeaponSkillBase> WeaponSkills = new HashMap<>();
    public static List<String> elements = Arrays.asList("Darkness", "Light", "Thunder", "Sun", "Moon", "Blood", "Wind", "Nature", "Ice", "Water", "Neutral");
    public static List<String> rarities = Arrays.asList("Mythical", "Legendary", "Epic", "Rare", "Uncommon", "Common");

    public static void registerWeaponArts(){
        WeaponSkills.put("Tornado", new Tornado());
        WeaponSkills.put("Ground Stomp", new GroundStomp());
        WeaponSkills.put("Flame Slash", new FlameSlashSkill());
        WeaponSkills.put("Darkness Cut", new DarknessCut());
        WeaponSkills.put("Kick", new Kick());
        WeaponSkills.put("Heaven's Strike", new HeavensStrike());
        WeaponSkills.put("Longsword Skill", new LongSwordWeaponSkill());
    }
}
