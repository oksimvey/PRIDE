package com.robson.pride.api.data;

import com.robson.pride.api.entity.PrideMobPatch;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;

public class MobData {
    public interface MobDataEnum extends ExtendableEnum {
        ExtendableEnumManager<MobDataEnum> ENUM_MANAGER = new ExtendableEnumManager("mob_data");
        PrideMobPatch getMobData();
    }
}
