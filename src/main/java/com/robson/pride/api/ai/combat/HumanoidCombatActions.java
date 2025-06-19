package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;

import java.util.Map;

public class HumanoidCombatActions {

    private final Map<WeaponCategoryAndStyleCondition, CombatActions> motions;

    public HumanoidCombatActions(Map<WeaponCategoryAndStyleCondition, CombatActions> motions) {
        this.motions = motions;
    }

    public CombatActions getCombatActions(PrideMobPatch<?> ent){
        if (ent != null){
            for (WeaponCategoryAndStyleCondition condition : motions.keySet()) {
                if (condition.isTrue(ent)){
                    return motions.get(condition);
                }
            }
        }
        return null;
    }
}
