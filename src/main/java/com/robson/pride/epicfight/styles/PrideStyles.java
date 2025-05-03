package com.robson.pride.epicfight.styles;

import yesman.epicfight.world.capabilities.item.Style;

public enum PrideStyles implements Style {

    DUAL_WIELD(true),
    SHIELD_OFFHAND(true),
    GUN_OFFHAND(true);
    final boolean canUseOffhand;
    final int id;

    PrideStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }

    public int universalOrdinal() {
        return this.id;
    }

    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }

}