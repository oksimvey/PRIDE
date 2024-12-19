package com.robson.pride.events;

import com.robson.pride.api.utils.*;
import com.robson.pride.mechanics.*;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.skills.magic.CloneSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingSwapItemsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber
public class EntityAttacked {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void OnAnyAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getDirectEntity() != null) {
            Entity ent = event.getEntity();
            Entity ddmgent = event.getSource().getDirectEntity();
            if (Objects.equals(ddmgent.getPersistentData().getString("Perilous"), "") || Objects.equals(ddmgent.getPersistentData().getString("Perilous"), null)) {
                Guard.checkGuard(ent, ddmgent, event);
            }

            GuardBreak.checkForGuardBreak(ent, ddmgent);
            if (ent.getPersistentData().getBoolean("isVulnerable")) {
                GuardBreak.onVulnerableDamage(ent, event);
            }
            if (PerilousAttack.checkPerilous(ddmgent)) {
                PerilousAttack.onPerilous(ent, ddmgent, event);
            }
            if (ddmgent instanceof ServerPlayer player && event.getAmount() > 0) {
                ProgressionUtils.addXp(player, "Strength", (int) event.getAmount());
            }
            if (ddmgent.getPersistentData().getBoolean("passive_clone")){
                event.setCanceled(true);
            }
            if (ent.getPersistentData().getBoolean("passive_clone")){
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void OnFinalAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getDirectEntity() != null) {
            Entity ent = event.getEntity();
            Entity ddmgent = event.getSource().getEntity();
            if (ddmgent instanceof LivingEntity liv){
                if (liv.hasEffect(EffectRegister.HYPNOTIZED.get())){
                    event.setCanceled(true);
                    CloneSkill.summonPassiveClone(ent, ddmgent);
                    TimerUtil.schedule(()->TeleportUtils.teleportEntityRelativeToEntity(ent, ddmgent, 0, -ddmgent.getBbHeight()   * 1.5), 100, TimeUnit.MILLISECONDS);
                    PlaySoundUtils.playSound(ent, SoundEvents.ENDERMAN_TELEPORT, 1, 1);
                    liv.removeEffect(EffectRegister.HYPNOTIZED.get());
                }
            }
            if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
                InteractionHand hand = AnimUtils.getAttackingHand(living);
                if (hand != null) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        if (ParticleTracking.shouldRenderParticle(living.getMainHandItem(), living)) {
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
}