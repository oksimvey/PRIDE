package com.robson.pride.api.ai.combat;

import com.robson.pride.api.entity.PrideMobPatch;
import jdk.jfr.Category;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

public class WeaponCategoryAndStyleCondition extends Condition {

    private final WeaponCategory category;

    private final Style style;

    public WeaponCategoryAndStyleCondition(WeaponCategory category, Style style){
        this.category = category;
        this.style = style;
    }

    public boolean isTrue(PrideMobPatch<?> ent){
        return ent.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == this.category &&
                ent.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(ent) == this.style;
    }
}
