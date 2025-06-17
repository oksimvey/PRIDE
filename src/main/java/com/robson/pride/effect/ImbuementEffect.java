package com.robson.pride.effect;

import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.maps.ElementMap.ELEMENTS;

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

    public void onEffectStart(LivingEntity ent) {
        TimerUtil.schedule(() -> {
            if (ent != null) {
                this.active = true;
                ElementalUtils.playSoundByElement(this.element, ent, 1);
            }
        }, 1500, TimeUnit.MILLISECONDS);
    }

    public void onEffectEnd(LivingEntity ent) {
        ElementalUtils.playSoundByElement(this.element, ent, 1);
    }

    @Override
    public void prideClientTick(LivingEntity ent) {
        if (this.active && ELEMENTS.containsKey(element) && ent.tickCount % ((int) (10 / ent.getBbHeight())) == 0) {
            if (!element.equals("Sun") || ParticleTracking.shouldRenderSunParticle(ent)) {
                ElementBase element = ELEMENTS.get(this.element);
                Vec3f vec3f = ParticleTracking.getAABBForImbuement(null, ent);
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolR, element.getNormalParticleType(), vec3f, element.getParticleAmount());
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolL, element.getNormalParticleType(), vec3f, element.getParticleAmount());
            }
        }
    }
}
