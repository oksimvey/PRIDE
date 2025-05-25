package com.robson.pride.api.client;

import com.robson.pride.api.entity.PrideMobBase;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.effect.PrideEffectBase;
import com.robson.pride.epicfight.styles.PrideStyles;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Armatures;

public class RenderingCore{

    public static void renderCore() {
        Minecraft client = Minecraft.getInstance();
        RenderScreens.renderPlayerScreens(client);
        if (client.player != null) {
            entityRenderer(client.player);
            for (Entity ent : client.player.level().getEntities(client.player,  MathUtils.createAABBAroundEnt(client.player, 50))){
                if (ent instanceof PrideMobBase living && living.tickLod(client, 1) && client.levelRenderer.getFrustum().isVisible(ent.getBoundingBox())) {
                       entityRenderer(living);
                }
            }
        }
    }

    public static void entityRenderer(LivingEntity ent) {
        if (ent != null) {
            if (ParticleTracking.shouldRenderParticle(ent.getMainHandItem(), ent)) {
                ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ParticleTracking.getParticle(ent.getMainHandItem(), ent), ParticleTracking.getAABBForImbuement(ent.getMainHandItem(), ent));
            }
            if (ItemStackUtils.getStyle(ent) == PrideStyles.DUAL_WIELD) {
                if (ParticleTracking.shouldRenderParticle(ent.getOffhandItem(), ent)) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolL, ParticleTracking.getParticle(ent.getOffhandItem(), ent), ParticleTracking.getAABBForImbuement(ent.getOffhandItem(), ent));
                }
            }
            for (MobEffectInstance effect : ent.getActiveEffects()){
                if (effect.getEffect() instanceof PrideEffectBase prideEffect){
                    prideEffect.prideClientTick(ent);
                }
            }
        }
    }
}
