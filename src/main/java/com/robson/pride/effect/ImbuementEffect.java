package com.robson.pride.effect;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.yukami.epicironcompat.animation.Animation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.registries.WeaponSkillRegister.elements;

public class ImbuementEffect extends PrideEffectBase {

    public boolean active;

    public String element;

    public ImbuementEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    public void setElement(String element) {
        this.element = element;
        this.active = false;
    }

    public void onEffectStart(LivingEntity ent){
        AnimUtils.playAnim(ent, Animation.CHANTING_TWO_HAND_BACK, 0.05f);
        TimerUtil.schedule(()-> {
            if (ent != null){
                this.active = true;
                AnimUtils.playAnim(ent, Animation.CASTING_TWO_HAND_BACK, 0.15f);
                ElementalUtils.playSoundByElement(this.element, ent, 1);
            }
        }, 1500, TimeUnit.MILLISECONDS);
    }

    public void onEffectEnd(LivingEntity ent){
    }

    @Override
    public void prideClientTick(LivingEntity ent) {
        if (this.active && elements.contains(element) && ent.tickCount % ((int) (10 / ent.getBbHeight())) == 0) {
                if (!element.equals("Sun") || ParticleTracking.shouldRenderSunParticle(ent)) {
                    Vec3f vec3f = ParticleTracking.getAABBForImbuement(null, ent);
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ElementalUtils.getParticleByElement(this.element), vec3f);
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolL, ElementalUtils.getParticleByElement(this.element), vec3f);
                }
            }
        }
}
