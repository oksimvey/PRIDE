package com.robson.pride.api.ai.actions.combat;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.data.types.skill.WeaponSkillData;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final short skillid;

    public ExecuteWeaponSkillAction(short skillid) {
        this.skillid = skillid;
    }

    public void start(PrideMobPatch<?> ent) {
        WeaponSkillData skill = ServerDataManager.getWeaponSkillData(skillid);
        if (skill != null){
            skill.tryToExecute(ent.getOriginal());
        }
    }
}
