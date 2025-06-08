package com.robson.pride.api.client;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ItemRenderingParams {

    private int r;

    private int g;

    private int b;

    private ResourceLocation texture;

    private RenderType direct_glint;

    private RenderType direct_entity_glint;

    public ItemRenderingParams(int r, int g, int b, ResourceLocation texture, RenderType direct_glint, RenderType direct_entity_glint) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.texture = texture;
        this.direct_glint = direct_glint;
        this.direct_entity_glint = direct_entity_glint;
    }

    public int getR() {
        return this.r;
    }

    public int getG() {
        return this.g;
    }

    public int getB() {
        return this.b;
    }

    public ResourceLocation getTexture() {
        return this.texture;
    }

    public RenderType getDirectGlint() {
        return this.direct_glint;
    }

    public RenderType getDirectEntityGlint() {
        return this.direct_entity_glint;
    }

}
