package com.robson.pride.entities.special;

import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.EntityRegister;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.TimeUnit;

public class Shooter extends AbstractSpellCastingMob implements Enemy {
    private Entity owner;

    public Shooter(EntityType<? extends Shooter> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
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
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 40.0D);
    }

    public Entity getOwner() {
        return this.owner;
    }

    public static Vec3 getShooterLocation(Entity owner, Entity target, boolean isspell) {
        if (isspell) {
            return owner.getLookAngle().scale(owner.getBbHeight() * 1.5).add(0, target.getBbHeight() * 0.75, 0).add(owner.position());
        }
        return owner.getLookAngle().scale(owner.getBbHeight() * 0.5).add(0, owner.getBbHeight() * 0.9, 0).add(owner.position());
    }

    public static Shooter summonShooter(Entity owner, Entity target, boolean isspell) {
        Shooter shooter = EntityRegister.SHOOTER.get().create(owner.level());
        owner.level().addFreshEntity(shooter);
        shooter.noPhysics = true;
        shooterTick(owner, target, shooter, isspell);
        return shooter;
    }

    public static void shooterTick(Entity owner, Entity target, Shooter shooter, boolean isspell) {
        if (owner != null && shooter != null && target != null) {
            TimerUtil.schedule(() -> shooterTick(owner, target, shooter, isspell), 50, TimeUnit.MILLISECONDS);
            Vec3 lolcation = getShooterLocation(owner, target, isspell);
            shooter.teleportTo(lolcation.x, lolcation.y, lolcation.z);
            double dx = target.getX() - shooter.getX();
            double dz = target.getZ() - shooter.getZ();
            shooter.setYRot((float) (Math.atan2(dz, dx) * (180 / Math.PI)) - 90.0F);
            shooter.yBodyRot = shooter.getYRot();
            shooter.yHeadRot = shooter.getYRot();

        }
    }
}
