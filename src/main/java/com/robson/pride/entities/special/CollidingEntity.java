package com.robson.pride.entities.special;

import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.registries.EntityRegister;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class CollidingEntity<T extends LivingEntity> extends AbstractHurtingProjectile {
    protected T original;
    public int life = 200;

    public CollidingEntity(EntityType<? extends CollidingEntity> p_37598_, Level p_37599_) {
        super(p_37598_, p_37599_);
    }


    public CollidingEntity(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
        super(EntityRegister.COLLIDING_ENTITY.get(), p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
    }

    public boolean isOnFire() {
        return false;
    }

    public void tick() {
        Entity entity = this.getOwner();
        if (!this.level().isClientSide() && entity != null && entity.isRemoved()) {
            this.discard();
        } else {
            if (this.shouldBurn()) {
                this.setSecondsOnFire(1);
            }

            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, (x$0) -> this.canHitEntity(x$0));
            if (hitresult.getType() != HitResult.Type.MISS && !ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            this.checkInsideBlocks();
            Vec3 vec3 = this.getDeltaMovement();
            double d0 = this.getX() + vec3.x;
            double d1 = this.getY() + vec3.y;
            double d2 = this.getZ() + vec3.z;
            ProjectileUtil.rotateTowardsMovement(this, 1.0F);
            float f = this.getInertia();
            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * (double) 0.25F, d1 - vec3.y * (double) 0.25F, d2 - vec3.z * (double) 0.25F, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double) f));
            this.level().addParticle(this.getTrailParticle(), d0 + (double) (((new Random()).nextFloat() - 0.5F) * 0.1F), d1 + (double) (((new Random()).nextFloat() - 0.5F) * 0.17F), d2 + (double) (((new Random()).nextFloat() - 0.5F) * 0.1F), (double) 0.0F, (double) 0.0F, (double) 0.0F);
            this.setPos(d0, d1, d2);
            if (Math.abs(this.getDeltaMovement().x) + Math.abs(this.getDeltaMovement().y) + Math.abs(this.getDeltaMovement().z) > (double) 100.0F || Math.abs(this.getDeltaMovement().x) + Math.abs(this.getDeltaMovement().y) + Math.abs(this.getDeltaMovement().z) < 0.1) {
                this.discard();
            }
        }

    }

    protected float getInertia() {
        return 1.5F;
    }

    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level().isClientSide()) {
            Entity entity = hitResult.getEntity();
            Entity entity1 = this.getOwner();
            HealthUtils.dealBlockableDmg(entity1, entity, 5);
        }

    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.DRAGON_BREATH;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide() && hitResult.getType() == HitResult.Type.BLOCK) {
            this.discard();
        }

    }

    public boolean isPickable() {
        return false;
    }

    public boolean hurt(DamageSource p_37616_, float p_37617_) {
        return false;
    }

    protected boolean shouldBurn() {
        return false;
    }
}
