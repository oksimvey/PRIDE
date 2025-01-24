package com.robson.pride.entities.thunderdomain.thundermage;

import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

import java.util.List;

public class ThunderMage extends PrideMobBase {

    public ThunderMage(EntityType<? extends ThunderMage> pEntityType, Level pLevel, List<String> skills, byte variant) {
        super(pEntityType, pLevel, "Thunder",variant);
    }
}
