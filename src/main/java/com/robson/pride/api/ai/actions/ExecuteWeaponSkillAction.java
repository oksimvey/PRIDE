package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.data.manager.DataManager;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.data.types.WeaponSkillData;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final short skillid;

    public ExecuteWeaponSkillAction(short skillid) {
        this.skillid = skillid;
    }

    protected void start(PrideMobPatch<?> ent) {
        WeaponSkillData skill = DataManager.getWeaponSkillData(skillid);
        if (skill != null){
            skill.tryToExecute(ent.getOriginal());
        }
    }
}
