package com.robson.pride.effect;

import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.ParticleUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import static com.robson.pride.registries.WeaponSkillRegister.elements;

public class ImbuementEffect extends PrideEffectBase {

    public String element;

    private byte tickcount = 0;

    public ImbuementEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    public void setElement(String element) {
        this.element = element;
    }

    @Override
    public void prideClientTick(LivingEntity ent) {
        if (tickcount < 100) {
            tickcount++;
        }
        if (elements.contains(element) && ent.tickCount % ((int) (10 / ent.getBbHeight())) == 0) {
            if (!element.equals("Sun") || ParticleTracking.shouldRenderSunParticle(ent)) {
                Vec3f vec3f = ParticleTracking.getAABBForImbuement(null, ent);
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ElementalUtils.getParticleByElement(this.element), vec3f);
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolL, ElementalUtils.getParticleByElement(this.element), vec3f);
            }
        }
    }
}
