package com.robson.pride.api.entity;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.item.weapons.CustomItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class PrideMob extends  Monster{

    public PrideMob(EntityType<? extends PrideMob> type, Level world) {
        super(type, world);
        this.setPersistenceRequired();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0);
        }
    }

    @Deprecated
    @org.jetbrains.annotations.ApiStatus.OverrideOnly
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
       this.setItemSlot(EquipmentSlot.MAINHAND, CustomItem.createItem(ServerDataManager.KURONAMI));
        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
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
