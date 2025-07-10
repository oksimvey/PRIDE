package com.robson.pride.api.data.types.skill;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.GenericItemData;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.mechanics.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.utils.ProgressionUtils.haveReqs;

public abstract class WeaponSkillData extends GenericItemData {

    private final int ManaConsumption;
    private final float StaminaConsumption;
    private List<SkillAnimation> motions;
    private final PerilousType perilousType;
    private final short id;

    public WeaponSkillData(CompoundTag tag, String name, short id, SkillCore.WeaponArtTier tier, byte SkillElement, int ManaConsumption, float StaminaConsumption, PerilousType perilousType) {
        super(tag);
        this.id = id;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
        this.perilousType = perilousType;
    }

    public short getId() {
        return id;
    }

    public static String getModelLocation(byte element) {
        return switch (element) {


            default -> "pride:item/scroll_wind";
        };
    }

    public void onClientTick(LivingEntity ent){

    }

    public float onHurt(LivingEntity dmgent, LivingEntity ent, LivingHurtEvent event){
        return 0;
    }

    public static ChatFormatting colorByTier(SkillCore.WeaponArtTier tier){
        return switch (tier){
            case MYTHICAL -> ChatFormatting.DARK_RED;
            case LEGENDARY -> ChatFormatting.YELLOW;
            case EPIC -> ChatFormatting.DARK_PURPLE;
            case RARE -> ChatFormatting.BLUE;
            case UNCOMMON -> ChatFormatting.GREEN;
            case COMMON -> ChatFormatting.WHITE;
        };
    }

    public abstract List<SkillAnimation> defineMotions(LivingEntity ent);


    public void tryToExecute(LivingEntity ent) {
        if (ent != null && SkillDataManager.ACTIVE_WEAPON_SKILL.get(ent) == null) {
            if (ent instanceof Player player) {
                if (StaminaUtils.getStamina(player) >= this.StaminaConsumption && ManaUtils.getMana(player) >= this.ManaConsumption && haveReqs(player)) {
                    StaminaUtils.consumeStamina(ent, this.StaminaConsumption);
                    ManaUtils.consumeMana(ent, this.ManaConsumption);
                    onExecution(ent, 0);
                }
                return;
            }
            onExecution(ent, 0);
        }
    }

    public void onExecution(LivingEntity ent, int currentAnim) {
        if (currentAnim == 0) {
            SkillDataManager.ACTIVE_WEAPON_SKILL.put(ent, this.getId());
            SkillDataManager.PERILOUS_MAP.put(ent, this.perilousType);
            this.motions = defineMotions(ent);
            if (TargetUtil.getTarget(ent) instanceof Player player) {
                PerilousAttack.playPerilous(player);
            }
        }
        if (ent != null && this.motions != null && currentAnim < this.motions.size()) {
            SkillAnimation animation = this.motions.get(currentAnim);
            int duration = animation.getDuration(ent);
            animation.play(ent);
            TimerUtil.schedule(() -> onExecution(ent, currentAnim + 1), duration, TimeUnit.MILLISECONDS);
        }
        else if (ent != null) {
            SkillDataManager.ACTIVE_WEAPON_SKILL.remove(ent);
            SkillDataManager.PERILOUS_MAP.remove(ent);
        }
    }
}
