package com.robson.pride.api.skillcore;

import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class SkillCore {

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasweaponart")){
                weaponArtCore(ent,  ent.getMainHandItem().getTag().getString("weapon_art"));
            }
            else defaultSkillCore(ent, ent.getMainHandItem());
        }
    }

    public static void defaultSkillCore(LivingEntity ent, ItemStack weapon){
        CapabilityItem itemcap = EpicFightCapabilities.getItemStackCapability(weapon);
        if (itemcap != null){
            if (itemcap.getWeaponCategory() instanceof WeaponCategoriesEnum categoriesEnum){
                categoriesEnum.skill().tryToExecute(ent);
            }
        }
    }

    public static void weaponArtCore(LivingEntity ent, String weaponart){
       SkillsEnum.valueOf(weaponart).skill().tryToExecute(ent);
    }

    public interface WeaponSkill extends ExtendableEnum {
        ExtendableEnumManager<WeaponSkill> ENUM_MANAGER = new ExtendableEnumManager<>("weapon_skill");
        WeaponSkillBase skill();
    }

    public static boolean canHit(Entity dmgent, Entity target, String skillname, int skillid){
        if (dmgent != null && target != null){
            return target instanceof LivingEntity && !DamageSources.isFriendlyFireBetween(target, dmgent) && target != dmgent && target.getPersistentData().getInt(skillname) != skillid;
        }
        return false;
    }
}
