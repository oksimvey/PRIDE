package com.robson.pride.api.ai.actions.combat;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final short skillid;

    public ExecuteWeaponSkillAction(short skillid) {
        this.skillid = skillid;
    }

    public void start(PrideMobPatch<?> ent) {

    }
}
