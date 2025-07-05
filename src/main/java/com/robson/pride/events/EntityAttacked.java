package com.robson.pride.events;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.api.mechanics.*;
import com.robson.pride.api.utils.*;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber
public class EntityAttacked {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void OnAnyAttack(LivingAttackEvent event) {
        if (event.getEntity() != null && event.getSource().getDirectEntity() != null) {
            LivingEntity ent = event.getEntity();
            if (event.getSource().getDirectEntity() instanceof Player player) {
                ProgressionUtils.addXp(player, ClientSavedData.Strength, (int) event.getAmount());
            }
            if (!ent.level().isClientSide){
                for (byte skill : SkillDataManager.getActiveSkills(ent)) {
                    DurationSkillData data = SkillDataManager.INSTANCE.getByID(skill);
                    if (data != null) {
                        data.onAttacked(ent, event);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void hurtEnt(LivingHurtEvent event) {
        if (event.getSource().getDirectEntity() != null && event.getSource().getEntity() != null) {
            float damage = event.getAmount();
            if (GuardBreak.isNeutralized(event.getEntity())) {
                GuardBreak.onVulnerableDamage(event.getEntity());
                damage *= 2;
            }
            if (event.getSource().getDirectEntity() instanceof AbstractArrow) {
                event.setAmount(event.getAmount() + AttributeUtils.getAttributeValue(event.getSource().getEntity(), "pride:arrow_power"));
            }
            if (SkillDataManager.PERILOUS_MAP.get(event.getEntity()) != null){
                event.setCanceled(true);
                return;
            }
            if (event.getSource().getDirectEntity() instanceof LivingEntity living) {
                if (SkillDataManager.ACTIVE_WEAPON_SKILL.get(living) != null){
                    WeaponSkillData data = ServerDataManager.getWeaponSkillData(SkillDataManager.ACTIVE_WEAPON_SKILL.get(living));
                   if (data != null){
                       data.onHurt(living, event.getEntity(), event);
                   }
                }
                InteractionHand hand = ItemStackUtils.checkAttackingHand(living);
                if (hand != null) {
                    if (living instanceof Player player) {
                        if (hand == InteractionHand.MAIN_HAND) {
                            damage += AttributeModifiers.calculateModifier(player, player.getMainHandItem(), damage);
                        } else if (hand == InteractionHand.OFF_HAND) {
                            damage += AttributeModifiers.calculateModifier(player, player.getOffhandItem(), damage);
                        }
                    }
                        if (hand == InteractionHand.MAIN_HAND) {
                            if (ItemStackUtils.getStyle(living) != PrideStyles.GUN_OFFHAND) {
                                ElementData elementBase = ParticleTracking.getItemElementForImbuement(living.getMainHandItem(), living);
                                if (elementBase != null && elementBase.createDamageSource(living) != event.getSource()) {
                                    damage = elementBase.onHit(event.getEntity(), living, damage, false);
                                }
                            }
                        }
                        if (hand == InteractionHand.OFF_HAND) {
                            ElementData elementBase = ParticleTracking.getItemElementForImbuement(living.getOffhandItem(), living);
                            if (elementBase != null && elementBase.createDamageSource(living) != event.getSource()) {
                                damage = elementBase.onHit(event.getEntity(), living, damage, false);
                            }
                        }
                    event.setAmount(damage);
                }
            }
        }
    }
}