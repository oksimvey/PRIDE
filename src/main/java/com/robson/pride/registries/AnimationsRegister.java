package com.robson.pride.registries;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public class AnimationsRegister {

    public static AnimationManager.AnimationAccessor<StaticAnimation> SHORT_WHISTLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_PARRY_MAIN_HAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SHIELD_PARRY_OFF_HAND;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> MIKIRI_JUMP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> MIKIRI_STEP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PERILOUS_TWO_HAND;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PERILOUS_ONE_HAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_SKILL1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_SKILL2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KATANA_IDLE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PROJECTILE_COUNTER;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> EXECUTE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> ELECTROCUTATE;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> DIVINE_RESURRECTION;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GREATSWORD_HOLD;
    public static AnimationManager.AnimationAccessor<MovementAnimation> GREATSWORD_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> MAUL_HOLD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SPEAR_ONEHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_GS_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> DUAL_GS_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> DUAL_GS_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_DASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_GREATSWORD_SKILL;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> LION_CLOW;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREAT_TACHI_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREAT_TACHI_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREAT_TACHI_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> GREAT_TACHI_AUTO4;
    public static AnimationManager.AnimationAccessor<AirSlashAnimation> DUAL_TACHI_AIRSLASH;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SCYTHE_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SCYTHE_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SCYTHE_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_SCYTHE_AUTO4;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_FORWARD;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_LEFT;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_RIGHT;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> STEP_BACKWARD;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder("pride", AnimationsRegister::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {

        Armatures.ArmatureAccessor<HumanoidArmature> biped = Armatures.BIPED;
        SHORT_WHISTLE = builder.nextAccessor("biped/skill/short_whistle", (acessor)-> new StaticAnimation(0.05f, false, acessor, biped).addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true));
        STEP_FORWARD = builder.nextAccessor("biped/skill/enderstep_forward", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_LEFT = builder.nextAccessor("biped/skill/enderstep_left", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_RIGHT = builder.nextAccessor("biped/skill/enderstep_right", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_BACKWARD = builder.nextAccessor("biped/skill/enderstep_backward", (acessor)->new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        SHIELD_PARRY_MAIN_HAND = builder.nextAccessor("biped/combat/shield_parry1", (acessor)-> new StaticAnimation(0.05f, false, acessor, biped));
        SHIELD_PARRY_OFF_HAND = builder.nextAccessor("biped/combat/shield_parry2", (acessor)-> new StaticAnimation(0.05f, false, acessor, biped));
        MIKIRI_JUMP = builder.nextAccessor("biped/skill/mikiri_jump", (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        MIKIRI_STEP = builder.nextAccessor("biped/skill/mikiri_step", (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        PERILOUS_ONE_HAND = builder.nextAccessor("biped/skill/perilous_pierce_one_hand" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        PERILOUS_TWO_HAND = builder.nextAccessor("biped/skill/perilous_pierce_two_hand" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        KATANA_IDLE = builder.nextAccessor("biped/combat/katana/hold_bokken", (acessor)-> new StaticAnimation(0.05f, true, acessor, biped));
        PROJECTILE_COUNTER = builder.nextAccessor("biped/skill/projectile_counter" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        DIVINE_RESURRECTION = builder.nextAccessor("biped/skill/divine_resurrection" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
       ELECTROCUTATE = builder.nextAccessor("biped/skill/electrocuted" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
       EXECUTE =  builder.nextAccessor("biped/skill/execute", (accessor) -> (new BasicAttackAnimation(0.01F, accessor, Armatures.BIPED, (new AttackAnimation.Phase(0.0F, 0.75F, 0.51F, 0.95F, 3.1F, ((HumanoidArmature)Armatures.BIPED.get()).toolR, null)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.1F)), (new AttackAnimation.Phase(0.85f, 3f, 3.15F, 6.0F, Float.MAX_VALUE, ((HumanoidArmature)Armatures.BIPED.get()).rootJoint, null)).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.EVISCERATE.get()))).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.0F, Float.MAX_VALUE})).addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_ON_LINK, false).addState(EntityState.MOVEMENT_LOCKED, true).addState(EntityState.TURNING_LOCKED, true).addState(EntityState.LOCKON_ROTATE, true).addState(EntityState.CAN_SKILL_EXECUTION, false).addState(EntityState.CAN_BASIC_ATTACK, false).addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, CONSTANT_ONE));
    }

    public static final AnimationProperty.PlaybackSpeedModifier CONSTANT_ONE = (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> 1.25F;

}
