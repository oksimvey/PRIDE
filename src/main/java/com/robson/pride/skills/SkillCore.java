package com.robson.pride.skills;

import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TagCheckUtils;
import com.robson.pride.skills.weaponarts.FlameSlashSkill;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class SkillCore {

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasWeaponArt")){
                weaponArtCore(ent,  ent.getMainHandItem().getTag().getString("WeaponArt"));
            }
            else defaultSkillCore(ent, ent.getMainHandItem());
        }
    }

    public static void defaultSkillCore(LivingEntity ent, ItemStack weapon){
        if (TagCheckUtils.itemsTagCheck(weapon, "skills/longswordskill")){
            LongSwordWeaponSkill.onExecution(ent);
        }
    }

    public static void weaponArtCore(LivingEntity ent, String weaponart){
        if (Objects.equals(weaponart, "flame_slash")){
            FlameSlashSkill.onExecution(ent);
        }
    }

    public static boolean consumptionCore(LivingEntity ent, float staminaconsumption, float manaconsumption){
        if (ManaUtils.getMana(ent) >= manaconsumption && StaminaUtils.getStamina(ent) >= staminaconsumption){
            ManaUtils.consumeMana(ent, manaconsumption);
            StaminaUtils.consumeStamina(ent, staminaconsumption);
            return true;
        }
        return false;
    }
}
