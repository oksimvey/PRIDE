package com.robson.pride.registries;

import com.robson.pride.api.animationtypes.GunShootAnimation;
import com.robson.pride.api.utils.*;
import com.robson.pride.main.Pride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.stringtemplate.v4.ST;
import reascer.wom.animation.attacks.EnderblasterShootAttackAnimation;
import reascer.wom.animation.attacks.SpecialAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMWeaponColliders;
import reascer.wom.particle.WOMParticles;
import reascer.wom.world.damagesources.WOMExtraDamageInstance;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.Set;
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
    public static StaticAnimation DIVINE_RESURRECTION;
    public static StaticAnimation SHORTSWORD_AUTO1;
    public static StaticAnimation SHORTSWORD_AUTO2;
    public static StaticAnimation SHORTSWORD_AUTO3;
    public static StaticAnimation SHORTSWORD_AUTO4;
    public static StaticAnimation SHORTSWORD_DUAL_AUTO1;
    public static StaticAnimation SHORTSWORD_DUAL_AUTO2;
    public static StaticAnimation SHORTSWORD_DUAL_AUTO3;
    public static StaticAnimation GREATSWORD_HOLD;
    public static StaticAnimation GREATSWORD_RUN;
    public static StaticAnimation SWORD_ONEHAND_AUTO1;
    public static StaticAnimation SWORD_ONEHAND_AUTO2;
    public static StaticAnimation SWORD_ONEHAND_AUTO3;
    public static StaticAnimation SWORD_ONEHAND_AUTO4;
    public static StaticAnimation MAUL_HOLD;
    public static StaticAnimation SPEAR_ONEHAND;
    public static StaticAnimation DUAL_GS_IDLE;
    public static StaticAnimation DUAL_GS_WALK;
    public static StaticAnimation DUAL_GS_RUN;
    public static StaticAnimation DUAL_GREATSWORD_AUTO1;
    public static StaticAnimation DUAL_GREATSWORD_AUTO2;
    public static StaticAnimation DUAL_GREATSWORD_AUTO3;
    public static StaticAnimation DUAL_GREATSWORD_AUTO4;
    public static StaticAnimation DUAL_GREATSWORD_DASH;
    public static StaticAnimation DUAL_GREATSWORD_AIRSLASH;
    public static StaticAnimation DUAL_GREATSWORD_SKILL;
    public static StaticAnimation LION_CLOW;
    public static StaticAnimation GREAT_TACHI_AUTO1;
    public static StaticAnimation GREAT_TACHI_AUTO2;
    public static StaticAnimation GREAT_TACHI_AUTO3;
    public static StaticAnimation GREAT_TACHI_AUTO4;
    public static StaticAnimation DUAL_TACHI_AIRSLASH;
    public static StaticAnimation DUAL_SCYTHE_AUTO1;
    public static StaticAnimation DUAL_SCYTHE_AUTO2;
    public static StaticAnimation DUAL_SCYTHE_AUTO3;
    public static StaticAnimation DUAL_SCYTHE_AUTO4;
    public static StaticAnimation ONEHAND_SHOOT;
    public static StaticAnimation DUAL_WIELD_SHOOT_RIGHT;
    public static StaticAnimation DUAL_WIELD_SHOOT_LEFT;
    public static StaticAnimation LEFT_SHOOT;

    @SubscribeEvent
    public static void registerAnimations(AnimationRegistryEvent event) {
        event.getRegistryMap().put(Pride.MOD_ID, AnimationsRegister::build);
    }

    private static void build() {

        HumanoidArmature biped = Armatures.BIPED;
        ONEHAND_SHOOT = (new GunShootAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ENDER_SHOOT, biped.toolR, "biped/combat/gun/enderblaster_onehand_shoot", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation("pride:dry_fire"))).get()).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(new float[]{0.4F}))).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.FIREWORK_ROCKET_BLAST).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false).newTimePair(0.0F, 0.5F).addStateRemoveOld(EntityState.MOVEMENT_LOCKED, false).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.05F, SHOOT_RIGHT, AnimationEvent.Side.CLIENT)});
        DUAL_WIELD_SHOOT_RIGHT = (new GunShootAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ENDER_SHOOT, biped.toolR, "biped/combat/gun/enderblaster_twohand_shoot_right", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation("pride:dry_fire"))).get()).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(new float[]{0.4F}))).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.FIREWORK_ROCKET_BLAST).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false).newTimePair(0.0F, 0.5F).addStateRemoveOld(EntityState.MOVEMENT_LOCKED, false).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.05F, SHOOT_RIGHT, AnimationEvent.Side.CLIENT)});
        DUAL_WIELD_SHOOT_LEFT = (new GunShootAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ENDER_SHOOT, biped.toolL, "biped/combat/gun/enderblaster_twohand_shoot_left", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation("pride:dry_fire"))).get()).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(new float[]{0.4F}))).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.FIREWORK_ROCKET_BLAST).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false).newTimePair(0.0F, 0.5F).addStateRemoveOld(EntityState.MOVEMENT_LOCKED, false).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.05F, SHOOT_LEFT, AnimationEvent.Side.CLIENT)});
        LEFT_SHOOT = (new GunShootAnimation(0.05F, 0.05F, 0.1F, 0.5F, WOMWeaponColliders.ENDER_SHOOT, biped.toolL, "biped/combat/gun/antitheus_shoot", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.SWING_SOUND, Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation("pride:dry_fire"))).get()).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(WOMExtraDamageInstance.WOM_SWEEPING_EDGE_ENCHANTMENT.create(new float[]{0.4F}))).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, SoundEvents.FIREWORK_ROCKET_BLAST).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.WEAPON_INNATE)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 1.0F).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false).newTimePair(0.0F, 0.5F).addStateRemoveOld(EntityState.MOVEMENT_LOCKED, false).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.05F, SHOOT_LEFT, AnimationEvent.Side.CLIENT)});
        DUAL_TACHI_AIRSLASH = new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.5F, ColliderPreset.DUAL_SWORD_AIR_SLASH, biped.torso, "biped/combat/scythe/dual_tachi_airslash", biped);
        DUAL_SCYTHE_AUTO1 = (new BasicAttackAnimation(0.15F, "biped/combat/scythe/dual_tachi_auto1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.3F, 0.45F, 0.5F, 0.7F, InteractionHand.MAIN_HAND, biped.toolR, (Collider) null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F);
        DUAL_SCYTHE_AUTO2 = (new BasicAttackAnimation(0.05F, "biped/combat/scythe/dual_tachi_auto2", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.2F, 0.3F, 0.5F, 0.5F, biped.toolR, (Collider) null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.7F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.5F));
        DUAL_SCYTHE_AUTO3 = (new BasicAttackAnimation(0.16F, "biped/combat/scythe/dual_tachi_auto3", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.45F, 0.45F, 0.45F, InteractionHand.MAIN_HAND, biped.toolR, (Collider) null)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F)), new AttackAnimation.Phase(0.25F, 0.25F, 0.35F, 0.9F, 0.9F, 0.9F, biped.toolL, (Collider) null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.0F));
        DUAL_SCYTHE_AUTO4 = (new BasicAttackAnimation(0.1F, "biped/combat/scythe/dual_tachi_auto4", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.5F, 0.65F, 1.1F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider) null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider) null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG);
        GREAT_TACHI_AUTO1 = (new BasicAttackAnimation(0.1F, 0.25F, 0.5F, 0.5F, (Collider) null, biped.toolR, "biped/combat/katana/great_tachi_auto1", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F));
        GREAT_TACHI_AUTO2 = (new BasicAttackAnimation(0.1F, 0.25F, 0.5F, 0.55F, (Collider) null, biped.toolR, "biped/combat/katana/great_tachi_auto2", biped)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(2.0F)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F);
        GREAT_TACHI_AUTO3 = (new BasicAttackAnimation(0.1F, "biped/combat/katana/great_tachi_auto3", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.1F, 0.15F, 0.3F, 0.35F, 0.35F, biped.toolR, (Collider) null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.75F)), new AttackAnimation.Phase(0.35F, 0.4F, 0.45F, 0.6F, 0.75F, 0.75F, biped.toolR, (Collider) null)})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(1.5F));
        GREAT_TACHI_AUTO4 = (new BasicAttackAnimation(0.1F, 0.5F, 0.95F, 1.5F, (Collider) null, biped.toolR, "biped/combat/katana/great_tachi_auto4", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.3F).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(5.0F));
        DUAL_GREATSWORD_AUTO1 = (new BasicAttackAnimation(0.25F, "biped/combat/colossalsword/greatsword_dual_auto_1", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, InteractionHand.OFF_HAND, biped.toolL, (Collider) null), (new AttackAnimation.Phase(0.45F, 0.5F, 0.7F, 0.8F, Float.MAX_VALUE, biped.toolR, (Collider) null)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.7F), 1).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_AUTO2 = (new BasicAttackAnimation(0.15F, 0.35F, 0.85F, 0.85F, null, biped.toolR, "biped/combat/colossalsword/greatsword_dual_auto_2", biped)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.FALL).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0.8F)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.85F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -1.0F, 2.0F), Armatures.BIPED.rootJoint, (double) 1.5F, 0.55F})});
        DUAL_GREATSWORD_AUTO3 = (new BasicAttackAnimation(0.15F, "biped/combat/colossalsword/greatsword_dual_auto_3", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 0.2F, 0.4F, 0.45F, 0.45F, biped.toolR, (Collider) null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F)), (new AttackAnimation.Phase(0.45F, 0.55F, 0.7F, 0.9F, Float.MAX_VALUE, InteractionHand.OFF_HAND, biped.toolL, (Collider) null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD)})).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(3.5F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F), 1).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(0.8F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -0.0F, -2.0F), Armatures.BIPED.rootJoint, 1.15, 0.55F})});
        DUAL_GREATSWORD_AUTO4 = (new BasicAttackAnimation(0.1F, 0.8F, 1.0F, 1.25F, InteractionHand.OFF_HAND, null, biped.rootJoint, "biped/combat/colossalsword/greatsword_dual_auto_4", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_DASH = (new BasicAttackAnimation(0.05F, 0.1F, 0.4F, 0.4F, null, biped.rootJoint, "biped/combat/colossalsword/greatsword_dual_dash", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.2F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT.get()).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_AIRSLASH = (new BasicAttackAnimation(0.05F, 0.25F, 0.4F, 0.45F, InteractionHand.OFF_HAND, null, biped.rootJoint, "biped/combat/colossalsword/greatsword_dual_airslash", biped)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F)).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageType.FINISHER)).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, 0.2F})).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DUAL_GREATSWORD_SKILL = (new AttackAnimation(0.15F, "biped/combat/colossalsword/greatsword_dual_earthquake", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 1.1F, 1.1F, 1.25F, 1.25F, biped.toolR, null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.5F)), (new AttackAnimation.Phase(1.25F, 1.3F, 1.4F, 1.5F, Float.MAX_VALUE, biped.rootJoint, null)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))})).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(1.3F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.rootJoint, 2.55, 0.55F})});
        LION_CLOW = (new AttackAnimation(0.15F, "biped/combat/colossalsword/lion_clow", biped, new AttackAnimation.Phase[]{(new AttackAnimation.Phase(0.0F, 2.1F, 2.4F, 2.45F, 2.45F, biped.toolR, null)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.2F))})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.05F).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addState(EntityState.MOVEMENT_LOCKED, true).addEvents(new AnimationEvent.TimeStampedEvent[]{AnimationEvent.TimeStampedEvent.create(2.3F, Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.CLIENT).params(new Object[]{new Vec3f(0.0F, -0.24F, -2.0F), Armatures.BIPED.rootJoint, 2.55, 0.55F})});
        DUAL_GS_IDLE = new StaticAnimation(true, "pride:biped/combat/colossalsword/greatsword_dual_idle", biped);
        DUAL_GS_WALK = new MovementAnimation(true, "pride:biped/combat/colossalsword/greatsword_dual_walk", biped);
        DUAL_GS_RUN = new MovementAnimation(true, "pride:biped/combat/colossalsword/greatsword_dual_run", biped);
        SPEAR_ONEHAND = new BasicAttackAnimation(0.16F, 0.1F, 0.2F, 0.45F, (Collider) null, biped.toolR, "biped/combat/maul/spear_onehand_auto", biped);
        MAUL_HOLD = new StaticAnimation(true, "pride:biped/combat/maul/hold_maul", biped);
        GREATSWORD_HOLD = (new StaticAnimation(true, "pride:biped/combat/greatsword/greatsword_dual_idle", biped));
        GREATSWORD_RUN = (new MovementAnimation(true, "pride:biped/combat/greatsword/greatsword_dual_run", biped));
        SHORTSWORD_DUAL_AUTO1 = (new BasicAttackAnimation(0.08F, 0.1F, 0.2F, 0.3F, (Collider) null, biped.toolR, "biped/combat/shortsword/sword_dual_auto1", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).newTimePair(0.0F, 0.2F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false);
        SHORTSWORD_DUAL_AUTO2 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, InteractionHand.OFF_HAND, (Collider) null, biped.toolL, "biped/combat/shortsword/sword_dual_auto2", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F).newTimePair(0.0F, 0.2F).addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false);
        SHORTSWORD_DUAL_AUTO3 = (new BasicAttackAnimation(0.1F, "biped/combat/shortsword/sword_dual_auto3", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.25F, 0.25F, 0.35F, 0.6F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(biped.toolR, (Collider) null), AttackAnimation.JointColliderPair.of(biped.toolL, (Collider) null)})})).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F);
        SHORTSWORD_AUTO1 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, (Collider) null, biped.toolR, "biped/combat/shortsword/tachi_twohand_auto_1", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        SHORTSWORD_AUTO2 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.3F, (Collider) null, biped.toolR, "biped/combat/shortsword/tachi_twohand_auto_2", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F);
        SHORTSWORD_AUTO3 = (new BasicAttackAnimation(0.1F, 0.1F, 0.2F, 0.45F, (Collider) null, biped.toolR, "biped/combat/shortsword/tachi_twohand_auto_3", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true);
        SHORTSWORD_AUTO4 = (new BasicAttackAnimation(0.1F, 0.2F, 0.3F, 0.65F, (Collider) null, biped.toolR, "biped/combat/shortsword/tachi_twohand_auto_4", biped)).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.5F).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.adder(1.25F));
        SWORD_ONEHAND_AUTO1 = (new BasicAttackAnimation(0.15F, 0.15F, 0.4F, 0.4F, (Collider) null, biped.toolR, "biped/combat/greatsword/sword_onehand_auto_1", biped)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO2 = (new BasicAttackAnimation(0.15F, 0.15F, 0.25F, 0.4F, (Collider) null, biped.toolR, "biped/combat/greatsword/sword_onehand_auto_2", biped)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO3 = (new BasicAttackAnimation(0.12F, 0.1F, 0.42F, 0.45F, (Collider) null, biped.toolR, "biped/combat/greatsword/sword_onehand_auto_3", biped)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        SWORD_ONEHAND_AUTO4 = (new BasicAttackAnimation(0.1F, 0.15F, 0.35F, 0.6F, (Collider) null, biped.toolR, "biped/combat/greatsword/sword_onehand_auto_4", biped)).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE);
        DIVINE_RESURRECTION = (new LongHitAnimation(0.25f, "biped/skill/divine_resurrection", biped));
        MOB_SNEAK = (new MovementAnimation(0.15f, true, "biped/skill/mob_sneak", biped));
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
            livingEntity.startUsingItem(InteractionHand.MAIN_HAND);
            TimerUtil.schedule(() -> {
                if (livingEntity != null) {
                    livingEntity.stopUsingItem();
                }
            }, 800, TimeUnit.MILLISECONDS);
        }
    };

    public static final AnimationEvent.AnimationEventConsumer SHOOT_RIGHT = (entitypatch, self, params) -> {
        if (entitypatch != null && AnimUtils.allowShoot(entitypatch.getOriginal())){
            PlaySoundUtils.playNonRegisteredSound(entitypatch.getOriginal(), "pride:shoot", 1, 1);
            Vec3 pos = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, entitypatch.getOriginal(), new Vec3f(0, -0.75f, -1f), Armatures.BIPED.toolR);
            if (pos != null){
                Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 0, 0, 0);
                particle.scale(0.15f);
                particle.setLifetime(3);
            }
        }
    };
    public static final AnimationEvent.AnimationEventConsumer SHOOT_LEFT = (entitypatch, self, params) -> {
        if (entitypatch != null && AnimUtils.allowShoot(entitypatch.getOriginal())){
            PlaySoundUtils.playNonRegisteredSound(entitypatch.getOriginal(), "pride:shoot", 1, 1);
            Vec3 pos = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, entitypatch.getOriginal(), new Vec3f(0, -0.75f, -1f), Armatures.BIPED.toolL);
            if (pos != null){
                Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 0, 0, 0);
                particle.scale(0.15f);
                particle.setLifetime(3);
            }
        }
    };
}
