package com.robson.pride.entities.gods;

import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class Wotanstrom extends PrideMobBase {

    public Wotanstrom(EntityType<? extends Wotanstrom> type, Level world) {
        super(type, world);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0);
        }
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 40.0D);
    }

    @Override
    public float getStepHeight() {
        return 1.2F;
    }


    @Override
    public double getMyRidingOffset() {
        return -2.5D;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected boolean canRide(Entity entity) {
        return false;
    }
}
