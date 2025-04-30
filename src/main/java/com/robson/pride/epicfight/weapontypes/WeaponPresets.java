package com.robson.pride.epicfight.weapontypes;

import M6FGR.dualaxes.gameassets.DualAxesAnimations;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.main.Pride;
import com.robson.pride.registries.AnimationsRegister;
import diego.efds.gameasset.DualSpearsAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.WOMWeaponColliders;
import reascer.wom.skill.WOMSkillDataKeys;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Pride.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponPresets {

    public static final Function<Item, CapabilityItem.Builder> PRIDE_SHORTSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SHORTSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SHORTSWORD))
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.TACHI_AUTO1, Animations.TACHI_AUTO2, Animations.TACHI_AUTO3, AnimationsRegister.SHORTSWORD_AUTO4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.SHORTSWORD_DUAL_AUTO1, AnimationsRegister.SHORTSWORD_DUAL_AUTO2, AnimationsRegister.SHORTSWORD_DUAL_AUTO3,  Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SHORTSWORD);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_LONGSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_LONGSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_LONGSWORD))
            .collider(ColliderPreset.LONGSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
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


    public static final Function<Item, CapabilityItem.Builder> PRIDE_GREATSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GREATSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GREATSWORD))
            .collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND,AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.BIPED_MOB_SWORD_DUAL1, Animations.BIPED_MOB_SWORD_DUAL2, Animations.BIPED_MOB_SWORD_DUAL3, Animations.SWORD_DUAL_DASH, DualAxesAnimations.AXE_DUAL_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, AnimationsRegister.GREATSWORD_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GREATSWORD);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_COLOSSALSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_COLOSSALSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_COLOSSALSWORD))
            .collider(WOMWeaponColliders.SOLAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_ONEHAND1, Animations.BIPED_MOB_ONEHAND2, DualAxesAnimations.AXE_AUTO_3, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, WOMAnimations.SOLAR_AUTO_1, WOMAnimations.SOLAR_AUTO_2, WOMAnimations.SOLAR_AUTO_3, WOMAnimations.SOLAR_AUTO_4, Animations.GREATSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.BIPED_MOB_ONEHAND1, Animations.BIPED_MOB_ONEHAND2, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_GREATSWORD_AUTO1, AnimationsRegister.DUAL_GREATSWORD_AUTO2, AnimationsRegister.DUAL_GREATSWORD_AUTO3, AnimationsRegister.DUAL_GREATSWORD_AUTO4, AnimationsRegister.DUAL_GREATSWORD_DASH, DualAxesAnimations.AXE_DUAL_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, WOMAnimations.STAFF_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.SOLAR_OBSCURIDAD_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.SOLAR_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.SOLAR_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.SOLAR_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, WOMAnimations.SOLAR_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.DUAL_GS_IDLE)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.DUAL_GS_WALK)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, AnimationsRegister.DUAL_GS_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_COLOSSALSWORD);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_AXE = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_AXE)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_AXE))
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, DualAxesAnimations.AXE_AUTO_1, DualAxesAnimations.AXE_AUTO_2, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, AnimationsRegister.SHORTSWORD_AUTO1, AnimationsRegister.SHORTSWORD_AUTO2, AnimationsRegister.SHORTSWORD_AUTO3, AnimationsRegister.SHORTSWORD_AUTO4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, DualAxesAnimations.AXE_AUTO_1, DualAxesAnimations.AXE_AUTO_2, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, DualAxesAnimations.AXE_DUAL_AUTO_1, DualAxesAnimations.AXE_DUAL_AUTO_2, DualAxesAnimations.AXE_DUAL_AUTO_3, DualAxesAnimations.AXE_DUAL_DASH, AnimationsRegister.DUAL_GREATSWORD_AIRSLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, DualAxesAnimations.AXE_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, DualAxesAnimations.AXE_DUAL_IDLE)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, DualAxesAnimations.AXE_DUAL_WALK)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, DualAxesAnimations.AXE_DUAL_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_AXE);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_GREATAXE = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GREATAXE)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GREATAXE))
            .collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, AnimationsRegister.GS_AUTO1, AnimationsRegister.GS_AUTO2, AnimationsRegister.GS_AUTO3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GREATAXE);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_SPEAR = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SPEAR)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SPEAR))
            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, WOMAnimations.HERRSCHER_AUTO_3, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SPEAR_ONEHAND, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND,WOMAnimations.HERRSCHER_AUTO_3, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SPEAR_ONEHAND, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, DualSpearsAnimations.SPEAR_DUAL_AUTO_1, DualSpearsAnimations.SPEAR_DUAL_AUTO_2, DualSpearsAnimations.SPEAR_DUAL_AUTO_3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.MAUL_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.MAUL_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, DualSpearsAnimations.SPEAR_DUAL_IDLE)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, DualSpearsAnimations.SPEAR_DUAL_WALK)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, DualSpearsAnimations.SPEAR_DUAL_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SPEAR);




    public static final Function<Item, CapabilityItem.Builder> PRIDE_GUN = (item -> (CapabilityItem.Builder) WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GUN)
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
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.BIPED_CROSSBOW_AIM));


    public static final Function<Item, CapabilityItem.Builder> PRIDE_KATANA = (item) -> WeaponCapability.builder().category(CapabilityItem.WeaponCategories.UCHIGATANA)
            .styleProvider(livingEntityPatch -> {
                if (livingEntityPatch instanceof PlayerPatch playerpatch && playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().hasData((SkillDataKey) WOMSkillDataKeys.SHEATH.get()) && (Boolean)playerpatch.getSkill(SkillSlots.WEAPON_PASSIVE).getDataManager().getDataValue((SkillDataKey)WOMSkillDataKeys.SHEATH.get())) {
                    return Styles.SHEATH;
                }
                return ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_KATANA);
            })
            .passiveSkill(EpicFightSkills.BATTOJUTSU_PASSIVE)
            .collider(WOMWeaponColliders.KATANA)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.SHEATH, Animations.UCHIGATANA_SHEATHING_AUTO, Animations.UCHIGATANA_SHEATHING_DASH, Animations.UCHIGATANA_SHEATHING_AUTO)
            .newStyleCombo(Styles.ONE_HAND, Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.TACHI_AUTO1, Animations.TACHI_AUTO2, Animations.TACHI_AUTO3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_UCHIGATANA)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.ANTITHEUS_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING)
            .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.KATANA_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.RUINE_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_KATANA);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_SCYTHE = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SCYTHE)
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

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_longsword"), PRIDE_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_gun"), PRIDE_GUN);
        event.getTypeEntry().put(new ResourceLocation(Pride.MODID, "pride_axe"), PRIDE_AXE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_colossalsword"), PRIDE_COLOSSALSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_scythe"), PRIDE_SCYTHE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_greataxe"), PRIDE_GREATAXE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_katana"), PRIDE_KATANA);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_shortsword"), PRIDE_SHORTSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_greatsword"), PRIDE_GREATSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_spear"), PRIDE_SPEAR);
    }
}

