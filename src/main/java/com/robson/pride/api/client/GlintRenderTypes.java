package com.robson.pride.api.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GlintRenderTypes extends RenderType {

    public static final ResourceLocation FIRE_GLINT_LOCATION = new ResourceLocation("pride:textures/glints/fire_glint.png");
    public static final ResourceLocation VENOM_GLINT_LOCATION = new ResourceLocation("pride:textures/glints/venom_glint.png");
    public static final ResourceLocation FREEZE_GLINT_LOCATION = new ResourceLocation("pride:textures/glints/snow_glint.png");
    public static final ResourceLocation SPARK_GLINT_LOCATION = new ResourceLocation("pride:textures/glints/lightning_glint.png");
    private static final RenderType FIRE_GLINT_DIRECT = create("fire_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(FIRE_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType FIRE_ENTITY_GLINT_DIRECT = create("fire_entity_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(FIRE_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType VENOM_GLINT_DIRECT = create("venom_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(VENOM_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType VENOM_ENTITY_GLINT_DIRECT = create("venom_entity_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(VENOM_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType FREEZE_GLINT_DIRECT = create("freeze_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(FREEZE_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType FREEZE_ENTITY_GLINT_DIRECT = create("freeze_entity_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(FREEZE_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));

    private static final RenderType SPARK_GLINT_DIRECT = create("spark_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(SPARK_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(GLINT_TEXTURING).createCompositeState(false));
    private static final RenderType SPARK_ENTITY_GLINT_DIRECT = create("spark_entity_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(SPARK_GLINT_LOCATION, true, false)).setWriteMaskState(COLOR_WRITE).setCullState(NO_CULL).setDepthTestState(EQUAL_DEPTH_TEST).setTransparencyState(GLINT_TRANSPARENCY).setTexturingState(ENTITY_GLINT_TEXTURING).createCompositeState(false));


    public GlintRenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    public static RenderType getFireEntityGlintDirect() {
        return FIRE_ENTITY_GLINT_DIRECT;
    }

    public static RenderType getFireGlintDirect() {
        return FIRE_GLINT_DIRECT;
    }

    public static RenderType getVenomEntityGlintDirect() {
        return VENOM_ENTITY_GLINT_DIRECT;
    }

    public static RenderType getVenomGlintDirect() {
        return VENOM_GLINT_DIRECT;
    }

    public static RenderType getFreezeEntityGlintDirect() {
        return FREEZE_ENTITY_GLINT_DIRECT;
    }

    public static RenderType getFreezeGlintDirect() {
        return FREEZE_GLINT_DIRECT;
    }

    public static RenderType getSparkEntityGlintDirect() {
        return SPARK_ENTITY_GLINT_DIRECT;
    }

    public static RenderType getSparkGlintDirect() {
        return SPARK_GLINT_DIRECT;
    }
}

