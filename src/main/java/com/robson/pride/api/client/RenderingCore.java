package com.robson.pride.api.client;


import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.effect.WetEffect;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;

public class RenderingCore {

    public static void renderCore() {
        if (Minecraft.getInstance().player != null) {
            LocalPlayer player = Minecraft.getInstance().player;
            entityRenderer(player);
            List<Entity> list = player.level().getEntities(player, MathUtils.createAABBForCulling(10));
            for (Entity ent : list) {
                if (ent instanceof LivingEntity living) {
                    entityRenderer(living);
                }
            }
        }
    }

    public static void entityRenderer(LivingEntity ent) {
        if (ent != null) {
            if (ent.hasEffect(EffectRegister.WET.get())){
                WetEffect.clientTick(ent);
            }
            if (ParticleTracking.shouldRenderParticle(ent.getMainHandItem(), ent)) {
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ParticleTracking.getParticle(ent.getMainHandItem()), ParticleTracking.getAABBForImbuement(ent.getMainHandItem(), ent));
            }
            if (ItemStackUtils.getStyle(ent) == PrideStyles.DUAL_WIELD) {
                if (ParticleTracking.shouldRenderParticle(ent.getOffhandItem(), ent)) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolL, ParticleTracking.getParticle(ent.getOffhandItem()), ParticleTracking.getAABBForImbuement(ent.getOffhandItem(), ent));
                }
            }
        }
    }
}
