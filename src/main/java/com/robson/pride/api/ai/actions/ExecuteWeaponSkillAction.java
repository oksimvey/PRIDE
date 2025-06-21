package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.skillcore.WeaponSkillBase;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final short skillid;

    public ExecuteWeaponSkillAction(short skillid) {
        this.skillid = skillid;
    }

    protected void start(PrideMobPatch<?> ent) {
        WeaponSkillBase skill = WeaponSkillsDataManager.INSTANCE.getByID(skillid);
        if (skill != null){
            skill.tryToExecute(ent.getOriginal());
        }
    }
}
