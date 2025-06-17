package com.robson.pride.api.enums;

import com.robson.pride.api.data.MobData;
import com.robson.pride.api.entity.PrideMobPatch;

public class MobsEnum implements MobData.MobDataEnum {

    ;
    final PrideMobPatch data;
    final int id;

    MobsEnum(PrideMobPatch data) {
        this.id = MobData.MobDataEnum.ENUM_MANAGER.assign(this);
        this.data = data;
    }

    @Override
    public PrideMobPatch getMobData() {
        return this.data;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
