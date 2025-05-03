package com.robson.pride.api.animationtypes;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.entities.special.BulletProjectile;
import com.robson.pride.registries.EntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import reascer.wom.gameasset.WOMWeaponColliders;
import reascer.wom.world.entity.WOMEntities;
import reascer.wom.world.entity.projectile.EnderBullet;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.LinkAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.QuaternionUtils;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HurtableEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.effect.EpicFightMobEffects;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GunShootAnimation extends AttackAnimation {
    public GunShootAnimation(float convertTime, float antic, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, antic, antic, contact, recovery, collider, colliderJoint, path, armature);
    }

    public GunShootAnimation(float convertTime, float antic, float preDelay, float contact, float recovery, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
    }

    public GunShootAnimation(float convertTime, float antic, float contact, float recovery, InteractionHand hand, @Nullable Collider collider, Joint colliderJoint, String path, Armature armature) {
        this(convertTime, path, armature, new AttackAnimation.Phase(0.0F, antic, antic, contact, recovery, Float.MAX_VALUE, hand, colliderJoint, collider));
    }

    public GunShootAnimation(float convertTime, String path, Armature armature, boolean Coordsetter, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
    }

    public GunShootAnimation(float convertTime, String path, Armature armature, AttackAnimation.Phase... phases) {
        super(convertTime, path, armature, phases);
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addStateRemoveOld(EntityState.TURNING_LOCKED, false);
        this.addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_LOC_TARGET);
        this.addProperty(AnimationProperty.ActionAnimationProperty.COORD_SET_TICK, (MoveCoordFunctions.MoveCoordSetter)(self, entitypatch, transformSheet) -> {
            LivingEntity attackTarget = entitypatch.getTarget();
            if (!(Boolean)self.getRealAnimation().getProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && attackTarget != null) {
                TransformSheet transform = ((TransformSheet)self.getTransfroms().get("Root")).copyAll();
                Keyframe[] keyframes = transform.getKeyframes();
                int startFrame = 0;
                int endFrame = transform.getKeyframes().length - 1;
                Vec3f keyLast = keyframes[endFrame].transform().translation();
                Vec3 pos = ((LivingEntity)entitypatch.getOriginal()).getEyePosition();
                Vec3 targetpos = attackTarget.position().add(attackTarget.getDeltaMovement().scale((double)8.0F));
                float horizontalDistance = Math.max((float)targetpos.subtract(pos).horizontalDistance() * 1.2F - (attackTarget.getBbWidth() + ((LivingEntity)entitypatch.getOriginal()).getBbWidth()) * 0.8F, 0.0F);
                Vec3f worldPosition = new Vec3f(keyLast.x, 0.0F, -horizontalDistance);
                float scale = Math.min(worldPosition.length() / keyLast.length(), 2.0F);

                for(int i = startFrame; i <= endFrame; ++i) {
                    Vec3f translation = keyframes[i].transform().translation();
                    translation.z *= scale;
                }

                transformSheet.readFrom(transform);
            } else {
                transformSheet.readFrom((TransformSheet)self.getTransfroms().get("Root"));
            }

        });
        this.addProperty(AnimationProperty.StaticAnimationProperty.POSE_MODIFIER, (AnimationProperty.PoseModifier)(self, pose, entitypatch, time, partialticks) -> {
            if (self instanceof AttackAnimation) {
                float pitch = (float)Math.toDegrees(((LivingEntity)entitypatch.getOriginal()).getViewVector(1.0F).y);
                JointTransform armR = pose.getOrDefaultTransform("Arm_R");
                armR.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch)), OpenMatrix4f::mul);
                if (((AttackAnimation)self).getPhaseByTime(1.0F).colliders[0].getFirst() != Armatures.BIPED.armR) {
                    JointTransform armL = pose.getOrDefaultTransform("Arm_L");
                    armL.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch)), OpenMatrix4f::mul);
                }

                JointTransform chest = pose.getOrDefaultTransform("Chest");
                chest.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(pitch > 35.0F ? -pitch + 35.0F : 0.0F)), OpenMatrix4f::mul);
                JointTransform head = pose.getOrDefaultTransform("Head");
                head.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(pitch)), OpenMatrix4f::mul);
            }

        });
    }

    public void setLinkAnimation(DynamicAnimation fromAnimation, Pose pose, boolean isOnSameLayer, float timeModifier, LivingEntityPatch<?> entitypatch, LinkAnimation dest) {
        float extTime = Math.max(this.convertTime + timeModifier, 0.0F);
        if (entitypatch instanceof PlayerPatch<?> playerpatch) {
            AttackAnimation.Phase phase = this.getPhaseByTime(playerpatch.getAnimator().getPlayerFor(this).getElapsedTime());
            extTime *= this.getTotalTime() * playerpatch.getAttackSpeed(phase.getHand());
        }

        extTime = Math.max(extTime - this.convertTime, 0.0F);
        super.setLinkAnimation(fromAnimation, pose, isOnSameLayer, extTime, entitypatch, dest);
    }

    public Vec3 getCoordVector(LivingEntityPatch<?> entitypatch, DynamicAnimation dynamicAnimation) {
        Vec3 vec3 = super.getCoordVector(entitypatch, dynamicAnimation);
        if (entitypatch.shouldBlockMoving() && (Boolean)this.getProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE).orElse(true)) {
            vec3.scale((double)0.0F);
        }

        return vec3;
    }

    protected void hurtCollidingEntities(LivingEntityPatch<?> entitypatch, float prevElapsedTime, float elapsedTime, EntityState prevState, EntityState state, AttackAnimation.Phase phase) {
        LivingEntity entity = (LivingEntity)entitypatch.getOriginal();
        float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
        float poseTime = state.attacking() ? elapsedTime : phase.contact;
        List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entitypatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entitypatch, this));
        if (list.size() > 0) {
            HitEntityList hitEntities = new HitEntityList(entitypatch, list, (HitEntityList.Priority)phase.getProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY).orElse(HitEntityList.Priority.DISTANCE));
            int maxStrikes = this.getMaxStrikes(entitypatch, phase);

            while(entitypatch.getCurrenltyHurtEntities().size() < maxStrikes && hitEntities.next()) {
                Entity hitten = hitEntities.getEntity();
                LivingEntity trueEntity = this.getTrueEntity(hitten);
                if (trueEntity != null && trueEntity.isAlive() && !entitypatch.getCurrenltyAttackedEntities().contains(trueEntity) && !entitypatch.isTeammate(hitten) && (hitten instanceof LivingEntity || hitten instanceof PartEntity) && entity.hasLineOfSight(hitten)) {
                    HurtableEntityPatch<?> hitHurtableEntityPatch = (HurtableEntityPatch) EpicFightCapabilities.getEntityPatch(hitten, HurtableEntityPatch.class);
                    EpicFightDamageSource source = this.getEpicFightDamageSource(entitypatch, hitten, phase);
                    float anti_stunlock = 1.0F;
                    if (hitHurtableEntityPatch != null) {
                        if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).isPresent()) {
                            if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).get() == StunType.NONE) {
                                if (trueEntity instanceof Player) {
                                    source.setStunType(StunType.LONG);
                                    source.setImpact(source.getImpact() * 5.0F);
                                } else {
                                    source.setStunType(StunType.NONE);
                                }
                            } else if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).get() == StunType.HOLD && ((LivingEntity)hitHurtableEntityPatch.getOriginal()).hasEffect((MobEffect) EpicFightMobEffects.STUN_IMMUNITY.get())) {
                                source.setStunType(StunType.NONE);
                            } else if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).get() == StunType.FALL && ((LivingEntity)hitHurtableEntityPatch.getOriginal()).hasEffect((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get())) {
                                source.setStunType(StunType.NONE);
                            } else {
                                source = this.getEpicFightDamageSource(entitypatch, hitten, phase);
                            }
                        } else {
                            source = this.getEpicFightDamageSource(entitypatch, hitten, phase);
                        }

                        String replaceTag = "anti_stunlock:" + anti_stunlock + ":" + hitten.tickCount + ":" + this.getId() + "-" + phase.contact;
                        if (hitHurtableEntityPatch.isStunned()) {
                            for(String tag : hitten.getTags()) {
                                if (tag.contains("anti_stunlock:")) {
                                    anti_stunlock = this.applyAntiStunLock(hitten, anti_stunlock, source, phase, tag, replaceTag);
                                    break;
                                }
                            }
                        } else {
                            boolean firstAttack = true;

                            for(String tag : hitten.getTags()) {
                                if (tag.contains("anti_stunlock:")) {
                                    if ((float)hitten.tickCount - Float.valueOf(tag.split(":")[2]) > 20.0F) {
                                        anti_stunlock = 1.0F;
                                    } else {
                                        anti_stunlock = this.applyAntiStunLock(hitten, anti_stunlock, source, phase, tag, replaceTag);
                                        firstAttack = false;
                                    }
                                    break;
                                }
                            }

                            if (firstAttack) {
                                int i = 0;

                                while(i < hitten.getTags().size()) {
                                    if (((String)hitten.getTags().toArray()[i]).contains("anti_stunlock:")) {
                                        hitten.getTags().remove(hitten.getTags().toArray()[i]);
                                    } else {
                                        ++i;
                                    }
                                }

                                hitten.addTag(replaceTag);
                            }
                        }

                        if (anti_stunlock < (hitten instanceof Player ? 0.3F : 0.2F)) {
                            for(String tag : hitten.getTags()) {
                                if (tag.contains("anti_stunlock:")) {
                                    hitten.removeTag(tag);
                                    break;
                                }
                            }

                            source.setStunType(StunType.KNOCKDOWN);
                        }

                        source.setImpact(source.getImpact() * anti_stunlock);
                    }

                    int prevInvulTime = hitten.invulnerableTime;
                    hitten.invulnerableTime = 0;
                    AttackResult attackResult = entitypatch.attack(source, hitten, phase.hand);
                    hitten.invulnerableTime = prevInvulTime;
                    if (attackResult.resultType.dealtDamage()) {
                        if (source.getStunType() == StunType.KNOCKDOWN) {
                            ((LivingEntity)hitHurtableEntityPatch.getOriginal()).addEffect(new MobEffectInstance((MobEffect)EpicFightMobEffects.STUN_IMMUNITY.get(), 60, 0, true, false, false));
                        }

                        hitten.level().playSound((Player)null, hitten.getX(), hitten.getY(), hitten.getZ(), this.getHitSound(entitypatch, phase), hitten.getSoundSource(), 1.0F, 1.0F);
                        this.spawnHitParticle((ServerLevel)hitten.level(), entitypatch, hitten, phase);
                        if (hitHurtableEntityPatch != null && phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).isPresent()) {
                            if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).get() == StunType.NONE && !(trueEntity instanceof Player)) {
                                float stunTime = (float)((double)(source.getImpact() / anti_stunlock * 0.2F) * ((double)1.0F - trueEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                if (((LivingEntity)hitHurtableEntityPatch.getOriginal()).isAlive()) {
                                    hitHurtableEntityPatch.applyStun(anti_stunlock > 0.3F ? StunType.LONG : StunType.KNOCKDOWN, stunTime);
                                    float power = source.getImpact() / anti_stunlock * 0.25F;
                                    double d1 = entity.getX() - hitten.getX();

                                    double d0;
                                    for(d0 = entity.getZ() - hitten.getZ(); d1 * d1 + d0 * d0 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01) {
                                        d1 = (Math.random() - Math.random()) * 0.01;
                                    }

                                    if (!(trueEntity instanceof Player)) {
                                        power = (float)((double)power * ((double)1.0F - trueEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                    }

                                    if ((double)power > (double)0.0F) {
                                        hitten.hasImpulse = true;
                                        Vec3 vec3 = hitten.getDeltaMovement();
                                        Vec3 vec31 = (new Vec3(d1, (double)0.0F, d0)).normalize().scale((double)power);
                                        hitten.setDeltaMovement(vec3.x / (double)2.0F - vec31.x, hitten.onGround() ? Math.min(0.4, vec3.y / (double)2.0F) : vec3.y, vec3.z / (double)2.0F - vec31.z);
                                    }
                                }
                            }

                            if (phase.getProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE).get() == StunType.FALL) {
                                float stunTime = (float)((double)(source.getImpact() / anti_stunlock * 0.5F) * ((double)1.0F - trueEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
                                if (((LivingEntity)hitHurtableEntityPatch.getOriginal()).isAlive()) {
                                    hitHurtableEntityPatch.applyStun(anti_stunlock > 0.3F ? StunType.SHORT : StunType.KNOCKDOWN, stunTime);
                                    double power = (double)(source.getImpact() / anti_stunlock * 0.25F);
                                    double d1 = entity.getX() - hitten.getX();
                                    double d2 = entity.getY() - (double)8.0F - hitten.getY();

                                    double d0;
                                    for(d0 = entity.getZ() - hitten.getZ(); d1 * d1 + d0 * d0 < 1.0E-4; d0 = (Math.random() - Math.random()) * 0.01) {
                                        d1 = (Math.random() - Math.random()) * 0.01;
                                    }

                                    if (!(trueEntity instanceof Player)) {
                                        power *= (double)1.0F - trueEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
                                    }

                                    if (power > (double)0.0F) {
                                        hitten.hasImpulse = true;
                                        Vec3 vec3 = entity.getDeltaMovement();
                                        Vec3 vec31 = (new Vec3(d1, d2, d0)).normalize().scale(power);
                                        hitten.setDeltaMovement(vec3.x / (double)2.0F - vec31.x, vec3.y / (double)2.0F - vec31.y, vec3.z / (double)2.0F - vec31.z);
                                    }

                                    if (trueEntity instanceof Player) {
                                        trueEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 5, (int)(power * (double)4.0F * (double)5.0F), true, false, false));
                                    }
                                }
                            }
                        }
                    }

                    entitypatch.getCurrenltyAttackedEntities().add(trueEntity);
                    if (attackResult.resultType.shouldCount()) {
                        entitypatch.getCurrenltyHurtEntities().add(trueEntity);
                    }
                }
            }
        }

    }

    public void tick(LivingEntityPatch<?> entitypatch) {
        super.tick(entitypatch);
        if (!entitypatch.isLogicalClient() && AnimUtils.allowShoot(entitypatch.getOriginal())) {
            AnimationPlayer player = entitypatch.getAnimator().getPlayerFor(this);
            float elapsedTime = player.getElapsedTime();
            float prevElapsedTime = player.getPrevElapsedTime();
            EntityState state = this.getState(entitypatch, elapsedTime);
            EntityState prevState = this.getState(entitypatch, prevElapsedTime);
            AttackAnimation.Phase phase = this.getPhaseByTime(elapsedTime);
            if (state.getLevel() == 1 && !state.turningLocked() && entitypatch instanceof MobPatch) {
                ((Mob)entitypatch.getOriginal()).getNavigation().stop();
                ((LivingEntity)entitypatch.getOriginal()).attackAnim = 2.0F;
                LivingEntity target = entitypatch.getTarget();
                if (target != null) {
                    entitypatch.rotateTo(target, entitypatch.getYRotLimit(), false);
                }
            }

            if ((prevState.attacking() || state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2) && (!prevState.attacking() || phase != this.getPhaseByTime(prevElapsedTime) && (state.attacking() || prevState.getLevel() < 2 && state.getLevel() > 2))) {
                Level worldIn = ((LivingEntity)entitypatch.getOriginal()).level();
                float prevPoseTime = prevState.attacking() ? prevElapsedTime : phase.preDelay;
                float poseTime = state.attacking() ? elapsedTime : phase.contact;
                List<Entity> list = this.getPhaseByTime(elapsedTime).getCollidingEntities(entitypatch, this, prevPoseTime, poseTime, this.getPlaySpeed(entitypatch, this));
                List<Entity> list2 = new ArrayList(list);

                for(Entity entity : list) {
                    if (entity instanceof Projectile) {
                        list2.remove(entity);
                    } else if (!(entity instanceof LivingEntity)) {
                        list2.remove(entity);
                    }
                }

                if (list2.size() == 0) {
                    Joint joint = (Joint)phase.colliders[0].getFirst();
                    Collider collider = (Collider)phase.colliders[0].getSecond();
                    if (joint != Armatures.BIPED.head && joint != Armatures.BIPED.rootJoint && collider != WOMWeaponColliders.ENDER_LASER && collider != WOMWeaponColliders.ENDER_PISTOLERO && collider != WOMWeaponColliders.ENDER_DASH && collider != WOMWeaponColliders.ENDER_BULLET_WIDE) {
                        OpenMatrix4f transformMatrix = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(-0.5F), joint);
                        transformMatrix.translate(new Vec3f(0.0F, -0.6F, -0.3F));
                        OpenMatrix4f.mul((new OpenMatrix4f()).rotate(-((float)Math.toRadians((double)(((LivingEntity)entitypatch.getOriginal()).yRotO + 180.0F))), new Vec3f(0.0F, 1.0F, 0.0F)), transformMatrix, transformMatrix);
                        Vec3 direction = ((LivingEntity)entitypatch.getOriginal()).getViewVector(1.0F);
                        BulletProjectile projectile = new BulletProjectile((EntityType) EntityRegister.BULLET_PROJECTILE.get(), worldIn);
                        projectile.addTag("wom_enderbullet:" + phase.getHand().toString());
                        projectile.setOwner(entitypatch.getOriginal());
                        projectile.setPosRaw((double)transformMatrix.m30 + ((LivingEntity)entitypatch.getOriginal()).getX(), (double)transformMatrix.m31 + ((LivingEntity)entitypatch.getOriginal()).getY(), (double)transformMatrix.m32 + ((LivingEntity)entitypatch.getOriginal()).getZ());
                        projectile.shoot(direction.x, direction.y, direction.z, 2.5F, 0.0F);
                        projectile.life = entitypatch.getValidItemInHand(phase.getHand()).getEnchantmentLevel(Enchantments.PIERCING);
                        worldIn.addFreshEntity(projectile);
                    }
                }
            }
        }

    }

    public Pose getPoseByTime(LivingEntityPatch<?> entitypatch, float time, float partialTicks) {
        Pose pose = this.getRawPose(time);
        this.modifyPose(this, pose, entitypatch, time, partialTicks);
        float pitch = (float)Math.toDegrees(((LivingEntity)entitypatch.getOriginal()).getViewVector(1.0F).y);
        JointTransform armR = pose.getOrDefaultTransform("Arm_R");
        armR.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch)), OpenMatrix4f::mul);
        if (this.getPhaseByTime(partialTicks).colliders[0].getFirst() != Armatures.BIPED.armR) {
            JointTransform armL = pose.getOrDefaultTransform("Arm_L");
            armL.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch)), OpenMatrix4f::mul);
        }

        JointTransform chest = pose.getOrDefaultTransform("Chest");
        chest.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch * 0.5F)), OpenMatrix4f::mul);
        if (entitypatch instanceof PlayerPatch) {
            JointTransform head = pose.getOrDefaultTransform("Head");
            head.frontResult(JointTransform.getRotation(QuaternionUtils.XP.rotationDegrees(-pitch * 0.5F)), OpenMatrix4f::mul);
        }

        return pose;
    }

    public boolean isBasicAttackAnimation() {
        return false;
    }

    public float applyAntiStunLock(Entity hitten, float anti_stunlock, EpicFightDamageSource source, AttackAnimation.Phase phase, String tag, String replaceTag) {
        boolean isPhaseFromSameAnimnation = false;
        if (hitten.level().getBlockState(new BlockPos.MutableBlockPos(hitten.getX(), hitten.getY() - (double)1.0F, hitten.getZ())).isAir() && source.getStunType() != StunType.FALL) {
            if (tag.split(":").length > 3) {
                if (String.valueOf(this.getId()).equals(tag.split(":")[3].split("-")[0]) && !String.valueOf(phase.contact).equals(tag.split(":")[3].split("-")[1])) {
                    anti_stunlock = Float.valueOf(tag.split(":")[1]) * 1.0F;
                    isPhaseFromSameAnimnation = true;
                } else {
                    anti_stunlock = Float.valueOf(tag.split(":")[1]) * 0.9F;
                    isPhaseFromSameAnimnation = false;
                }
            }
        } else if (tag.split(":").length > 3) {
            if (String.valueOf(this.getId()).equals(tag.split(":")[3].split("-")[0]) && !String.valueOf(phase.contact).equals(tag.split(":")[3].split("-")[1])) {
                anti_stunlock = Float.valueOf(tag.split(":")[1]) * 1.0F;
                isPhaseFromSameAnimnation = true;
            } else {
                anti_stunlock = Float.valueOf(tag.split(":")[1]) * 0.8F;
                isPhaseFromSameAnimnation = false;
            }
        }

        hitten.removeTag(tag);
        int maxSavedAttack = 5;
        if (isPhaseFromSameAnimnation) {
            replaceTag = "anti_stunlock:" + anti_stunlock + ":" + hitten.tickCount;
            maxSavedAttack = 6;
        } else {
            replaceTag = "anti_stunlock:" + anti_stunlock + ":" + hitten.tickCount + ":" + this.getId() + "-" + phase.contact;
            maxSavedAttack = 5;
        }

        for(int i = 3; i < tag.split(":").length && i < maxSavedAttack; ++i) {
            replaceTag = replaceTag.concat(":" + tag.split(":")[i]);
        }

        hitten.addTag(replaceTag);
        return anti_stunlock;
    }
}
