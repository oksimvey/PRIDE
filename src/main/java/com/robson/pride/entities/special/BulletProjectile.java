package com.robson.pride.entities.special;

import com.robson.pride.registries.EntityRegister;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.entity.WOMEntities;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;

public class BulletProjectile extends AbstractHurtingProjectile {
    public int life;

    public BulletProjectile(EntityType<? extends BulletProjectile> p_37598_, Level p_37599_) {
        super(p_37598_, p_37599_);
    }

    public BulletProjectile(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
        super((EntityType) EntityRegister.BULLET_PROJECTILE.get(), p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
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
                for(int i = 0; i < 4; ++i) {
                    this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * (double)0.25F, d1 - vec3.y * (double)0.25F, d2 - vec3.z * (double)0.25F, vec3.x, vec3.y, vec3.z);
                }

                f = 0.8F;
            }

            this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
            this.level().addParticle(this.getTrailParticle(), d0 + (double)(((new Random()).nextFloat() - 0.5F) * 0.1F), d1 + (double)(((new Random()).nextFloat() - 0.5F) * 0.17F), d2 + (double)(((new Random()).nextFloat() - 0.5F) * 0.1F), (double)0.0F, (double)0.0F, (double)0.0F);
            this.setPos(d0, d1, d2);
            if (Math.abs(this.getDeltaMovement().x) + Math.abs(this.getDeltaMovement().y) + Math.abs(this.getDeltaMovement().z) > (double)100.0F || Math.abs(this.getDeltaMovement().x) + Math.abs(this.getDeltaMovement().y) + Math.abs(this.getDeltaMovement().z) < 0.1) {
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
            PlayerPatch<?> playerpatch = (PlayerPatch) EpicFightCapabilities.getEntityPatch(this.getOwner(), PlayerPatch.class);
            if (entity1 instanceof LivingEntity && playerpatch != null) {
                LivingEntity livingentity = (LivingEntity)entity1;
                EpicFightDamageSource damage = playerpatch.getDamageSource(WOMAnimations.ENDERBLASTER_ONEHAND_SHOOT, InteractionHand.MAIN_HAND);
                damage.setStunType(StunType.HOLD);
                damage.setImpact(0.5F);
                damage.addRuntimeTag(EpicFightDamageType.WEAPON_INNATE);
                int prevInvulTime = entity.invulnerableTime;
                entity.invulnerableTime = 0;
                float entity1damage = 5.0F;
                float enchantmentDamage = 0.0F;
                if (entity instanceof LivingEntity) {
                    for(String tag : this.getTags()) {
                        if (tag.contains("wom_enderbullet")) {
                            if (tag.split(":")[1].equals(InteractionHand.MAIN_HAND.toString())) {
                                enchantmentDamage = (float)livingentity.getMainHandItem().getEnchantmentLevel(Enchantments.POWER_ARROWS);
                            } else {
                                enchantmentDamage = (float)livingentity.getOffhandItem().getEnchantmentLevel(Enchantments.POWER_ARROWS);
                            }
                        }
                    }

                    entity1damage *= 1.0F + enchantmentDamage / 5.0F;
                }

                entity.hurt(damage, entity1damage);
                  entity.invulnerableTime = prevInvulTime;
                if (this.life-- <= 0) {
                    this.discard();
                }
            } else {
                entity.hurt(this.damageSources().magic(), 6.0F);
            }
        }

    }

    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ASH;
    }

    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);

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
