package com.robson.pride.epicfight.weapontypes;

import com.google.common.collect.Maps;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.main.Pride;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Pride.MOD_ID , bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponPresets {

    public static final Function<Item, CapabilityItem.Builder> PRIDE_FIGHTNING_STYLE = (item -> {
        CapabilityItem.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_FIGHTNING_STYLE)
                .styleProvider((playerpatch)-> PrideStyles.DUAL_WIELD)
                .collider(ColliderPreset.FIST)
                .hitSound(EpicFightSounds.BLUNT_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLUNT.get())
                .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.INFERNAL_AUTO_1, AnimationsRegister.INFERNAL_AUTO_2, AnimationsRegister.INFERNAL_AUTO_3_A, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_1, AnimationsRegister.INFERNAL_FULLHOUSE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.INFERNAL_IDLE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.INFERNAL_IDLE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, AnimationsRegister.INFERNAL_GUARD)
                ;
        return builder;
    });

    public static final Function<Item, CapabilityItem.Builder> PRIDE_LONGSWORD = (item) -> {
    WeaponCapability.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_LONGSWORD)
            .styleProvider((playerpatch) -> {
                if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                    return PrideStyles.SHIELD_OFFHAND;
                }
                if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_LONGSWORD) {
                    return PrideStyles.DUAL_WIELD;
                }
                if (playerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN  && ControllEngine.isKeyDown(EpicFightKeyMappings.GUARD) && ControllEngine.isKeyDown(EpicFightKeyMappings.ATTACK)) {
                    return Styles.RANGED;
                }
                else if (playerpatch instanceof PlayerPatch<?> tplayerpatch && tplayerpatch.getOriginal().getMainHandItem().getTag() != null) {
                    return tplayerpatch.getOriginal().getMainHandItem().getTag().getBoolean("two_handed") && tplayerpatch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST ? Styles.TWO_HAND : Styles.ONE_HAND;
                }
                return Styles.ONE_HAND;
            })
            .collider(ColliderPreset.LONGSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND,  Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LIECHTENAUER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LIECHTENAUER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_LONGSWORD);


    return builder;
};
    public WeaponPresets() {
    }

    private static boolean CheckPlayer(LivingEntityPatch<?> playerPatch) {
        return playerPatch.getOriginal().getType() != EntityType.PLAYER;
    }

    private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID,"pride_longsword"), PRIDE_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MODID, "kickbox"), PRIDE_FIGHTNING_STYLE);
    }
}

