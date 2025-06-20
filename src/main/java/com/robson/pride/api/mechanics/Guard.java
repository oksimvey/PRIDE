package com.robson.pride.api.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.damagesource.EpicFightDamageType;

import java.util.Objects;

public class Guard {

    public static void checkGuard(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        if (ent instanceof ServerPlayer player) {
            if (player.isUsingItem() && canBlock(ent, ddmgent, event.getSource()) && ItemStackUtils.getStyle(player) != PrideStyles.GUN_OFFHAND) {
                checkParry(ent, ddmgent, event);
            } else {
                ProgressionUtils.addXp(player, "Vigor", (int) event.getAmount());
            }
        } else {

        }
    }

    public static void checkParry(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        if (ent instanceof Player player) {
            if (ent.getPersistentData().getBoolean("isParrying")) {
                Parry.onParry(ent, ddmgent);
                ProgressionUtils.addXp(player, "Endurance", (int) event.getAmount() * 2);
                if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)) {
                    onAnyBlock(player, event, true);
                }
            } else {
                onGuard(ent, ddmgent, event);
                ProgressionUtils.addXp(player, "Endurance", (int) event.getAmount());
                if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)) {
                    onAnyBlock(player, event, false);
                }
            }
        } else {

        }
    }

    public static void onGuard(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        String BlockType = ItemStackUtils.checkBlockType(ent);
        if (Objects.equals(BlockType, "weapon")) {
            onMainHandWeaponGuard(ent, ddmgent, event);
        }
        if (Objects.equals(BlockType, "mainhandshield")) {
            onMainHandShieldGuard(ent, ddmgent, event);
        }
        if (Objects.equals(BlockType, "offhandshield")) {
            onOffHandShieldGuard(ent, ddmgent, event);
        }
    }

    public static StaticAnimation getGuardHitMotion(Player player) {
        if (player != null) {
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)) {
                if (EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).getAnimator() instanceof ClientAnimator) {
                    StaticAnimation anim = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class).getClientAnimator().getCompositeLivingMotion(LivingMotions.BLOCK).orElse(null);
                    if (anim == null){
                        return Animations.SWORD_GUARD_HIT.get();
                    }
                    if (anim == Animations.SWORD_DUAL_GUARD) {
                        return Animations.SWORD_DUAL_GUARD_HIT.get();
                    } else if (anim == Animations.LONGSWORD_GUARD) {
                        return Animations.LONGSWORD_GUARD_HIT.get();
                    } else if (anim == Animations.GREATSWORD_GUARD) {
                        return Animations.GREATSWORD_GUARD_HIT.get();
                    } else if (anim == Animations.UCHIGATANA_GUARD) {
                        return Animations.UCHIGATANA_GUARD_HIT.get();
                    } else if (anim == Animations.SPEAR_GUARD) {
                        return Animations.SPEAR_GUARD_HIT.get();
                    }

                }
            }
        }
        return Animations.SWORD_GUARD_HIT.get();
    }

    public static StaticAnimation getParryMotion(Player player) {
        boolean toggle = false;
        if (player != null) {
            toggle = TagsUtils.toggleBoolean(player.getPersistentData(), "parry_toggle");
            Style style = ItemStackUtils.getStyle(player);
            if (style == PrideStyles.DUAL_WIELD) {
                toggle = !toggle;
                return toggle ? Animations.SWORD_GUARD_ACTIVE_HIT2.get() : Animations.SWORD_GUARD_ACTIVE_HIT3.get();
            } else if (style == CapabilityItem.Styles.TWO_HAND) {
                toggle = !toggle;
                return toggle ? Animations.LONGSWORD_GUARD_ACTIVE_HIT1.get() : Animations.LONGSWORD_GUARD_ACTIVE_HIT2.get();
            }
        }
        toggle = !toggle;
        return toggle ? Animations.SWORD_GUARD_ACTIVE_HIT1.get() : Animations.SWORD_GUARD_ACTIVE_HIT2.get();
    }

    public static void onAnyBlock(Player serveerPlayer, LivingAttackEvent event, boolean isparry) {
        if (serveerPlayer != null) {
            float scale = 1.5f;
            StaticAnimation motion = getGuardHitMotion(serveerPlayer);
            if (isparry) {
                motion = getParryMotion(serveerPlayer);
                scale = 2;
            }
            Joint joint = Armatures.BIPED.get().toolR;
            if (motion == Animations.SWORD_GUARD_ACTIVE_HIT3) {
                joint = Armatures.BIPED.get().toolL;
            }
            AnimUtils.playAnim(serveerPlayer, motion, 0);
            PlaySoundUtils.playSound(serveerPlayer, EpicFightSounds.CLASH.get(), scale * 3 - 1, 1);
            spawnBlockParticle(serveerPlayer, scale * 0.75f, joint);
            event.setCanceled(true);
            guardKnockBack(serveerPlayer, event.getSource().getDirectEntity(), isparry);
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

    public static void spawnBlockParticle(Player player, float scale, Joint joint) {
        if (player != null) {
            ItemStack itemStack = player.getMainHandItem();
            if (joint == Armatures.BIPED.get().toolL) {
                itemStack = player.getOffhandItem();
            }
            Vec3f vec3f = ParticleTracking.getAABBForImbuement(itemStack, player);
            Vec3 vec3 = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, player, new Vec3f(0, 0, vec3f.z), joint);
            Particle particle = Minecraft.getInstance().particleEngine.createParticle(EpicFightParticles.HIT_BLUNT.get(), vec3.x, vec3.y, vec3.z, 0, 0, 0);
            if (particle != null) {
                particle.scale(scale);
            }
        }
    }

    public static boolean canBlock(Entity ent, Entity dmgent, DamageSource damageSource) {
        if (ent != null && dmgent != null) {
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

    public static void onOffHandShieldGuard(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.OFF_HAND, EquipmentSlot.OFFHAND);
        onGuardStamina(ent, ddmgent, event.getEntity(), weight * 2);
    }

    public static void onMainHandWeaponGuard(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND);
        onGuardStamina(ent, ddmgent, event.getEntity(), weight * 1.5f);
    }

    public static void onMainHandShieldGuard(Entity ent, Entity ddmgent, LivingAttackEvent event) {
        float weight = ItemStackUtils.getWeaponWeight(ent, InteractionHand.MAIN_HAND, EquipmentSlot.MAINHAND);
        onGuardStamina(ent, ddmgent, event.getEntity(), weight * 2);
    }

    public static void onGuardStamina(Entity ent, Entity ddmgent, Entity dmgent, float weight) {
        float impact = 1;
        if (ddmgent instanceof LivingEntity) {
            impact = AttributeUtils.getAttributeValue(ddmgent, "epicfight:impact");
        }
        if (weight > 100) {
            weight = 100;
        }
        float amount = 0.5f + MathUtils.getValueWithPercentageDecrease(impact, weight);
        StaminaUtils.consumeStamina(ent, amount);
    }
}
