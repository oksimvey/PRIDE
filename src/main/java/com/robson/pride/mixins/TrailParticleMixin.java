package com.robson.pride.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.item.weapons.CustomWeaponItem;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Function;

@Mixin(value = AbstractTrailParticle.class, remap = false)
@OnlyIn(Dist.CLIENT)
public class TrailParticleMixin extends TextureSheetParticle {
    @Mutable
    @Shadow
    @Final
    protected TrailInfo trailInfo;

    private ResourceLocation elementPath = null;

    protected TrailParticleMixin(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
    }

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public ParticleRenderType getRenderType() {
        return TRAIL_PROVIDER.apply(this.elementPath == null ? this.trailInfo.texturePath() : this.elementPath);
    }

    private static final Function<ResourceLocation, ParticleRenderType> TRAIL_PROVIDER = Util.memoize((texturePath) -> new ParticleRenderType() {
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.enableBlend();
            RenderSystem.disableCull();
            Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(true);
            RenderSystem.setShader(GameRenderer::getParticleShader);
            RenderSystem.setShaderTexture(0, texturePath);
            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.lightTexture().turnOnLightLayer();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        public void end(Tesselator tesselator) {
            tesselator.getBuilder().setQuadSorting(VertexSorting.DISTANCE_TO_ORIGIN);
            tesselator.end();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableCull();
            Minecraft mc = Minecraft.getInstance();
            mc.gameRenderer.lightTexture().turnOffLightLayer();
        }

        public String toString() {
            return "PRIDE:TRAIL";
        }
    });

    @Inject(at = @At(value = "TAIL"), method = "<init>", cancellable = true)
    private void injectTrailParticle(ClientLevel level, EntityPatch entitypatch, TrailInfo trailInfo, CallbackInfo ci) {
        AbstractTrailParticle trailParticle = ((AbstractTrailParticle) (Object) this);
        if (entitypatch instanceof LivingEntityPatch<?> livingEntityPatch){
            ItemStack item = livingEntityPatch.getValidItemInHand(trailInfo.hand());
            if (item.getTag() == null) {
                return;
            }
            if (item.getItem() instanceof CustomWeaponItem && WeaponData.getWeaponData(item) != null){
                this.trailInfo = WeaponData.getWeaponData(item).getTrailInfo(this.trailInfo);
            }
            if (ParticleTracking.shouldRenderParticle(item)) {
                ElementBase element = ParticleTracking.getItemElementForImbuement(item);
                if (element != null) {
                    ItemRenderingParams params = element.getItemRenderingParams();
                    if (params != null) {
                        trailParticle.setColor(params.getR(), params.getG(), params.getB());
                        this.elementPath = params.getTexture();
                    }
                }
            }
        }
    }
}