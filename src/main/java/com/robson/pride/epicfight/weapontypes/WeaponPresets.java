package com.robson.pride.epicfight.weapontypes;

import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.main.Pride;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.yonchi.refm.gameasset.RapierAnimations;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Pride.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WeaponPresets {

    public static List<LivingEntity> WIELDING_TWO_HAND = Collections.synchronizedList(new ArrayList<>());

    public static final Function<Item, CapabilityItem.Builder> PRIDE_SHORTSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SHORTSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SHORTSWORD))
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.TACHI_AUTO1, Animations.TACHI_AUTO2, Animations.TACHI_AUTO3, AnimationsRegister.SHORTSWORD_AUTO4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.SHORTSWORD_DUAL_AUTO1, AnimationsRegister.SHORTSWORD_DUAL_AUTO2, AnimationsRegister.SHORTSWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
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
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SHORTSWORD || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_LONGSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_LONGSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_LONGSWORD))
            .collider(ColliderPreset.LONGSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
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
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_LONGSWORD || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_GREATSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GREATSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GREATSWORD))
            .collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.BIPED_MOB_SWORD_DUAL1, Animations.BIPED_MOB_SWORD_DUAL2, Animations.BIPED_MOB_SWORD_DUAL3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DASH)
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
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GREATSWORD || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_COLOSSALSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_COLOSSALSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_COLOSSALSWORD))
            .collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_ONEHAND1, Animations.BIPED_MOB_ONEHAND2, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.BIPED_MOB_ONEHAND1, Animations.BIPED_MOB_ONEHAND2, Animations.GREATSWORD_AUTO1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_GREATSWORD_AUTO1, AnimationsRegister.DUAL_GREATSWORD_AUTO2, AnimationsRegister.DUAL_GREATSWORD_AUTO3, AnimationsRegister.DUAL_GREATSWORD_AUTO4, AnimationsRegister.DUAL_GREATSWORD_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.DUAL_GS_IDLE)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.DUAL_GS_WALK)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, AnimationsRegister.DUAL_GS_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_COLOSSALSWORD || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_RAPIER = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_RAPIER)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_RAPIER))
            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.TWO_HAND, RapierAnimations.RAPIER_AUTO1, RapierAnimations.RAPIER_AUTO2, RapierAnimations.RAPIER_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, AnimationsRegister.SPEAR_ONEHAND, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SPEAR_ONEHAND, AnimationsRegister.SPEAR_ONEHAND, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.DAGGER_DUAL_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, Animations.DAGGER_DUAL_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, RapierAnimations.BIPED_HOLD_RAPIER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, RapierAnimations.BIPED_WALK_RAPIER)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, RapierAnimations.BIPED_RUN_RAPIER)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_RAPIER || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_SPEAR = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SPEAR)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SPEAR))
            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.BIPED_MOB_SWORD_DUAL1, Animations.BIPED_MOB_SWORD_DUAL2, Animations.BIPED_MOB_SWORD_DUAL3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
             .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.MAUL_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.MAUL_HOLD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SPEAR || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_KATANA = (item) ->
            WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_KATANA)
                    .styleProvider(livingEntityPatch -> {
                        if (livingEntityPatch.getOriginal() instanceof Player player && player.getPersistentData().getBoolean("pride_sheat")) {
                            return Styles.SHEATH;
                        }
                        return ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_KATANA);
                    })
                    .collider(ColliderPreset.LONGSWORD)
                    .hitSound(EpicFightSounds.BLADE_HIT.get())
                    .hitParticle(EpicFightParticles.HIT_BLADE.get())
                    .newStyleCombo(Styles.SHEATH, Animations.UCHIGATANA_SHEATHING_AUTO, Animations.UCHIGATANA_SHEATHING_DASH, Animations.UCHIGATANA_SHEATHING_AUTO)
                    .newStyleCombo(Styles.ONE_HAND, Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
                    .newStyleCombo(Styles.TWO_HAND, AnimationsRegister.GREAT_TACHI_AUTO1, AnimationsRegister.GREAT_TACHI_AUTO2, AnimationsRegister.GREAT_TACHI_AUTO3, AnimationsRegister.GREAT_TACHI_AUTO4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
                    .newStyleCombo(PrideStyles.SHIELD_OFFHAND, Animations.UCHIGATANA_AUTO1, Animations.UCHIGATANA_AUTO2, Animations.UCHIGATANA_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
                    .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
                    .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA)
                    .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA)
                    .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_WALK_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_RUN_UCHIGATANA_SHEATHING)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, AnimationsRegister.KATANA_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, AnimationsRegister.KATANA_IDLE)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                    .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                    .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
                    .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
                    .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
                    .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_KATANA || EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GUN);

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_longsword"), PRIDE_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_colossalsword"), PRIDE_COLOSSALSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_katana"), PRIDE_KATANA);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_shortsword"), PRIDE_SHORTSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_greatsword"), PRIDE_GREATSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_spear"), PRIDE_SPEAR);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_rapier"), PRIDE_RAPIER);
    }
}

