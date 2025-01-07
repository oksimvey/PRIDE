package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.TagCheckUtils;
import com.robson.pride.skills.weaponarts.DarknessCut;
import com.robson.pride.skills.weaponarts.FlameSlashSkill;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
        if (TagCheckUtils.itemsTagCheck(weapon, "skills/longswordskill")){
            LongSwordWeaponSkill.onExecution(ent);
        }
    }

    public static void weaponArtCore(LivingEntity ent, String weaponart){
        switch (weaponart){
            case "Flame Slash" -> FlameSlashSkill.onExecution(ent);
            case "Darkness Cut" -> DarknessCut.onExecution(ent);
        }
    }
}
