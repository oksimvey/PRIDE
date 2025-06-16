package com.robson.pride.events;

import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.mechanics.*;
import com.robson.pride.api.utils.*;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.effect.ImmunityEffect;
import com.robson.pride.entities.special.Shooter;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.progression.AttributeModifiers;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.skills.special.CloneSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class EntityAttacked {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void OnAnyAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getDirectEntity() != null) {
            Entity ent = event.getEntity();
            Entity ddmgent = event.getSource().getDirectEntity();
            if (event.getSource().getEntity() instanceof PrideMobBase prideMobBase && (prideMobBase.getType().equals(ent.getType()) || prideMobBase.allies.contains(EntityType.getKey(ent.getType()).toString()))) {
                event.setCanceled(true);
            } else if (ent instanceof PrideMobBase) {
                for (Entity entity : ent.level().getEntities(ent, MathUtils.createAABBAroundEnt(ent, 25))) {
                    if (entity != ent && entity instanceof PrideMobBase prideMobBase) {
                        prideMobBase.deserializeAlliesTargeting(ent, event.getSource().getEntity());
                    }
                }
            }
            if (event.getSource().getEntity() instanceof Shooter shooter) {
                if (shooter.getOwner() == ent) {
                    event.setCanceled(true);
                }
            }
            if (ddmgent instanceof Projectile) {
                if (ddmgent instanceof AbstractArrow arrow && ent.getPersistentData().getBoolean("mikiri_dodge")) {
                    MikiriCounter.onArrowMikiri(ent, arrow, event);
                }
            }
            GuardBreak.checkForGuardBreak(ent, ddmgent);
            if (ent.getPersistentData().getBoolean("isVulnerable")) {
                GuardBreak.onVulnerableDamage(ent, event);
            }
            if (PerilousAttack.checkPerilous(ent)) {
                event.setCanceled(true);
            }
            if (PerilousAttack.checkPerilous(ddmgent)) {
                PerilousAttack.onPerilous(ent, ddmgent, event);
            } else Guard.checkGuard(ent, ddmgent, event);
            if (ddmgent instanceof ServerPlayer player && event.getAmount() > 0) {
                ProgressionUtils.addXp(player, "Strength", (int) event.getAmount());
            }
            if (ddmgent.getPersistentData().getBoolean("passive_clone")) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void OnFinalAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getDirectEntity() != null) {
            Entity ent = event.getEntity();
            Entity ddmgent = event.getSource().getDirectEntity();
            if (ddmgent instanceof LivingEntity liv) {
                if (liv.hasEffect(EffectRegister.HYPNOTIZED.get())) {
                    event.setCanceled(true);
                    CloneSkill.summonPassiveClone(ent, ddmgent, true);
                    TimerUtil.schedule(() -> TeleportUtils.teleportEntityRelativeToEntity(ent, ddmgent, 0, -ddmgent.getBbHeight() * 1.5), 100, TimeUnit.MILLISECONDS);
                    PlaySoundUtils.playSound(ent, SoundEvents.ENDERMAN_TELEPORT, 1, 1);
                    liv.removeEffect(EffectRegister.HYPNOTIZED.get());
                }
            }
            if (ent instanceof LivingEntity living && living.hasEffect(EffectRegister.IMMUNITY.get())) {
                ImmunityEffect.onDamage(event, ent);
            }
        }
    }

    @SubscribeEvent
    public static void hurtEnt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() != null && event.getSource().getEntity() != null) {
            if (event.getSource().getDirectEntity() instanceof AbstractArrow) {
                event.setAmount(event.getAmount() + AttributeUtils.getAttributeValue(event.getSource().getEntity(), "pride:arrow_power"));
            }
            InteractionHand hand = ItemStackUtils.checkAttackingHand(event.getSource().getDirectEntity());
            if (hand != null) {
                float damage = event.getAmount();
                if (event.getSource().getDirectEntity() instanceof Player player) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        damage += AttributeModifiers.calculateModifier(player, player.getMainHandItem(), damage);
                    } else if (hand == InteractionHand.OFF_HAND) {
                        damage += AttributeModifiers.calculateModifier(player, player.getOffhandItem(), damage);
                    }
                }
                if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        if (ItemStackUtils.getStyle(living) != PrideStyles.GUN_OFFHAND) {
                            ElementBase elementBase = ParticleTracking.getItemElementForImbuement(living.getMainHandItem(), living);
                            if (elementBase != null && elementBase.createDamageSource(living) != event.getSource()) {
                                damage = elementBase.onHit(event.getEntity(), living, damage, false);
                            }
                        }
                    }
                    if (hand == InteractionHand.OFF_HAND) {
                        ElementBase elementBase = ParticleTracking.getItemElementForImbuement(living.getOffhandItem(), living);
                        if (elementBase != null && elementBase.createDamageSource(living) != event.getSource()) {
                            damage = elementBase.onHit(event.getEntity(), living, damage, false);
                        }
                    }
                }
                event.setAmount(damage);
            }
        }
    }
}