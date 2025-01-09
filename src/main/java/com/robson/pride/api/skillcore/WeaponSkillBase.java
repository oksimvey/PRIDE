package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public abstract class WeaponSkillBase{

    private String SkillRarity;
    private float ManaConsumption;
    private float StaminaConsumption;

    public WeaponSkillBase(String SkillRarity, float ManaConsumption, float StaminaConsumption){
        this.SkillRarity = SkillRarity;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
    }

    public String getSkillRarity(){return this.SkillRarity;}

    public void tryToExecute(LivingEntity ent){
        if (ent != null){
            if (ent instanceof Player player){
                if (StaminaUtils.getStamina(player) >= this.StaminaConsumption && ManaUtils.getMana(player) >= this.ManaConsumption){
                    StaminaUtils.consumeStamina(ent, this.StaminaConsumption);
                    ManaUtils.consumeMana(ent, this.ManaConsumption);
                    onExecution(ent);
                }
            }
            else onExecution(ent);
        }
    }

    public void onExecution(LivingEntity ent){
        if (ent != null){
            if (ItemStackUtils.getStyle(ent) == CapabilityItem.Styles.TWO_HAND){
                twohandExecute(ent);
            }
            else onehandExecute(ent);
        }
    }

    public void twohandExecute(LivingEntity ent){
    }
    public void onehandExecute(LivingEntity ent){
    }
}
