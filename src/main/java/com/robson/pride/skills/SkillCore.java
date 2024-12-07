package com.robson.pride.skills;

import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TagCheckUtils;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class SkillCore {

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasWeaponArt")){
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
