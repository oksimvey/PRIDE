package com.robson.pride.api.enums;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;

public enum WeaponsEnum implements WeaponData.WeaponDataEnum {

    Kuronami(new Kuronami()),
    European_Longsword(new EuropeanLongsword());
    final WeaponData data;
    final int id;

    WeaponsEnum(WeaponData data) {
        this.id = WeaponData.WeaponDataEnum.ENUM_MANAGER.assign(this);
        this.data = data;
    }

    @Override
    public WeaponData getWeaponData() {
        return this.data;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
