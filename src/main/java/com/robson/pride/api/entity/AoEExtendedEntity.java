package com.robson.pride.api.entity;

import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

public abstract class AoEExtendedEntity extends AoeEntity {

    public AoEExtendedEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

}
