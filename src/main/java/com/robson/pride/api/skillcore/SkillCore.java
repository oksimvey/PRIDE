package com.robson.pride.api.skillcore;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;

import static com.robson.pride.registries.WeaponSkillRegister.WeaponSkills;

public class SkillCore {

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasweaponart")) {
                weaponArtCore(ent, ent.getMainHandItem().getTag().getString("weapon_art"));
            }
            else defaultSkillCore(ent, ent.getMainHandItem());
        }
    }

    public static void defaultSkillCore(LivingEntity ent, ItemStack weapon) {
       CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(weapon.getItem());
       if (tag != null){
           if (tag.contains("skill")){
               WeaponSkillBase skill = WeaponSkills.get(tag.getString("skill"));
               if (skill != null){
                   skill.tryToExecute(ent);
               }
           }
       }
    }

    public static void weaponArtCore(LivingEntity ent, String weaponart) {
        WeaponSkillBase skill = WeaponSkills.get(weaponart);
        if (skill != null){
            skill.tryToExecute(ent);
        }
    }

    public static boolean canHit(Entity dmgent, Entity target, String skillname, int skillid) {
        if (dmgent != null && target != null) {
            return target instanceof LivingEntity && !DamageSources.isFriendlyFireBetween(target, dmgent) && target != dmgent && target.getPersistentData().getInt(skillname) != skillid;
        }
        return false;
    }
}
