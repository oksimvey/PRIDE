package com.robson.pride.api.client;

import com.robson.pride.api.utils.math.FixedRGB;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ItemRenderingParams {

    private FixedRGB color;

    private RenderType direct_glint;

    private RenderType direct_entity_glint;

    public ItemRenderingParams(FixedRGB color,  RenderType direct_glint, RenderType direct_entity_glint) {
        this.color = color;
        this.direct_glint = direct_glint;
        this.direct_entity_glint = direct_entity_glint;
    }

    public FixedRGB getColor() {
        return this.color;
    }


    public RenderType getDirectGlint() {
        return this.direct_glint;
    }

    public RenderType getDirectEntityGlint() {
        return this.direct_entity_glint;
    }

}
