package com.robson.pride.api.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class PrideMobRenderer extends HumanoidMobRenderer<PrideMobBase, HumanoidModel<PrideMobBase>> {

    public PrideMobRenderer(EntityRendererProvider.Context context) {
        super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(PrideMobBase entity) {
        if (!entity.textures.isEmpty()) {
            return  new ResourceLocation(entity.textures.get(entity.getTypeVariant()));
        }
        return new ResourceLocation("pride:textures/entities/special/empty.png");
    }
}