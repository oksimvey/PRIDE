package com.robson.pride.epicfight.weapontypes;

import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public enum WeaponCategoriesEnum implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
    PRIDE_SHORTSWORD(),
    PRIDE_LONGSWORD(),
    PRIDE_GREATSWORD(),
    PRIDE_COLOSSALSWORD(),
    PRIDE_GUN(),
    PRIDE_AXE(),
    PRIDE_RAPIER(),
    PRIDE_KATANA(),
    PRIDE_SPEAR(),
    PRIDE_SCYTHE();
    final int id;

    WeaponCategoriesEnum() {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }

    @Override
    public CapabilityItem.Builder apply(Item item) {
        return null;
    }
}