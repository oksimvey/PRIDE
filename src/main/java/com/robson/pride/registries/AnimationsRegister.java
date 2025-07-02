package com.robson.pride.registries;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class AnimationsRegister {

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
        STEP_FORWARD = builder.nextAccessor("biped/skill/enderstep_forward", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_LEFT = builder.nextAccessor("biped/skill/enderstep_left", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_RIGHT = builder.nextAccessor("biped/skill/enderstep_right", (acessor)-> new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        STEP_BACKWARD = builder.nextAccessor("biped/skill/enderstep_backward", (acessor)->new DodgeAnimation(0.1f, acessor, 0.5f, 0.5f, biped));
        SHIELD_PARRY_MAIN_HAND = builder.nextAccessor("biped/combat/shield_parry1", (acessor)-> new StaticAnimation(0.05f, true, acessor, biped));
        SHIELD_PARRY_OFF_HAND = builder.nextAccessor("biped/combat/shield_parry2", (acessor)-> new StaticAnimation(0.05f, true, acessor, biped));
        MIKIRI_JUMP = builder.nextAccessor("biped/skill/mikiri_jump", (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        MIKIRI_STEP = builder.nextAccessor("biped/skill/mikiri_step", (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        PERILOUS_ONE_HAND = builder.nextAccessor("biped/skill/perilous_pierce_one_hand" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        PERILOUS_TWO_HAND = builder.nextAccessor("biped/skill/perilous_pierce_two_hand" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        KATANA_IDLE = builder.nextAccessor("biped/combat/katana/hold_bokken", (acessor)-> new StaticAnimation(0.05f, true, acessor, biped));
        PROJECTILE_COUNTER = builder.nextAccessor("biped/skill/projectile_counter" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        DIVINE_RESURRECTION = builder.nextAccessor("biped/skill/divine_resurrection" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
       ELECTROCUTATE = builder.nextAccessor("biped/skill/electrocuted" , (acessor)-> new LongHitAnimation(0.05f, acessor, biped));
        GREATSWORD_HOLD = builder.nextAccessor("biped/combat/shield_parry1", (acessor)-> new StaticAnimation(0.05f, true, acessor, biped));

    }
}
