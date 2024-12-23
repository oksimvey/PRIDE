package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
import yesman.epicfight.world.damagesource.StunType;

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


        @SubscribeEvent
        public static void registerAnimations(AnimationRegistryEvent event){
        event.getRegistryMap().put(Pride.MOD_ID, AnimationsRegister::build);
    }

        private static void build() {

        HumanoidArmature biped = Armatures.BIPED;

            MIKIRI_JUMP = (new BasicAttackAnimation(0.05F, "biped/skill/mikiri_jump", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, biped.legL, ColliderPreset.FIST), new AttackAnimation.Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, biped.legL, ColliderPreset.FIST)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT.get()).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent)EpicFightSounds.BLUNT_HIT_HARD.get(), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.05F, 0.75F}));
            RECHARGE = (new LongHitAnimation(0f, "pride:biped/skill/recharge", biped));
            PROJECTILE_COUNTER = (new LongHitAnimation(0f, "pride:biped/skill/projectile_counter", biped));
            MIKIRI_STEP = (new LongHitAnimation(0.05F, "pride:biped/skill/mikiri_step",biped));
            KATANA_IDLE = (new StaticAnimation(true, "pride:biped/combat/katana/hold_bokken", biped));
            MOB_EAT_MAINHAND = new LongHitAnimation(0, "pride:biped/skill/mob_eat_mainhand", biped).addEvents(AnimationEvent.TimeStampedEvent.create(0.25F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(0.5F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(0.75F, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT)).addEvents(AnimationEvent.TimeStampedEvent.create(1, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.SERVER).params(SoundEvents.GENERIC_EAT));
                MOB_PERILOUS_TWO_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_two_hand",biped));
            MOB_PERILOUS_DUAL_WIELD = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_dual_wield",biped));
            MOB_PERILOUS_ONE_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/mob_perilous_pierce_one_hand",biped));
            PERILOUS_ONE_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_one_hand",biped)).addState(EntityState.TURNING_LOCKED, false)
                    .addState(EntityState.MOVEMENT_LOCKED, true)
                    .addState(EntityState.UPDATE_LIVING_MOTION, true)
                    .addState(EntityState.CAN_BASIC_ATTACK, false)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .addState(EntityState.INACTION, true)
                    .addState(EntityState.HURT_LEVEL, 0);
            PERILOUS_TWO_HAND = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_two_hand",biped)).addState(EntityState.TURNING_LOCKED, false)
                    .addState(EntityState.MOVEMENT_LOCKED, true)
                    .addState(EntityState.UPDATE_LIVING_MOTION, true)
                    .addState(EntityState.CAN_BASIC_ATTACK, false)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .addState(EntityState.INACTION, true)
                    .addState(EntityState.HURT_LEVEL, 0);
            PERILOUS_DUAL_WIELD = (new LongHitAnimation(0.25F, "pride:biped/skill/perilous_pierce_dual_wield",biped)).addState(EntityState.TURNING_LOCKED, false)
                    .addState(EntityState.MOVEMENT_LOCKED, true)
                    .addState(EntityState.UPDATE_LIVING_MOTION, true)
                    .addState(EntityState.CAN_BASIC_ATTACK, false)
                    .addState(EntityState.CAN_SKILL_EXECUTION, false)
                    .addState(EntityState.INACTION, true)
                    .addState(EntityState.HURT_LEVEL, 0);
            DUAL_TACHI_AUTO1 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_tachi/dual_tachi_auto1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.367F, 0.41F, 0.567F, 1.3F, InteractionHand.OFF_HAND, biped.toolL, (Collider)null), new AttackAnimation.Phase(0.2F, 0.633F, 0.68F, 0.767F, 1.3F, biped.toolR, (Collider)null)})).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);
            DUAL_TACHI_AUTO2 = (new BasicAttackAnimation(0.15F, "biped/combat/dual_tachi/dual_tachi_auto2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.63F, 0.667F, 0.667F, InteractionHand.MAIN_HAND, biped.toolR, (Collider)null), new AttackAnimation.Phase(0.2F, 0.7F, 0.8F, 0.9F, 1.3F, biped.toolL, (Collider)null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
            DUAL_TACHI_AUTO3 = (new BasicAttackAnimation(0.16F, "biped/combat/dual_tachi/dual_tachi_auto3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.66F, 0.69F, 0.733F, 1.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider)null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider)null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
            DUAL_TACHI_AUTO4 = (new BasicAttackAnimation(0.1F, "biped/combat/dual_tachi/dual_tachi_auto4", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.633F, 0.69F, 0.8F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider)null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider)null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG);
            DUAL_TACHI_SKILL1 = (new BasicAttackAnimation(0.05F, 0.9F, 1.05F, 1.5F, (Collider)null, biped.toolR, "biped/combat/dual_tachi/uchigatana_heavy1", biped)).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.9F).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(25.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addState(EntityState.MOVEMENT_LOCKED, true);
            DUAL_TACHI_SKILL2 = (new BasicAttackAnimation(0.05F, 1.2F, 1.35F, 1.5F, (Collider)null, biped.toolR, "biped/combat/dual_tachi/uchigatana_heavy2", biped)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.setter(25.0F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addState(EntityState.MOVEMENT_LOCKED, true);
        }
}
