package com.robson.pride.api.skillcore;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.concurrent.ConcurrentHashMap;

import static com.robson.pride.api.utils.ProgressionUtils.haveReqs;

public abstract class WeaponSkillBase {

    private String SkillRarity;
    private String SkillElement;
    private float ManaConsumption;
    private float StaminaConsumption;

    public WeaponSkillBase(String SkillRarity, String SkillElement, float ManaConsumption, float StaminaConsumption) {
        this.SkillRarity = SkillRarity;
        this.SkillElement = SkillElement;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
    }

    public String getSkillRarity() {
        return this.SkillRarity;
    }

    public String getSkillElement(){
        return this.SkillElement;
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
