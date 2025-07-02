package com.robson.pride.api.data.types;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.mechanics.perilous.PerilousType;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.utils.ProgressionUtils.haveReqs;

public abstract class WeaponSkillData extends GenericData {

    private final int ManaConsumption;
    private final float StaminaConsumption;
    private List<SkillAnimation> motions;
    private final PerilousType perilousType;
    private final short id;

    public WeaponSkillData(String name, short id, SkillCore.WeaponArtTier tier, byte SkillElement, int ManaConsumption, float StaminaConsumption, PerilousType perilousType) {
        super(Component.literal(name + " Weapon Art(" + tier.name() + ")").withStyle(colorByTier(tier)),
                getModelLocation(SkillElement),
                new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f), SkillElement, (byte) 64);
        this.id = id;
        this.StaminaConsumption = StaminaConsumption;
        this.ManaConsumption = ManaConsumption;
        this.perilousType = perilousType;
    }

    public short getId() {
        return id;
    }

    public PerilousType getPerilousType() {
        return perilousType;
    }

    public static String getModelLocation(byte element) {
        return switch (element) {

            case ServerDataManager.DARKNESS -> "pride:item/scroll_darkness";

            case ServerDataManager.LIGHT -> "pride:item/scroll_light";

            case ServerDataManager.THUNDER -> "pride:item/scroll_thunder";

            case ServerDataManager.SUN -> "pride:item/scroll_sun";

            case ServerDataManager.MOON -> "pride:item/scroll_moon";

            case ServerDataManager.BLOOD -> "pride:item/scroll_blood";

            case ServerDataManager.NATURE -> "pride:item/scroll_nature";

            case ServerDataManager.ICE, ServerDataManager.WATER -> "pride:item/scroll_ice";

            default -> "pride:item/scroll_wind";
        };
    }

    public void onClientTick(LivingEntity ent){

    }

    public void onHurt(LivingEntity dmgent, LivingEntity ent, LivingHurtEvent event){
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
                    return;
                }
            }
            onExecution(ent, 0);
        }
    }

    public void onExecution(LivingEntity ent, int currentAnim) {
        if (currentAnim == 0) {
            SkillDataManager.ACTIVE_WEAPON_SKILL.put(ent, this);
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
        }
    }
}
