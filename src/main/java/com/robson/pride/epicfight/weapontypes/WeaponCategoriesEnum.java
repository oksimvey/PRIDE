package com.robson.pride.epicfight.weapontypes;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.item.Item;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.function.Function;

public enum WeaponCategoriesEnum implements WeaponCategory, Function<Item, CapabilityItem.Builder> {
    PRIDE_LONGSWORD(new LongSwordWeaponSkill()),
    PRIDE_GREATSWORD(new LongSwordWeaponSkill()),
    PRIDE_COLOSSALSWORD(new LongSwordWeaponSkill()),
    PRIDE_GUN(new LongSwordWeaponSkill()),
    PRIDE_FIGHTNING_STYLE(new LongSwordWeaponSkill());
    final WeaponSkillBase skillBase;
    final int id;

    WeaponCategoriesEnum(WeaponSkillBase skillBase) {
        this.id = WeaponCategory.ENUM_MANAGER.assign(this);
        this.skillBase = skillBase;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }

    @Override
    public CapabilityItem.Builder apply(Item item) {
        return null;
    }

    public WeaponSkillBase skill() {
        return this.skillBase;
    }
}