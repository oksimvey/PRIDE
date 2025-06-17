package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.yonchi.refm.gameasset.RapierAnimations;
import yesman.epicfight.api.animation.AnimationClip;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.collider.Collider;
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

import java.util.Set;

public class AnimationsRegister {

    public static DirectStaticAnimation EMPTY_ANIMATION = new DirectStaticAnimation() {
        public void loadAnimation() {
        }

        public AnimationClip getAnimationClip() {
            return AnimationClip.EMPTY_CLIP;
        }
    };

    public static AnimationManager.AnimationAccessor<LongHitAnimation> MIKIRI_JUMP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> MIKIRI_STEP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PERILOUS_TWO_HAND;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PERILOUS_DUAL_WIELD;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> PERILOUS_ONE_HAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_SKILL1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> DUAL_TACHI_SKILL2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KATANA_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> RECHARGE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PROJECTILE_COUNTER;
    public static AnimationManager.AnimationAccessor<StaticAnimation> MOB_EAT_MAINHAND;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> EXECUTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ELECTROCUTATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DIVINE_RESURRECTION;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SHORTSWORD_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GREATSWORD_HOLD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> GREATSWORD_RUN;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO1;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO2;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO3;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SWORD_ONEHAND_AUTO4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> MAUL_HOLD;
    public static AnimationManager.AnimationAccessor<BasicAttackAnimation> SPEAR_ONEHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_GS_IDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_GS_WALK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DUAL_GS_RUN;
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
        DUAL_TACHI_AIRSLASH = builder.nextAccessor("biped/combat/scythe/dual_tachi_airslash", (acessor)-> new AirSlashAnimation(0.1F, 0.15F, 0.26F, 0.5F, ColliderPreset.DUAL_SWORD_AIR_SLASH, biped.get().torso, acessor, biped));
       DUAL_SCYTHE_AUTO2 = createMotion(builder,"biped/combat/scythe/dual_tachi_auto2", 0.05f, 0.2f, 0.3f, 0.5f, 0.5f, biped.get().toolR);

        DUAL_SCYTHE_AUTO3 = createMotion(builder, "biped/combat/scythe/dual_tachi_auto3", 0.16f, 0.1f, 0.15f, 0.45f, 0.45f, biped.get().toolR);
       DUAL_SCYTHE_AUTO4 = createMotion(builder, "biped/combat/scythe/dual_tachi_auto4", 0.1f,0.5f, 0.6f, 1.1f, 1.167f, biped.get().toolR);

       GREAT_TACHI_AUTO1 = createMotion(builder, "biped/combat/katana/great_tachi_auto1", 0.1f, 0.1f, 0.25f, 0.5f, 0.5f, biped.get().toolR);
        GREAT_TACHI_AUTO2 = createMotion(builder, "biped/combat/katana/great_tachi_auto2", 0.1f, 0.1f, 0.25f, 0.5f, 0.55f, biped.get().toolR);
        GREAT_TACHI_AUTO3 = createMotion(builder, "biped/combat/katana/great_tachi_auto3", 0.1f, 0.1f, 0.15f, 0.3f, 0.35f, biped.get().toolR);
        GREAT_TACHI_AUTO4 = createMotion(builder, "biped/combat/katana/great_tachi_auto4", 0.1f, 0.1f, 0.5f, 0.95f, 1.5f, biped.get().toolR);
      DUAL_GREATSWORD_AUTO1 = createMotion(builder, "biped/combat/colossalsword/greatsword_dual_auto_1", 0.25f, 0.2f, 0.4f, 0.45f, 0.45f, biped.get().toolL);
        DUAL_GREATSWORD_AUTO2 =  createMotion(builder, "biped/combat/colossalsword/greatsword_dual_auto_1", 0.25f, 0.2f, 0.4f, 0.45f, 0.45f, biped.get().toolL);
        DUAL_GREATSWORD_AUTO3 =  createMotion(builder, "biped/combat/colossalsword/greatsword_dual_auto_1", 0.25f, 0.2f, 0.4f, 0.45f, 0.45f, biped.get().toolL);

    }

    private static AnimationManager.AnimationAccessor<BasicAttackAnimation> createMotion(AnimationManager.AnimationBuilder builder, String id, float convert, float antecipation, float predelay, float contact, float recovery, Joint joint){
        return builder.nextAccessor(id, (acessor)-> new BasicAttackAnimation(convert, antecipation, predelay, contact, recovery, null, joint, acessor, Armatures.BIPED));

    }
}
