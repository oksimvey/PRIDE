package com.robson.pride.api.skillcore;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.utils.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.lang.annotation.Target;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.mechanics.PerilousAttack.perilousParticle;
import static com.robson.pride.api.mechanics.PerilousAttack.playPerilous;
import static com.robson.pride.api.utils.ProgressionUtils.haveReqs;

public abstract class WeaponSkillBase {

    private String SkillRarity;
    private String SkillElement;
    private int ManaConsumption;
    private float StaminaConsumption;
    private List<SkillAnimation> motions;
    private String perilousType;

    public WeaponSkillBase(String SkillRarity, String SkillElement, int ManaConsumption, float StaminaConsumption, String perilousType) {
        this.SkillRarity = SkillRarity;
        this.SkillElement = SkillElement;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
        this.perilousType = perilousType;
    }

    public abstract List<SkillAnimation> defineMotions(LivingEntity ent);

    public String getSkillRarity() {
        return this.SkillRarity;
    }

    public String getSkillElement(){
        return this.SkillElement;
    }

    public void tryToExecute(LivingEntity ent) {
        if (ent != null && !SkillCore.performingSkillEntities.contains(ent)) {
            if (ent instanceof Player player) {
                if (StaminaUtils.getStamina(player) >= this.StaminaConsumption && ManaUtils.getMana(player) >= this.ManaConsumption && haveReqs(player)) {
                    StaminaUtils.consumeStamina(ent, this.StaminaConsumption);
                    ManaUtils.consumeMana(ent, this.ManaConsumption);
                    onExecution(ent, 0);
                    return;
                }
            }
            onExecution(ent, 0);
        }
    }

    public void onExecution(LivingEntity ent, int currentAnim){
        if (currentAnim == 0){
            SkillCore.performingSkillEntities.add(ent);
            this.motions = defineMotions(ent);
            ent.getPersistentData().putString("Perilous", this.perilousType);
            if (TargetUtil.getTarget(ent) instanceof Player player){
                PerilousAttack.playPerilous(player);
            }
        }
        if (ent != null && this.motions != null && currentAnim < this.motions.size()) {
            SkillAnimation animation = this.motions.get(currentAnim);
            int duration = animation.getDuration(ent);
            animation.play(ent);
            TimerUtil.schedule(()-> onExecution(ent, currentAnim + 1), duration, TimeUnit.MILLISECONDS);
        }
        else {
            SkillCore.performingSkillEntities.remove(ent);
             if (ent != null){
                 ent.getPersistentData().remove("Perilous");
             }
        };
    }
}
