package com.robson.pride.api.entity;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.MobTypeData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Random;

public class PrideMob extends Monster {

    private static final EntityDataAccessor<Integer> TYPE =
            SynchedEntityData.defineId(PrideMob.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Byte> VARIANT =
            SynchedEntityData.defineId(PrideMob.class, EntityDataSerializers.BYTE);

    public PrideMob(EntityType<? extends PrideMob> type, Level world) {
        super(type, world);
        this.setPersistenceRequired();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0);
        }
    }

    public void setType(int type){
        this.entityData.set(TYPE, type);
    }

    public void setTypeVariant(byte variant){
        this.entityData.set(VARIANT, variant);
    }

    @Deprecated
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        MobTypeData data = ServerDataManager.getMobType(this.getTypeID());
        if (data != null){
            setTypeVariant((byte) new Random().nextInt(data.getMaxVariants()));

        }
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
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(VARIANT, tag.getByte("variant"));
        this.entityData.set(TYPE, tag.getInt("type"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("variant", this.getTypeVariant());
        tag.putInt("type", this.getTypeID());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, (byte) 0);
        this.entityData.define(TYPE, 0);
    }

    public short getTypeID(){
        return (short)(int)(this.entityData.get(TYPE));
    }

    public byte getTypeVariant() {
        return this.entityData.get(VARIANT);
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
