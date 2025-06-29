package com.robson.pride.skills.special;

import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.swing.text.Style;
import java.util.Map;

public interface GuardSkill {

    Map<StaticAnimation, AnimationManager.AnimationAccessor<? extends StaticAnimation>> GUARD_HIT_MOTIONS = Map.ofEntries(
            Map.entry(Animations.SWORD_GUARD.get(), Animations.SWORD_GUARD_HIT),
            Map.entry(Animations.SWORD_DUAL_GUARD.get(), Animations.SWORD_DUAL_GUARD_HIT),
            Map.entry(Animations.LONGSWORD_GUARD.get(), Animations.LONGSWORD_GUARD_HIT),
            Map.entry(Animations.GREATSWORD_GUARD.get(), Animations.GREATSWORD_GUARD_HIT),
            Map.entry(Animations.UCHIGATANA_GUARD.get(), Animations.UCHIGATANA_GUARD_HIT),
            Map.entry(Animations.SPEAR_GUARD.get(), Animations.SPEAR_GUARD_HIT));


    Map<Style, AnimationManager.AnimationAccessor<? extends StaticAnimation>> PARRY_MOTIONS = Map.ofEntries();

    DurationSkillData DATA = new DurationSkillData() {

        @Override
        public void onStart(LivingEntity ent) {
            super.onStart(ent);
            ent.startUsingItem(InteractionHand.MAIN_HAND);
        }

        @Override
        public void onAttacked(LivingEntity ent, LivingAttackEvent event) {
            if (getActiveTicks(ent) <= 10) {

            }
            tryBlock(ent, event);
        }

        public void tryBlock(LivingEntity ent, LivingAttackEvent event){
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                event.setCanceled(true);
                StaticAnimation currentguard = AnimUtils.getBlockMotion(entityPatch);
                if (currentguard != null){
                    AnimationManager.AnimationAccessor<? extends StaticAnimation> guardhitmotion = GUARD_HIT_MOTIONS.getOrDefault(currentguard, Animations.SWORD_GUARD_HIT);
                    entityPatch.playAnimationSynchronized(guardhitmotion, 0);
                }
                PlaySoundUtils.playSound(ent, EpicFightSounds.CLASH.get(), 2 * 3 - 1, 1);
                Vec3f trans = ParticleTracking.getAABBForImbuement(ent.getUseItem(), ent);
                PrideVec3f vec = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, entityPatch, trans, Armatures.BIPED.get().toolR);
                if (vec != null){
                    ParticleUtils.spawnParticle(EpicFightParticles.HIT_BLUNT.get(), vec, 0,0 ,0).scale(1.5f);
                }
            }
        }

        public void tryParry(LivingEntity ent, LivingAttackEvent event){

        }

        @Override
        public void onHurt(LivingEntity ent, LivingHurtEvent event) {
        }

        @Override
        public void onEnd(LivingEntity ent) {
            super.onEnd(ent);
            ent.stopUsingItem();
        }
    };
}
