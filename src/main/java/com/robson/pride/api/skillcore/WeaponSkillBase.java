package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import static com.robson.pride.api.utils.ProgressionUtils.haveReqs;

public abstract class WeaponSkillBase {

    private String SkillRarity;
    private String SkillElement;
    private int ManaConsumption;
    private float StaminaConsumption;
    private int duration;

    public WeaponSkillBase(String SkillRarity, String SkillElement, int ManaConsumption, float StaminaConsumption, int duration) {
        this.SkillRarity = SkillRarity;
        this.SkillElement = SkillElement;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
        this.duration = duration;
    }

    public String getSkillRarity() {
        return this.SkillRarity;
    }

    public String getSkillElement(){
        return this.SkillElement;
    }

    public int getDuration(){
        return this.duration;
    }

    public void tryToExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                if (StaminaUtils.getStamina(player) >= this.StaminaConsumption && ManaUtils.getMana(player) >= this.ManaConsumption && haveReqs(player)) {
                    StaminaUtils.consumeStamina(ent, this.StaminaConsumption);
                    ManaUtils.consumeMana(ent, this.ManaConsumption);
                    onExecution(ent);
                }
            }
            else onExecution(ent);
        }
    }

    public void onExecution(LivingEntity ent) {
        if (ent != null) {
            if (ItemStackUtils.getStyle(ent) == CapabilityItem.Styles.TWO_HAND) {
                twohandExecute(ent);
            }
            else onehandExecute(ent);
        }
    }

    public abstract void twohandExecute(LivingEntity ent);

    public abstract void onehandExecute(LivingEntity ent);
}
