package com.robson.pride.main.registries;

import com.robson.pride.main.Pride;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.LongHitAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.AnimationRegistryEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

public class AnimationsRegister {

        public static StaticAnimation MIKIRI_JUMP;
        public static StaticAnimation MIKIRI_STEP;

        @SubscribeEvent
        public static void registerAnimations(AnimationRegistryEvent event){
        event.getRegistryMap().put(Pride.MOD_ID, AnimationsRegister::build);
    }

        private static void build() {

        HumanoidArmature biped = Armatures.BIPED;

            MIKIRI_JUMP = (new BasicAttackAnimation(0.05F, "biped/skill/mikiri_jump", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, biped.legL, ColliderPreset.FIST), new AttackAnimation.Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, biped.legL, ColliderPreset.FIST)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.4F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) EpicFightSounds.BLUNT_HIT.get()).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.0F), 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent)EpicFightSounds.BLUNT_HIT_HARD.get(), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.05F, 0.75F}));
            MIKIRI_STEP = (new BasicAttackAnimation(0.0F, "biped/skill/mikiri_step", biped, new AttackAnimation.Phase[]{new AttackAnimation.Phase(0.0F, 0.15F, 0.2F, 0.45F, 0.45F, biped.legL, ColliderPreset.BATTOJUTSU_DASH), new AttackAnimation.Phase(0.45F, 0.45F, 0.75F, 1.0F, Float.MAX_VALUE, biped.legL, ColliderPreset.BATTOJUTSU_DASH)})).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0F)).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent) SoundsRegister.SHIELDPARRY.get()).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F), 1).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(0F), 1).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.NONE, 1).addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1).addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, (SoundEvent)EpicFightSounds.BLUNT_HIT_HARD.get(), 1).addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F).addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.5F).addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false).addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true).addProperty(AnimationProperty.ActionAnimationProperty.NO_GRAVITY_TIME, TimePairList.create(new float[]{0.05F, 0.75F}));
    }
}
