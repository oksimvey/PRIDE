package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.skill.WeaponSkillData;

public interface WeaponSkillDataManager {

    DataManager<WeaponSkillData> MANAGER = new DataManager<>() {

        @Override
        protected WeaponSkillData getDefault(String value) {
            if (DataFileManager.validItem(DataFileManager.WEAPON_SKILLS, value)) {

            }
            return null;
        }
    };

}
