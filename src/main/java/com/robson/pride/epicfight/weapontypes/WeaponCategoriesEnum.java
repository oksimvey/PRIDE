package com.robson.pride.epicfight.weapontypes;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public enum WeaponCategoriesEnum implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
    PRIDE_LONGSWORD(),
    PRIDE_GREATSWORD(),
    PRIDE_COLOSSALSWORD(),
    PRIDE_GUN(),
    PRIDE_FIGHTNING_STYLE();
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

    public static ConcurrentHashMap<WeaponCategory, WeaponSkillBase> DefaultSkills = new ConcurrentHashMap<>();

    public static void registerDefaultSkills(){
        DefaultSkills.put(PRIDE_LONGSWORD, new LongSwordWeaponSkill());
    }
}