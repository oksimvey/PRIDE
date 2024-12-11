package com.robson.pride.epicfight.weapontypes;

import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.forgeevent.WeaponCategoryIconRegisterEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static com.robson.pride.main.Pride.LOGGER;


public class WeaponGuardMotions {


    public static void regIcon(WeaponCategoryIconRegisterEvent event){
        event.registerCategory(WeaponCategoriesEnum.PRIDE_LONGSWORD,new ItemStack(ItemsRegister.EuropeanLongsword.get()));
    }

    public static boolean regGuarded=false;

    public static void buildSkillEvent(RegisterEvent event){

        if (EpicFightSkills.GUARD==null){return;}
        if (regGuarded){return;}
        try {
            regGuard();
        }catch (Exception e){
            e.printStackTrace();
        }
        regGuarded=true;
    }
    public static void regGuard() throws NoSuchFieldException, IllegalAccessException {
        LOGGER.info("buildSkillEvent");
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions=new HashMap<>();
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> advancedGuardMotions=new HashMap<>();

        guardMotions.put(WeaponCategoriesEnum.PRIDE_LONGSWORD,
                (item, player) -> {
                    if (item.getStyle(player) == CapabilityItem.Styles.TWO_HAND) {
                        return Animations.LONGSWORD_GUARD_HIT;
                    }  else if (item.getStyle(player) == PrideStyles.DUAL_WIELD) {
                        return Animations.SWORD_DUAL_GUARD_HIT;
                    }  else {
                        return Animations.SWORD_GUARD_HIT;
                    }
                });
        guardBreakMotions.put(WeaponCategoriesEnum.PRIDE_LONGSWORD,
                (item, player) -> Animations.BIPED_COMMON_NEUTRALIZED);
        advancedGuardMotions.put(WeaponCategoriesEnum.PRIDE_LONGSWORD, (itemCap, playerpatch) -> {
            if (itemCap.getStyle(playerpatch) == CapabilityItem.Styles.TWO_HAND) {
                return new StaticAnimation[] { Animations.LONGSWORD_GUARD_ACTIVE_HIT1, Animations.LONGSWORD_GUARD_ACTIVE_HIT2 };
            }
            else if (itemCap.getStyle(playerpatch) == PrideStyles.DUAL_WIELD) {
                return new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT2, Animations.SWORD_GUARD_ACTIVE_HIT3 };
            }  else {
                return new StaticAnimation[] { Animations.SWORD_GUARD_ACTIVE_HIT1, Animations.SWORD_GUARD_ACTIVE_HIT2 };
            }
        });


        Field temp;
        Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> target;
        temp= GuardSkill.class.getDeclaredField("guardMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardMotions.keySet()){
            target.put(weaponCapability,guardMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("guardBreakMotions");
        temp.setAccessible(true);
        target= (Map) temp.get(EpicFightSkills.GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }
        target=(Map) temp.get(EpicFightSkills.IMPACT_GUARD);
        for (WeaponCategory weaponCapability:guardBreakMotions.keySet()){
            target.put(weaponCapability,guardBreakMotions.get(weaponCapability));
        }


        temp=GuardSkill.class.getDeclaredField("advancedGuardMotions");
        temp.setAccessible(true);
        target=(Map) temp.get(EpicFightSkills.PARRYING);
        for (WeaponCategory weaponCapability:advancedGuardMotions.keySet()){
            target.put(weaponCapability,advancedGuardMotions.get(weaponCapability));
        }
    }
}
