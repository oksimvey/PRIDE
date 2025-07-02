package com.robson.pride.skills.special;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.registries.AnimationsRegister;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import org.lwjgl.system.linux.Stat;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.EpicFightDamageType;

import java.util.List;
import java.util.Map;

public interface GuardSkill {

    Map<StaticAnimation, AnimationManager.AnimationAccessor<? extends StaticAnimation>> GUARD_HIT_MOTIONS = Map.ofEntries(
            Map.entry(Animations.SWORD_GUARD.get(), Animations.SWORD_GUARD_HIT),
            Map.entry(Animations.SWORD_DUAL_GUARD.get(), Animations.SWORD_DUAL_GUARD_HIT),
            Map.entry(Animations.LONGSWORD_GUARD.get(), Animations.LONGSWORD_GUARD_HIT),
            Map.entry(Animations.GREATSWORD_GUARD.get(), Animations.GREATSWORD_GUARD_HIT),
            Map.entry(Animations.UCHIGATANA_GUARD.get(), Animations.UCHIGATANA_GUARD_HIT),
            Map.entry(Animations.SPEAR_GUARD.get(), Animations.SPEAR_GUARD_HIT));

    Map<WeaponCategory, Map<Style, List<AnimationManager.AnimationAccessor<? extends StaticAnimation>>>> PER_WEAPON_PARRY_MOTIONS = Map.ofEntries(
            Map.entry(CapabilityItem.WeaponCategories.SHIELD, Map.ofEntries(
                    Map.entry(CapabilityItem.Styles.COMMON, List.of(AnimationsRegister.SHIELD_PARRY_MAIN_HAND
                    )))));

    Map<Style, List<AnimationManager.AnimationAccessor<? extends StaticAnimation>>> PARRY_MOTIONS = Map.ofEntries(
            Map.entry(CapabilityItem.Styles.ONE_HAND, List.of(
                    Animations.SWORD_GUARD_ACTIVE_HIT1,
                    Animations.SWORD_GUARD_ACTIVE_HIT2
            )),
            Map.entry(CapabilityItem.Styles.TWO_HAND, List.of(
                    Animations.LONGSWORD_GUARD_ACTIVE_HIT1,
                    Animations.LONGSWORD_GUARD_ACTIVE_HIT2
            )),
            Map.entry(PrideStyles.SHIELD_OFFHAND, List.of(AnimationsRegister.SHIELD_PARRY_OFF_HAND)),
            Map.entry(PrideStyles.DUAL_WIELD, List.of(
                    Animations.SWORD_GUARD_ACTIVE_HIT2,
                    Animations.SWORD_GUARD_ACTIVE_HIT3
            )));

    DurationSkillData DATA = new DurationSkillData() {

        private byte currentparry = 0;

        @Override
        public void onStart(LivingEntity ent) {
            super.onStart(ent);
            ent.startUsingItem(InteractionHand.MAIN_HAND);
        }

        public boolean canBlock(LivingEntity ent, DamageSource damageSource){
            if (ent != null && damageSource != null){
                boolean isFront = false;
                Vec3 sourceLocation = damageSource.getSourcePosition();
                if (sourceLocation != null) {
                    Vec3 viewVector = ent.getViewVector(1.0F);
                    viewVector = viewVector.subtract(0, viewVector.y, 0).normalize();
                    Vec3 toSourceLocation = sourceLocation.subtract(ent.position()).normalize();
                    if (toSourceLocation.dot(viewVector) > 0.0D) {
                        isFront = true;
                    }
                }
                if (isFront) {
                    return !damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                            && !damageSource.is(EpicFightDamageType.PARTIAL_DAMAGE)
                            && !damageSource.is(DamageTypeTags.BYPASSES_ARMOR)
                            && !damageSource.is(DamageTypeTags.IS_EXPLOSION)
                            && !damageSource.is(DamageTypes.MAGIC)
                            && !damageSource.is(DamageTypeTags.IS_FIRE);
                }
            }
            return false;
        }

        @Override
        public void onAttacked(LivingEntity ent, LivingAttackEvent event) {
            if (!canBlock(ent, event.getSource()) || (event.getSource().getDirectEntity() instanceof LivingEntity living && SkillDataManager.ACTIVE_WEAPON_SKILL.get(living) != null)){
                onEnd(ent);
                return;
            }
            event.setCanceled(true);
            if (getActiveTicks(ent) <= 8) {
                tryParry(ent, event);
                return;
            }
            tryBlock(ent, event);
        }

        public void tryBlock(LivingEntity ent, LivingAttackEvent event) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                AssetAccessor<? extends StaticAnimation> currerntmotion = entityPatch.getAnimator().getLivingAnimations().get(LivingMotions.BLOCK);
                if (currerntmotion != null){
                    onBlock(entityPatch, event, GUARD_HIT_MOTIONS.getOrDefault(currerntmotion.get(), Animations.SWORD_GUARD_HIT), false);
                }
            };
        }

        public void tryParry(LivingEntity ent, LivingAttackEvent event) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                CapabilityItem itemcap = entityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                if (itemcap != null) {
                    List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> motions = PARRY_MOTIONS.get(itemcap.getStyle(entityPatch));
                    if (PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()) != null &&
                            PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).getOrDefault(itemcap.getStyle(entityPatch), 
                                    PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).get(CapabilityItem.Styles.COMMON)) != null){
                        motions = PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).getOrDefault(itemcap.getStyle(entityPatch),
                                PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).get(CapabilityItem.Styles.COMMON));
                    }
                    if (motions != null){
                        currentparry++;
                        if (currentparry >= motions.size()) currentparry = 0;
                        onBlock(entityPatch, event, motions.get(currentparry), true);
                    }
                }
            }
        }

        public void onBlock(LivingEntityPatch<?> ent, LivingAttackEvent event, AnimationManager.AnimationAccessor<? extends StaticAnimation> motion, boolean isParry) {
            if (motion != null && ent != null) {
                ent.playAnimationSynchronized(motion, 0);
                PlaySoundUtils.playSound(ent.getOriginal(), EpicFightSounds.CLASH.get(), 2 * 3 - 1, 1);
                Vec3f trans = ParticleTracking.getAABBForImbuementDivided(ent.getOriginal().getUseItem(), ent.getOriginal(), 99, 99, 5);
                PrideVec3f vec = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent.getOriginal(), trans, Armatures.BIPED.get().toolR);
                if (vec != null) {
                    ParticleUtils.spawnParticle(EpicFightParticles.HIT_BLUNT.get(), vec, 0, 0, 0).scale(1.5f);
                }
            }
        }

        public static void guardKnockBack(Entity ent, Entity dmgent, boolean isparry) {
            if (ent != null && dmgent != null) {
                float impact = AttributeUtils.getAttributeValue(dmgent, "epicfight:impact");
                if (isparry) {
                    impact = impact / 2;
                }
                impact = MathUtils.getValueWithPercentageDecrease(impact, AttributeUtils.getAttributeValue(ent, "epicfight:weight") / 2);
                if (impact < 0) {
                    impact = 0;
                }
                PlayerPatch livingEntity = EpicFightCapabilities.getEntityPatch(ent, PlayerPatch.class);
                if (livingEntity != null) {
                    livingEntity.knockBackEntity(dmgent.position(), impact);
                }
            }
        }


        @Override
        public void onSpellDamage(LivingEntity ent, SpellDamageEvent event) {

        }

        @Override
        public void onEnd(LivingEntity ent) {
            super.onEnd(ent);
            ent.stopUsingItem();
        }
    };
}
