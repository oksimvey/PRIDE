package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.ai.combat.Condition;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.maps.WeaponSkillsMap;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

import java.util.List;

public class ExecuteWeaponSkillAction extends ActionBase {

    private final String skillid;

    public ExecuteWeaponSkillAction(List<Condition> conditions, String skillid) {
        super(conditions);
        this.skillid = skillid;
    }

    protected void start(PrideMobPatch<?> ent) {
        WeaponSkillBase skill = WeaponSkillsMap.WEAPON_SKILLS.get(skillid);
        if (skill != null){
            skill.tryToExecute(ent.getOriginal());
        }
    }
}
