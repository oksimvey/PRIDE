package com.robson.pride.entities.pre_hardmode.japanese.boss.shogun;

import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.EquipUtils;
import com.robson.pride.entities.forest.eliteknight.EliteKnight;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class Shogun extends PrideMobBase {
    public Entity target = this.getTarget();

    public Shogun(EntityType<? extends Shogun> type, Level world) {
        super(type, world, (byte) 0);

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
                .add(Attributes.ATTACK_DAMAGE, 40.0D)
                .add(Attributes.ARMOR, 100);
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