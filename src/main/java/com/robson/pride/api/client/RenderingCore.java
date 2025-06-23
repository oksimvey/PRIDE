package com.robson.pride.api.client;

import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.effect.PrideEffectBase;
import com.robson.pride.epicfight.styles.PrideStyles;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Armatures;

public class RenderingCore {


    public static void entityRenderer(LivingEntity ent) {
        if (ent != null) {
            ParticleTracking.tickParticleMapping(ent.getMainHandItem(), ent);
            ParticleTracking.tickParticleMapping(ent.getOffhandItem(), ent);
            ElementData element = ParticleTracking.getItemElementForImbuement(ent.getMainHandItem());
            if (element != null) {
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolR, element.getNormalParticleType(), ParticleTracking.getAABBForImbuement(ent.getMainHandItem(), ent), element.getParticleAmount());
            }
            if (ItemStackUtils.getStyle(ent) == PrideStyles.DUAL_WIELD) {
                ElementData element2 = ParticleTracking.getItemElementForImbuement(ent.getMainHandItem());
                if (element2 != null) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.get().toolL, element2.getNormalParticleType(), ParticleTracking.getAABBForImbuement(ent.getOffhandItem(), ent), element2.getParticleAmount());
                }
            }
            for (MobEffectInstance effect : ent.getActiveEffects()) {
                if (effect.getEffect() instanceof PrideEffectBase prideEffect) {
                    prideEffect.prideClientTick(ent);
                }
            }
        }
    }
}
