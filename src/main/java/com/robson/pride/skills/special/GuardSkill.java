package com.robson.pride.skills.special;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.data.types.skill.DurationSkillData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

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
            );

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
            InteractionHand hand;
            if (ItemStackUtils.checkShield(ent, InteractionHand.OFF_HAND)) {
                hand = InteractionHand.OFF_HAND;
            }
            else hand = InteractionHand.MAIN_HAND;
            ent.startUsingItem(hand);
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
            if (!canBlock(ent, event.getSource()) || Vulnerability.isVulnerable(ent) || (event.getSource().getDirectEntity() instanceof LivingEntity living && SkillDataManager.PERILOUS_MAP.get(living) != null)){
                onEnd(ent);
                return;
            }
            boolean isshield = ItemStackUtils.checkShield(ent, ent.getUsedItemHand());
            event.setCanceled(true);
            if (getActiveTicks(ent) <= 8) {
                tryParry(ent, event, isshield);
                return;
            }
            tryBlock(ent, event, isshield);
        }

        public void tryBlock(LivingEntity ent, LivingAttackEvent event, boolean isshield) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                AnimationManager.AnimationAccessor<? extends StaticAnimation> hitMotion = null;
                if (!isshield) {
                    AssetAccessor<? extends StaticAnimation> currerntmotion = entityPatch.getAnimator().getLivingAnimations().get(LivingMotions.BLOCK);
                    if (currerntmotion != null) {
                        hitMotion = GUARD_HIT_MOTIONS.getOrDefault(currerntmotion.get(), Animations.SWORD_GUARD_HIT);
                    }
                }
                else hitMotion = Animations.BIPED_HIT_SHIELD;
                onBlock(entityPatch, event, hitMotion, false, isshield);
            };
        }

        public void tryParry(LivingEntity ent, LivingAttackEvent event, boolean isshield) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                CapabilityItem itemcap = entityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                if (itemcap != null) {
                    List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> motions = isshield ?
                            switch (ent.getUsedItemHand()){
                        case MAIN_HAND -> List.of(AnimationsRegister.SHIELD_PARRY_MAIN_HAND);
                        case OFF_HAND -> List.of(AnimationsRegister.SHIELD_PARRY_OFF_HAND);
                            } : PARRY_MOTIONS.get(itemcap.getStyle(entityPatch));

                    if (PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()) != null){
                        List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> weapon_parry_motions =
                                PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).getOrDefault(itemcap.getStyle(entityPatch),
                                PER_WEAPON_PARRY_MOTIONS.get(itemcap.getWeaponCategory()).get(CapabilityItem.Styles.COMMON));
                        if (weapon_parry_motions != null){
                            motions = weapon_parry_motions;
                        }
                    }
                    if (motions != null){
                        currentparry++;
                        if (currentparry >= motions.size()) currentparry = 0;
                        onBlock(entityPatch, event, motions.get(currentparry), true, isshield);
                    }
                }
            }
        }

        public void onBlock(LivingEntityPatch<?> ent, LivingAttackEvent event, AnimationManager.AnimationAccessor<? extends StaticAnimation> motion, boolean isParry, boolean isshield) {
            if (motion != null && ent != null) {
                float impact = 1;
                if (event.getSource().getDirectEntity() instanceof LivingEntity entity){
                    AttributeInstance attribute = entity.getAttribute(EpicFightAttributes.IMPACT.get());
                   if (attribute != null) {
                       impact = (float) attribute.getValue();
                   }
                }
                float scale = 0.75f + (impact / 10);
                if (isParry){
                    scale *= 1.25f;
                }
               String sound = isshield ? isParry ? "pride:shieldparry" : "minecraft:item.shield.block" : "epicfight:entity.hit.clash";
                PlaySoundUtils.playSoundByString(ent.getOriginal(), sound, scale, 1);
                Joint joint = ent.getOriginal().getUsedItemHand() == InteractionHand.OFF_HAND || motion == Animations.SWORD_GUARD_ACTIVE_HIT3 ?
                        Armatures.BIPED.get().toolL : Armatures.BIPED.get().toolR;
                ent.playAnimationSynchronized(motion, 0);
                guardStamina(ent, impact, isParry, isshield);
                if (isParry){
                    targetStamina(event.getSource().getDirectEntity(), isshield);
                }
                PrideVec3f trans = ParticleTracking.getAABBForImbuementDivided(ent.getOriginal().getUseItem(), ent.getOriginal(), 99, 99, 5);
                PrideVec3f vec = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent.getOriginal(), trans, joint);
                if (vec != null) {
                    ParticleUtils.spawnParticle(EpicFightParticles.HIT_BLUNT.get(), vec, 0, 0, 0).scale(scale);
                }
                guardKnockBack(ent, event.getSource().getDirectEntity(), impact, isParry, isshield);
                if (ent.getOriginal() instanceof Player player){
                    int xp = (int) (isParry ? event.getAmount() * 2 : event.getAmount());
                    ProgressionUtils.addXp(player, ClientSavedData.Endurance, xp);
                }
            }
        }

        public void guardKnockBack(LivingEntityPatch<?> ent, Entity dmgent, float impact, boolean isparry, boolean isshield) {
            if (ent != null && dmgent != null) {
               float weight = 0;
               impact *= 0.25f;
                if (isparry) {
                    impact *= 0.25f;
                }
                if (isshield) {
                    impact *= 0.75f;
                }
               AttributeInstance weightinstance = ent.getOriginal().getAttribute(EpicFightAttributes.WEIGHT.get());
               if (weightinstance != null){
                   weight = (float) weightinstance.getValue();
               }
               ent.knockBackEntity(dmgent.position(), impact - (0.005f * weight));
            }
        }

        public void targetStamina(Entity entity, boolean isshield){
            float amount = isshield ? 3 : 2;
            StaminaUtils.consumeStamina(entity, amount);
        }

        public void guardStamina(LivingEntityPatch<?> ent, float impact, boolean isparry, boolean isshield) {
            float itemweight = ItemStackUtils.getWeaponWeight(ent.getOriginal().getUseItem());
            float weightmultiplier = isshield ? 2 : 1.5f;
            itemweight *= weightmultiplier;
            if (itemweight > 100){
                itemweight = 100;
            }
            float amount = isparry ? 0.1f * impact : impact;
            amount = MathUtils.getValueWithPercentageDecrease(amount, itemweight);
            StaminaUtils.consumeStamina(ent.getOriginal(), amount);
        }

        @Override
        public void onEnd(LivingEntity ent) {
            super.onEnd(ent);
            ent.stopUsingItem();
        }
    };
}
