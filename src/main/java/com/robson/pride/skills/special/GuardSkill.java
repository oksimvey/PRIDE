package com.robson.pride.skills.special;

import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import javax.swing.text.Style;
import java.util.Map;

public interface GuardSkill {

    Map<StaticAnimation, AnimationManager.AnimationAccessor<? extends StaticAnimation>> GUARD_HIT_MOTIONS = Map.ofEntries(

    );

    Map<Style, AnimationManager.AnimationAccessor<? extends StaticAnimation>> PARRY_MOTIONS = Map.ofEntries();

    DurationSkillData DATA = new DurationSkillData() {

        @Override
        public void onStart(LivingEntity ent) {
            ent.startUsingItem(InteractionHand.MAIN_HAND);
        }

        @Override
        public void onAttacked(LivingEntity ent, LivingAttackEvent event) {
            event.setCanceled(true);
        }

        @Override
        public void onHurt(LivingEntity ent, LivingHurtEvent event) {
        }

        @Override
        public void onClientTick(LivingEntity ent) {
            if (ent != null) {
                float heightfactor = ent.getBbHeight() / 1.8f;
                PrideVec3f lEye = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(-0.11f * heightfactor, 0.175f * heightfactor, -0.3f * heightfactor), Armatures.BIPED.get().head);
                PrideVec3f rEye = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(0.11f * heightfactor, 0.175f * heightfactor, -0.3f * heightfactor), Armatures.BIPED.get().head);
                if (lEye != null && rEye != null) {
                    Particle particle = ParticleUtils.spawnAuraParticle(ParticleRegistry.EMBER_PARTICLE.get(), lEye.x(), lEye.y(), lEye.z(), ent.getDeltaMovement().x * 1.2, 0, ent.getDeltaMovement().z * 1.2);
                    particle.scale(0.4f);
                    particle.setColor(50, 0, 0);
                    Particle particle1 = ParticleUtils.spawnAuraParticle(ParticleRegistry.EMBER_PARTICLE.get(), rEye.x(), rEye.y(), rEye.z(), ent.getDeltaMovement().x * 1.2, 0, ent.getDeltaMovement().z * 1.2);
                    particle1.scale(0.4f);
                    particle1.setColor(50, 0, 0);
                }
            }
        }

        @Override
        public void onEnd(LivingEntity ent) {
            super.onEnd(ent);
            ent.stopUsingItem();
        }
    };
}
