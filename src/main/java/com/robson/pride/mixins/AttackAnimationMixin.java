package com.robson.pride.mixins;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.ActionAnimation;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.BiFunction;

@Mixin(AttackAnimation.class)
public class AttackAnimationMixin extends ActionAnimation {

    public AttackAnimationMixin(float transitionTime, AnimationManager.AnimationAccessor<? extends ActionAnimation> accessor, AssetAccessor<? extends Armature> armature) {
        super(transitionTime, accessor, armature);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    protected void spawnHitParticle(ServerLevel world, LivingEntityPatch<?> attacker, Entity hit, AttackAnimation.Phase phase) {
        HitParticleType particle =  attacker.getWeaponHitParticle(phase.hand);
        particle.spawnParticleWithArgument(world, (BiFunction)null, (BiFunction)null, hit, attacker.getOriginal());
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    protected SoundEvent getHitSound(LivingEntityPatch<?> entitypatch, AttackAnimation.Phase phase) {
        return entitypatch.getWeaponHitSound(phase.hand);
    }
}
