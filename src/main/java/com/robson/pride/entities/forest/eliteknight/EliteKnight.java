package com.robson.pride.entities.forest.eliteknight;

import com.robson.pride.api.ai.goals.InteractGoal;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.npc.JsonDialoguesReader;
import com.robson.pride.api.utils.EquipUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.Shogun;
import com.robson.pride.entities.pre_hardmode.japanese.mob.ronin.Ronin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class EliteKnight extends PrideMobBase {


    public EliteKnight(EntityType<? extends EliteKnight> type, Level world) {
        super(type, world);

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            this.setDropChance(slot, 0);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity attackTarget) {
                return this.mob.getBbWidth() * this.mob.getBbHeight();
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity pEnemy, double pDistToEnemySqr) {
                double eyeHeightDistToEnemySqr = this.mob.distanceToSqr(pEnemy.getX(), pEnemy.getY() - this.mob.getEyeHeight() + pEnemy.getEyeHeight(), pEnemy.getZ());
                super.checkAndPerformAttack(pEnemy, Math.min(pDistToEnemySqr, eyeHeightDistToEnemySqr * 0.8D));
            }
        });
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Ronin.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Shogun.class, true));
        this.goalSelector.addGoal(1, new InteractGoal(
                this,
                EliteKnight.class,
                1.5,
                10.0,
                200,
                0.25
        ));
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
