package com.robson.pride.epicfight.weapontypes;

import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.main.Pride;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMWeaponColliders;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Pride.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponPresets {

    public static final Function<Item, CapabilityItem.Builder> PRIDE_GUN = (item -> {
        CapabilityItem.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GUN)
                .zoomInType(CapabilityItem.ZoomInType.CUSTOM)
                .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GUN))
                .collider(ColliderPreset.FIST)
                .hitSound(EpicFightSounds.BLUNT_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLUNT.get())
                .newStyleCombo(Styles.ONE_HAND, WOMAnimations.ENDERBLASTER_ONEHAND_SHOOT)
                .newStyleCombo(Styles.TWO_HAND, Animations.BIPED_CROSSBOW_SHOT)
                .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, WOMAnimations.ENDERBLASTER_ONEHAND_IDLE)
                .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, WOMAnimations.ENDERBLASTER_ONEHAND_WALK)
                .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.ENDERBLASTER_ONEHAND_RUN)
                .livingMotionModifier(Styles.COMMON, LivingMotions.BLOCK, WOMAnimations.ENDERBLASTER_ONEHAND_AIMING)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.BIPED_CROSSBOW_AIM);
        return builder;
    });

    public static final Function<Item, CapabilityItem.Builder> PRIDE_FIGHTNING_STYLE = (item -> {
        CapabilityItem.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_FIGHTNING_STYLE)
                .styleProvider((playerpatch) -> PrideStyles.DUAL_WIELD)
                .collider(ColliderPreset.FIST)
                .hitSound(EpicFightSounds.BLUNT_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLUNT.get())
                .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.INFERNAL_AUTO_1, AnimationsRegister.INFERNAL_AUTO_2, AnimationsRegister.INFERNAL_AUTO_3_A, WOMAnimations.ENDERBLASTER_ONEHAND_AUTO_1, AnimationsRegister.INFERNAL_FULLHOUSE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.INFERNAL_IDLE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.INFERNAL_IDLE)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, AnimationsRegister.INFERNAL_GUARD);
        return builder;
    });

    public static final Function<Item, CapabilityItem.Builder> PRIDE_SCYTHE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SCYTHE)
                .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SCYTHE))
                .collider(WOMWeaponColliders.SOLAR)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLADE.get())
                .newStyleCombo(Styles.ONE_HAND, WOMAnimations.ANTITHEUS_AUTO_1, WOMAnimations.ANTITHEUS_AUTO_2, WOMAnimations.ANTITHEUS_AUTO_3, WOMAnimations.ANTITHEUS_AUTO_4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND, WOMAnimations.RUINE_AUTO_1, WOMAnimations.RUINE_AUTO_2, WOMAnimations.RUINE_AUTO_3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
                .newStyleCombo(PrideStyles.SHIELD_OFFHAND, WOMAnimations.ANTITHEUS_AUTO_1, WOMAnimations.ANTITHEUS_AUTO_2, WOMAnimations.ANTITHEUS_AUTO_3, WOMAnimations.ANTITHEUS_AUTO_4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, WOMAnimations.AGONY_IDLE)
                .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, WOMAnimations.ANTITHEUS_WALK)
                .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.ANTITHEUS_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.RUINE_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.RUINE_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.RUINE_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SCYTHE);


        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> PRIDE_COLOSSALSWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_COLOSSALSWORD)
                .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_COLOSSALSWORD))
                .collider(WOMWeaponColliders.SOLAR)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLADE.get())
                .newStyleCombo(Styles.ONE_HAND, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_2, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND, WOMAnimations.SOLAR_AUTO_1, WOMAnimations.SOLAR_AUTO_2, WOMAnimations.SOLAR_AUTO_3, WOMAnimations.SOLAR_AUTO_4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
                .newStyleCombo(PrideStyles.SHIELD_OFFHAND, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_2, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_3, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, WOMAnimations.SOLAR_OBSCURIDAD_IDLE)
                .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, WOMAnimations.SOLAR_OBSCURIDAD_WALK)
                .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.SOLAR_OBSCURIDAD_RUN)
                .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.SOLAR_IDLE)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.SOLAR_WALK)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.SOLAR_RUN)
                .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_COLOSSALSWORD);
        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> PRIDE_LONGSWORD = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_LONGSWORD)
                .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_LONGSWORD))
                .collider(ColliderPreset.LONGSWORD)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .hitParticle(EpicFightParticles.HIT_BLADE.get())
                .newStyleCombo(Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
                .newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
                .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
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

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_longsword"), PRIDE_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MODID, "kickbox"), PRIDE_FIGHTNING_STYLE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_gun"), PRIDE_GUN);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_colossalsword"), PRIDE_COLOSSALSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_scythe"), PRIDE_SCYTHE);
    }
}

