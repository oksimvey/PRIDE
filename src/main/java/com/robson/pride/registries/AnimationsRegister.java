package com.robson.pride.registries;

import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.main.Pride;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class AnimationsRegister {

    public static StaticAnimation MIKIRI_JUMP;
    public static StaticAnimation MIKIRI_STEP;
    public static StaticAnimation PERILOUS_TWO_HAND;
    public static StaticAnimation PERILOUS_DUAL_WIELD;
    public static StaticAnimation PERILOUS_ONE_HAND;
    public static StaticAnimation MOB_PERILOUS_TWO_HAND;
    public static StaticAnimation MOB_PERILOUS_DUAL_WIELD;
    public static StaticAnimation MOB_PERILOUS_ONE_HAND;
    public static StaticAnimation DUAL_TACHI_AUTO1;
    public static StaticAnimation DUAL_TACHI_AUTO2;
    public static StaticAnimation DUAL_TACHI_AUTO3;
    public static StaticAnimation DUAL_TACHI_AUTO4;
    public static StaticAnimation DUAL_TACHI_SKILL1;
    public static StaticAnimation DUAL_TACHI_SKILL2;
    public static StaticAnimation KATANA_IDLE;
    public static StaticAnimation RECHARGE;
    public static StaticAnimation PROJECTILE_COUNTER;
    public static StaticAnimation MOB_EAT_MAINHAND;
    public static StaticAnimation EXECUTE;
    public static StaticAnimation ELECTROCUTATE;
    public static StaticAnimation INFERNAL_AUTO_1;
    public static StaticAnimation INFERNAL_AUTO_2;
    public static StaticAnimation INFERNAL_AUTO_3_A;
    public static StaticAnimation INFERNAL_AUTO_4_A;
    public static StaticAnimation INFERNAL_AUTO_3_B;
    public static StaticAnimation INFERNAL_AUTO_3_B_CONTINUE_1;
    public static StaticAnimation INFERNAL_AUTO_3_B_CONTINUE_2;
    public static StaticAnimation INFERNAL_AUTO_3_B_CONTINUE_3;
    public static StaticAnimation INFERNAL_AUTO_3_B_CONTINUE_4;
    public static StaticAnimation INFERNAL_AUTO_4_B;
    public static StaticAnimation INFERNAL_KCIK13;
    public static StaticAnimation INFERNAL_FULLHOUSE;
    public static StaticAnimation INFERNAL_SHORYUKEN;
    public static StaticAnimation INFERNAL_IDLE;
    public static StaticAnimation INFERNAL_GUARD;
    public static StaticAnimation INFERNAL_GUARD_HIT;
    public static StaticAnimation INFERNAL_GUARD_PARRY;
    public static StaticAnimation INFERNAL_STRAIGHT_FLUSH;
    public static StaticAnimation MOB_AIM;
    public static StaticAnimation MOB_SHOOT;
    public static StaticAnimation MOB_SNEAK;

    public static ConcurrentHashMap<LivingEntity, Byte> pullLvl = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(Pride.MOD_ID, AnimationsRegister::build);
    }

    private static void build() {

        HumanoidArmature biped = Armatures.BIPED;

        MOB_SNEAK = (new MovementAnimation(0.15f, true,"biped/skill/mob_sneak", biped));

        MOB_AIM = (new LongHitAnimation(0.25f, "biped/combat/mob_aim", biped)).addEvents(AnimationEvent.TimeStampedEvent.create(0F, MOB_AIM_NBTS, AnimationEvent.Side.SERVER));

        MOB_SHOOT = (new LongHitAnimation(0, "biped/combat/mob_shoot", biped));
        INFERNAL_AUTO_1 = (new BasicAttackAnimation(0.1F, 0.3F, 0.4F, 0.5F, null, biped.toolL, "biped/combat/infernal_auto_1", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        INFERNAL_AUTO_2 = (new BasicAttackAnimation(0.2F, 0.1F, 0.2F, 0.25F, null, biped.toolR, "biped/combat/infernal_auto_2", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.45F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_3_A = (new BasicAttackAnimation(0.05F, 0.35F, 0.45F, 0.5F, null, biped.legL, "biped/combat/infernal_auto_3", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        INFERNAL_AUTO_4_A = (new BasicAttackAnimation(0.25F, 0.3F, 0.4F, 0.75F, null, biped.legR, "biped/combat/infernal_auto_4", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);
        INFERNAL_AUTO_3_B = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_3_b", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.4F, 0.45F, 0.49F, 0.49F, biped.legL, null), new AttackAnimation.Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F, biped.legL, null), new AttackAnimation.Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F, biped.legL, null), new AttackAnimation.Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F, biped.legL, null), new AttackAnimation.Phase(0.79F, 0.8F, 0.85F, 0.85F, Float.MAX_VALUE, biped.legL, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 2).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 3).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 4).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(1.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(INFERNAL_AUTO_4_B, 0.0F);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_3_B_CONTINUE_1 = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_3_b_continue", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.05F, 0.09F, 0.09F, biped.legL, null), new AttackAnimation.Phase(0.09F, 0.1F, 0.15F, 0.19F, 0.19F, biped.legL, null), new AttackAnimation.Phase(0.19F, 0.2F, 0.25F, 0.25F, Float.MAX_VALUE, biped.legL, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 2).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(INFERNAL_AUTO_4_B, 0.0F);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_3_B_CONTINUE_2 = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_3_b_continue", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.05F, 0.09F, 0.09F, biped.legL, null), new AttackAnimation.Phase(0.09F, 0.1F, 0.15F, 0.19F, 0.19F, biped.legL, null), new AttackAnimation.Phase(0.19F, 0.2F, 0.25F, 0.25F, Float.MAX_VALUE, biped.legL, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 2).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(INFERNAL_AUTO_4_B, 0.0F);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_3_B_CONTINUE_3 = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_3_b_continue", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.05F, 0.09F, 0.09F, biped.legL, null), new AttackAnimation.Phase(0.09F, 0.1F, 0.15F, 0.19F, 0.19F, biped.legL, null), new AttackAnimation.Phase(0.19F, 0.2F, 0.25F, 0.25F, Float.MAX_VALUE, biped.legL, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 2).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.4F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(INFERNAL_AUTO_4_B, 0.0F);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_3_B_CONTINUE_4 = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_3_b_continue", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.0F, 0.05F, 0.09F, 0.09F, biped.legL, null), new AttackAnimation.Phase(0.09F, 0.1F, 0.15F, 0.19F, 0.19F, biped.legL, null), new AttackAnimation.Phase(0.19F, 0.2F, 0.25F, 0.29F, 0.69F, biped.legL, null), new AttackAnimation.Phase(0.29F, 0.3F, 0.35F, 0.39F, 0.39F, biped.legL, null), new AttackAnimation.Phase(0.39F, 0.4F, 0.45F, 0.45F, Float.MAX_VALUE, biped.legL, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 2).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 3).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.3F), 4).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 2).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 3).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 4).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.0F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER), AnimationEvent.TimeStampedEvent.create(0.45F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(INFERNAL_AUTO_4_B, 0.0F);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_AUTO_4_B = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_auto_4_b", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.1F, 0.25F, 0.29F, 0.29F, biped.legL, null), new AttackAnimation.Phase(0.29F, 0.3F, 0.45F, 0.95F, Float.MAX_VALUE, biped.legR, null)})).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 1).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get()).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, (SoundEvent) EpicFightSounds.WHOOSH.get(), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.8F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.05F, (entitypatch, self, params) -> {
            if (entitypatch instanceof ServerPlayerPatch) {
                ((ServerPlayerPatch) entitypatch).modifyLivingMotionByCurrentItem(true);
            }

        }, AnimationEvent.Side.SERVER)});
        INFERNAL_KCIK13 = (new BasicAttackAnimation(0.05F, "biped/combat/infernal_kick13", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, biped.legL, null), new AttackAnimation.Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, biped.legR, null), new AttackAnimation.Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, biped.legL, null), new AttackAnimation.Phase(0.7F, 0.75F, 0.85F, 0.9F, 0.9F, biped.legR, null), new AttackAnimation.Phase(0.9F, 1.05F, 1.15F, 1.8F, Float.MAX_VALUE, biped.legR, null)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F), 1).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F), 2).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.25F), 3).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F), 4).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F), 4).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG, 4).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);

        INFERNAL_FULLHOUSE = (new BasicAttackAnimation(0.05F, 0.3F, 0.5F, 0.65F, null, biped.legR, "biped/combat/infernal_full_house", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.65F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.2F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.multiplier(4.0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT.get()).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackAnimationProperty.EXTRA_COLLIDERS, 20).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 0.3F}));

        INFERNAL_SHORYUKEN = (new SpecialAttackAnimation(0.1F, 0.3F, 0.65F, 0.95F, null, biped.toolR, "biped/skill/infernal_shoryuken", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F);
        INFERNAL_IDLE = new StaticAnimation(0.5F, true, "biped/living/infernal_idle", biped);
        INFERNAL_GUARD = new StaticAnimation(0.05F, true, "biped/skill/infernal_guard", biped);
        INFERNAL_GUARD_HIT = new GuardAnimation(0.05F, 0.2F, "biped/skill/infernal_guard_hit", biped);
        INFERNAL_GUARD_PARRY = new GuardAnimation(0.05F, 0.0F, "biped/skill/infernal_guard_parry", biped);
        INFERNAL_STRAIGHT_FLUSH = (new BasicAttackAnimation(0.05F, 0.05F, 0.15F, 0.25F, null, biped.toolR, "biped/skill/infernal_straight_flush", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(6.0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.4F);

        MIKIRI_JUMP = (new BasicAttackAnimation(0.05F, "biped/skill/mikiri_jump", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, biped.legL, ColliderPreset.FIST), new AttackAnimation.Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, biped.legL, ColliderPreset.FIST)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT.get()).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT_HARD.get(), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.05F, 0.75F}));
        RECHARGE = (new LongHitAnimation(0f, "pride:biped/skill/recharge", biped));
        ELECTROCUTATE = (new LongHitAnimation(0, "pride:biped/skill/electrocuted", biped));
        PROJECTILE_COUNTER = (new LongHitAnimation(0f, "pride:biped/skill/projectile_counter", biped));
        MIKIRI_STEP = (new LongHitAnimation(0.05F, "pride:biped/skill/mikiri_step", biped));
        KATANA_IDLE = (new StaticAnimation(true, "pride:biped/combat/katana/hold_bokken", biped));
        MOB_EAT_MAINHAND = new LongHitAnimation(0, "pride:biped/skill/mob_eat_mainhand", biped).addEvents(AnimationEvent.TimeStampedEvent.create(0.25F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(0.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(0.75F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(1, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT));
        MOB_PERILOUS_TWO_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_two_hand", biped));
        MOB_PERILOUS_DUAL_WIELD = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_dual_wield", biped));
        MOB_PERILOUS_ONE_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_one_hand", biped));
        PERILOUS_ONE_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_one_hand", biped)).addState(EntityState.TURNING_LOCKED, false)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.INACTION, true)
                .addState(EntityState.HURT_LEVEL, 0);
        PERILOUS_TWO_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_two_hand", biped)).addState(EntityState.TURNING_LOCKED, false)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.INACTION, true)
                .addState(EntityState.HURT_LEVEL, 0);
        PERILOUS_DUAL_WIELD = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_dual_wield", biped)).addState(EntityState.TURNING_LOCKED, false)
                .addState(EntityState.MOVEMENT_LOCKED, true)
                .addState(EntityState.UPDATE_LIVING_MOTION, true)
                .addState(EntityState.CAN_BASIC_ATTACK, false)
                .addState(EntityState.CAN_SKILL_EXECUTION, false)
                .addState(EntityState.INACTION, true)
                .addState(EntityState.HURT_LEVEL, 0);
        DUAL_TACHI_AUTO1 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_tachi/dual_tachi_auto1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.367F, 0.41F, 0.567F, 1.3F, InteractionHand.OFF_HAND, biped.toolL, (Collider) null), new AttackAnimation.Phase(0.2F, 0.633F, 0.68F, 0.767F, 1.3F, biped.toolR, (Collider) null)})).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);
        DUAL_TACHI_AUTO2 = (new BasicAttackAnimation(0.15F, "biped/combat/dual_tachi/dual_tachi_auto2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, biped.toolR, (Collider) null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider) null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        DUAL_TACHI_AUTO3 = (new BasicAttackAnimation(0.16F, "biped/combat/dual_tachi/dual_tachi_auto3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider) null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider) null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        DUAL_TACHI_AUTO4 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_tachi/dual_tachi_auto4", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider) null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider) null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG);
        DUAL_TACHI_SKILL1 = (new BasicAttackAnimation(0.05F, 0.9F, 1.05F, 1.5F, (Collider) null, biped.toolR, "biped/combat/dual_tachi/uchigatana_heavy1", biped)).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.9F).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(25.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addState(EntityState.MOVEMENT_LOCKED, true);
        DUAL_TACHI_SKILL2 = (new BasicAttackAnimation(0.05F, 1.2F, 1.35F, 1.5F, (Collider) null, biped.toolR, "biped/combat/dual_tachi/uchigatana_heavy2", biped)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(25.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addState(EntityState.MOVEMENT_LOCKED, true);
        EXECUTE = (new BasicAttackAnimation(0.1F, "biped/skill/execute", biped, new AttackAnimation.Phase(0.0F, 0.4F, 0.8F, 0.9F, 3.5F, InteractionHand.MAIN_HAND, biped.toolR, null), new AttackAnimation.Phase(0.9F, 2.5f, 3, 3.1f, 3.5F, biped.toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.EVISCERATE))).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5f).addState(EntityState.MOVEMENT_LOCKED, true);

    }

    public static final AnimationEvent.AnimationEventConsumer MOB_AIM_NBTS = (entitypatch, animation, params) -> {
        Entity ent = entitypatch.getOriginal();
        if (ent instanceof LivingEntity livingEntity) {
            TimerUtil.schedule(() -> {
                if (livingEntity != null) {
                    pullLvl.put(livingEntity, (byte) 1);
                    TimerUtil.schedule(() -> {
                        if (livingEntity != null) {
                            pullLvl.put(livingEntity, (byte) 2);
                            TimerUtil.schedule(() -> {
                                if (livingEntity != null) {
                                    pullLvl.put(livingEntity, (byte) 3);
                                    TimerUtil.schedule(() -> {
                                        if (livingEntity != null) {
                                            pullLvl.remove(livingEntity);
                                        }
                                    }, 200, TimeUnit.MILLISECONDS);
                                }
                            }, 200, TimeUnit.MILLISECONDS);
                        }
                    }, 200, TimeUnit.MILLISECONDS);
                }
            }, 200, TimeUnit.MILLISECONDS);
        }
    };
}
