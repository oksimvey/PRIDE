package com.robson.pride.entities.forest.eliteknight;

import com.robson.pride.api.ai.goals.JsonGoalsReader;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.utils.EquipUtils;
import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.Shogun;
import com.robson.pride.entities.pre_hardmode.japanese.mob.ronin.Ronin;
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

public class EliteKnight extends PrideMobBase {


    public EliteKnight(EntityType<? extends EliteKnight> type, Level world) {
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

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(accessor, difficulty, reason, spawnDataIn, dataTag);
        EquipUtils.equipMainHandByString(this, "pride:european_longsword");
        EquipUtils.equipOffHandByString(this, "minecraft:shield");
        EquipUtils.equipArmorSetByString(this, "jet_and_elias_armors:elite_sentinel_set_helmet", "jet_and_elias_armors:elite_sentinel_set_chestplate", "jet_and_elias_armors:sentinel_set_leggings", "jet_and_elias_armors:sentinel_set_boots");

        return data;
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
