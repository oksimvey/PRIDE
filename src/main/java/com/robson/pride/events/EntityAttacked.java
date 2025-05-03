package com.robson.pride.events;

import com.robson.pride.api.mechanics.*;
import com.robson.pride.api.utils.*;
import com.robson.pride.entities.special.Shooter;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.progression.AttributeModifiers;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.skills.special.CloneSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
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
            if (PerilousAttack.checkPerilous(ent)){
                event.setCanceled(true);
            }
            if (PerilousAttack.checkPerilous(ddmgent)) {
                PerilousAttack.onPerilous(ent, ddmgent, event);
            }
            else Guard.checkGuard(ent, ddmgent, event);
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
            ddmgent.setDeltaMovement(ddmgent.getDeltaMovement().x, 0, ddmgent.getDeltaMovement().z);
            ent.setDeltaMovement(ent.getDeltaMovement().x, 0, ent.getDeltaMovement().z);
            if (ddmgent instanceof LivingEntity liv) {
                if (liv.hasEffect(EffectRegister.HYPNOTIZED.get())) {
                    event.setCanceled(true);
                    CloneSkill.summonPassiveClone(ent, ddmgent, true);
                    TimerUtil.schedule(() -> TeleportUtils.teleportEntityRelativeToEntity(ent, ddmgent, 0, -ddmgent.getBbHeight() * 1.5), 100, TimeUnit.MILLISECONDS);
                    PlaySoundUtils.playSound(ent, SoundEvents.ENDERMAN_TELEPORT, 1, 1);
                    liv.removeEffect(EffectRegister.HYPNOTIZED.get());
                }
            }
            if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
                InteractionHand hand = ItemStackUtils.checkAttackingHand(living);
                if (hand != null) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        if (ParticleTracking.shouldRenderParticle(living.getMainHandItem(), living) && ItemStackUtils.getStyle(living) != PrideStyles.GUN_OFFHAND) {
                            ElementalPassives.onElementalDamage(ent, living, living.getMainHandItem(), event);
                        }
                    }
                    if (hand == InteractionHand.OFF_HAND) {
                        if (ParticleTracking.shouldRenderParticle(living.getOffhandItem(), living)) {
                            ElementalPassives.onElementalDamage(ent, living, living.getOffhandItem(), event);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void hurtEnt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() != null && event.getSource().getEntity() != null) {
            if (event.getSource().getDirectEntity() instanceof AbstractArrow  ) {
                event.setAmount(event.getAmount() + AttributeUtils.getAttributeValue(event.getSource().getEntity(), "pride:arrow_power"));
            }
            if (event.getSource().getDirectEntity() instanceof Player player) {
                InteractionHand hand = ItemStackUtils.checkAttackingHand(player);
                if (hand != null) {
                    float extradamage = 0;
                    if (hand == InteractionHand.MAIN_HAND) {
                        extradamage = AttributeModifiers.calculateModifier(player, player.getMainHandItem(), event.getAmount());
                    } else if (hand == InteractionHand.OFF_HAND) {
                        extradamage = AttributeModifiers.calculateModifier(player, player.getOffhandItem(), event.getAmount());
                    }
                    event.setAmount(event.getAmount() + extradamage);
                }
            }
        }
    }
}