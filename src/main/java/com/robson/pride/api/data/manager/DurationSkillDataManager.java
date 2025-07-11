package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.skill.DurationSkillData;
import com.robson.pride.skills.special.GuardSkill;

public interface DurationSkillDataManager {

    String GUARD = "Guard";

    DataManager<DurationSkillData> MANAGER = new DataManager<>() {

        @Override
        protected DurationSkillData getDefault(String value) {
            return switch (value){
                case GUARD -> GuardSkill.DATA;
                default -> null;
            };
        }
    };

}
