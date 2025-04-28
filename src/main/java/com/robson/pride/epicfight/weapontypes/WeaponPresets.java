package com.robson.pride.epicfight.weapontypes;

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
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SHORTSWORD, false))
            .collider(ColliderPreset.SWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, AnimationsRegister.SHORTSWORD_AUTO1, AnimationsRegister.SHORTSWORD_AUTO2, AnimationsRegister.SHORTSWORD_AUTO3, AnimationsRegister.SHORTSWORD_AUTO4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
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


    public static final Function<Item, CapabilityItem.Builder> PRIDE_GREATSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GREATSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GREATSWORD, false))
            .collider(ColliderPreset.GREATSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND,AnimationsRegister.SWORD_ONEHAND_AUTO1, AnimationsRegister.SWORD_ONEHAND_AUTO2, AnimationsRegister.SWORD_ONEHAND_AUTO3, AnimationsRegister.SWORD_ONEHAND_AUTO4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, Animations.BIPED_MOB_SWORD_DUAL1, Animations.BIPED_MOB_SWORD_DUAL2, Animations.BIPED_MOB_SWORD_DUAL3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, AnimationsRegister.GREATSWORD_HOLD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, AnimationsRegister.GREATSWORD_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GREATSWORD);

    public static final Function<Item, CapabilityItem.Builder> PRIDE_SPEAR = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_SPEAR)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SPEAR, false))
            .collider(ColliderPreset.SPEAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, WOMAnimations.AGONY_AUTO_1, WOMAnimations.HERRSCHER_AUTO_3, WOMAnimations.AGONY_AUTO_1, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND,Animations.DAGGER_AUTO1, Animations.DAGGER_AUTO2, Animations.DAGGER_AUTO3, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, DualSpearsAnimations.SPEAR_DUAL_AUTO_1, DualSpearsAnimations.SPEAR_DUAL_AUTO_2, DualSpearsAnimations.SPEAR_DUAL_AUTO_3, DualSpearsAnimations.SPEAR_DUAL_AUTO_4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, DualSpearsAnimations.SPEAR_DUAL_IDLE)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, DualSpearsAnimations.SPEAR_DUAL_WALK)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, DualSpearsAnimations.SPEAR_DUAL_RUN)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_SPEAR);




    public static final Function<Item, CapabilityItem.Builder> PRIDE_GUN = (item -> (CapabilityItem.Builder) WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GUN)
            .zoomInType(CapabilityItem.ZoomInType.CUSTOM)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GUN, false))
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
                return ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_KATANA, false);
            })
            .collider(WOMWeaponColliders.KATANA)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.SHEATH, Animations.UCHIGATANA_SHEATHING_AUTO)
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
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_SCYTHE, false))
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

    public static final Function<Item, CapabilityItem.Builder> PRIDE_GREATAXE = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_GREATAXE)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_GREATAXE, false))
            .collider(WOMWeaponColliders.SOLAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, WOMAnimations.TORMENT_BERSERK_AUTO_1, WOMAnimations.TORMENT_BERSERK_AUTO_2, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, WOMAnimations.TORMENT_AUTO_1, WOMAnimations.TORMENT_AUTO_2, WOMAnimations.TORMENT_AUTO_3, WOMAnimations.TORMENT_AUTO_4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, WOMAnimations.TORMENT_BERSERK_AUTO_1, WOMAnimations.TORMENT_BERSERK_AUTO_2, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.DUAL_WIELD, AnimationsRegister.DUAL_TACHI_AUTO1, AnimationsRegister.DUAL_TACHI_AUTO2, AnimationsRegister.DUAL_TACHI_AUTO3, AnimationsRegister.DUAL_TACHI_AUTO4, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
            .newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.IDLE, WOMAnimations.TORMENT_BERSERK_IDLE)
            .livingMotionModifier(Styles.COMMON, LivingMotions.WALK, WOMAnimations.TORMENT_BERSERK_WALK)
            .livingMotionModifier(Styles.COMMON, LivingMotions.RUN, WOMAnimations.TORMENT_BERSERK_RUN)
            .livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, WOMAnimations.TORMENT_IDLE)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, WOMAnimations.TORMENT_WALK)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, WOMAnimations.TORMENT_RUN)
            .livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.IDLE, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.WALK, Animations.BIPED_HOLD_DUAL_WEAPON)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.RUN, Animations.BIPED_RUN_DUAL)
            .livingMotionModifier(PrideStyles.DUAL_WIELD, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
            .weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory() == WeaponCategoriesEnum.PRIDE_GREATAXE);


    public static final Function<Item, CapabilityItem.Builder> PRIDE_COLOSSALSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_COLOSSALSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_COLOSSALSWORD, false))
            .collider(WOMWeaponColliders.SOLAR)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_2, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_4, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, WOMAnimations.SOLAR_AUTO_1, WOMAnimations.SOLAR_AUTO_2, WOMAnimations.SOLAR_AUTO_3, WOMAnimations.SOLAR_AUTO_4, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
            .newStyleCombo(PrideStyles.SHIELD_OFFHAND, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_1, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_2, WOMAnimations.SOLAR_OBSCURIDAD_AUTO_4, WOMAnimations.GESETZ_AUTO_1, Animations.SWORD_AIR_SLASH)
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

    public static final Function<Item, CapabilityItem.Builder> PRIDE_LONGSWORD = (item) -> WeaponCapability.builder().category(WeaponCategoriesEnum.PRIDE_LONGSWORD)
            .styleProvider(livingEntityPatch -> ItemStackUtils.getStyle(livingEntityPatch, WeaponCategoriesEnum.PRIDE_LONGSWORD, false))
            .collider(ColliderPreset.LONGSWORD)
            .hitSound(EpicFightSounds.BLADE_HIT.get())
            .hitParticle(EpicFightParticles.HIT_BLADE.get())
            .newStyleCombo(Styles.ONE_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.BIPED_MOB_ONEHAND1, Animations.SWORD_AIR_SLASH)
            .newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_LIECHTENAUER_AUTO1, Animations.LONGSWORD_LIECHTENAUER_AUTO2, Animations.LONGSWORD_LIECHTENAUER_AUTO3, Animations.BIPED_MOB_LONGSWORD2, Animations.LONGSWORD_AIR_SLASH)
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

    @SubscribeEvent
    public static void register(WeaponCapabilityPresetRegistryEvent event) {
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_longsword"), PRIDE_LONGSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_gun"), PRIDE_GUN);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_colossalsword"), PRIDE_COLOSSALSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_scythe"), PRIDE_SCYTHE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_greataxe"), PRIDE_GREATAXE);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_katana"), PRIDE_KATANA);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_shortsword"), PRIDE_SHORTSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_greatsword"), PRIDE_GREATSWORD);
        event.getTypeEntry().put(new ResourceLocation(Pride.MOD_ID, "pride_spear"), PRIDE_SPEAR);
    }
}

