package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.maps.WeaponSkillsMap;
import com.robson.pride.api.skillcore.WeaponSkillBase;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final String skillid;

    public ExecuteWeaponSkillAction(String skillid) {
        this.skillid = skillid;
    }

    protected void start(PrideMobPatch<?> ent) {
        WeaponSkillBase skill = WeaponSkillsMap.WEAPON_SKILLS.get(skillid);
        if (skill != null){
            skill.tryToExecute(ent.getOriginal());
        }
    }
}
