package com.robson.pride.api.maps;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponarts.*;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;

import java.util.Map;

public interface SkillMap {

    Map<String, WeaponSkillBase> WEAPON_SKILLS = Map.ofEntries(
            Map.entry("Tornado", new Tornado()),
            Map.entry("Ground Stomp", new GroundStomp()),
            Map.entry("Flame Slash", new FlameSlashSkill()),
            Map.entry("Darkness Cut", new DarknessCut()),
            Map.entry("Kick", new Kick()),
            Map.entry("Heaven's Strike", new HeavensStrike()),
            Map.entry("Longsword Skill", new LongSwordWeaponSkill())
    );
}
