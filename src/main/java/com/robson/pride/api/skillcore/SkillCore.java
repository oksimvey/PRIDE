package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.TagCheckUtils;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.ExtendableEnum;
import yesman.epicfight.api.utils.ExtendableEnumManager;

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
       SkillsEnum.valueOf(weaponart).skill().tryToExecute(ent);
    }

    public interface WeaponSkill extends ExtendableEnum {
        ExtendableEnumManager<WeaponSkill> ENUM_MANAGER = new ExtendableEnumManager<>("weapon_skill");
        WeaponSkillBase skill();
    }
}
