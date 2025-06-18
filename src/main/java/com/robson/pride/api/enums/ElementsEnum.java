package com.robson.pride.api.enums;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.elements.*;
import net.minecraft.world.item.ItemStack;

public enum ElementsEnum implements ElementBase.ElementEnum {

    darkness(new DarknessElement()),
    light(new LightElement()),
    thunder(new ThunderElement()),
    sun(new SunElement()),
    moon(new MoonElement()),
    blood(new BloodElement()),
    wind(new WindElement()),
    nature(new NatureElement()),
    ice(new IceElement()),
    water(new WaterElement());

    final ElementBase element;
    final int id;

    ElementsEnum(ElementBase element) {
        this.id = ElementBase.ElementEnum.ENUM_MANAGER.assign(this);
        this.element = element;
    }

    public static ElementBase get(String id){
            ElementsEnum elementsEnum;
            try {
                elementsEnum = ElementsEnum.valueOf(id);
                return elementsEnum.getElement();
            }
            catch (IllegalArgumentException e) {
                return null;
            }
    }


    @Override
    public ElementBase getElement() {
        return this.element;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
