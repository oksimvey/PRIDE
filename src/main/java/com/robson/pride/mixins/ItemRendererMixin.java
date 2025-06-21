package com.robson.pride.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.robson.pride.api.elements.ElementBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import com.robson.pride.api.mechanics.ParticleTracking;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemRenderer.class)
@OnlyIn(Dist.CLIENT)
public abstract class ItemRendererMixin implements ResourceManagerReloadListener {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Shadow public abstract ItemModelShaper getItemModelShaper();

    @Redirect(at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/ItemRenderer;getFoilBufferDirect(Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/renderer/RenderType;ZZ)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
            method = "render")
    public VertexConsumer overlayRendererDirect(MultiBufferSource multiBuffer, RenderType p_115224_, boolean p_115225_, boolean p_115226_, ItemStack item, ItemDisplayContext context, boolean bool) {
        LocalPlayer localplayer = Minecraft.getInstance().player;
        if (localplayer != null && item.getTag() != null) {
            if (ParticleTracking.shouldRenderParticle(item)) {
                return getEffectFoilBufferDirect(item, multiBuffer, p_115224_, true, true);
            }
        }
        return ItemRenderer.getFoilBufferDirect(multiBuffer, p_115224_, true, false);
    }


    private static VertexConsumer getEffectFoilBufferDirect(ItemStack item, MultiBufferSource mbs, RenderType p_115224_, boolean p_115225_, boolean hasFoil) {
        ElementBase elementBase = ParticleTracking.getItemElementForImbuement(item);
        VertexConsumer cons = hasFoil ? VertexMultiConsumer.create(mbs.getBuffer(p_115225_ ? elementBase.getItemRenderingParams().getDirectGlint() : elementBase.getItemRenderingParams().getDirectEntityGlint()), mbs.getBuffer(p_115224_)) : mbs.getBuffer(p_115224_);
        return cons;
    }


    public void blit(PoseStack poseStack, int x, int y, int width, int height, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight) {
        innerBlit(poseStack, x, x + width, y, y + height, 0, uWidth, vHeight, uOffset, vOffset, textureWidth, textureHeight);
    }

    public void blit(PoseStack poseStack, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight) {
        blit(poseStack, x, y, width, height, uOffset, vOffset, width, height, textureWidth, textureHeight);
    }

    private void innerBlit(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_) {
        innerBlit(p_93188_.last().pose(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float) p_93198_, (p_93196_ + (float) p_93194_) / (float) p_93198_, (p_93197_ + 0.0F) / (float) p_93199_, (p_93197_ + (float) p_93195_) / (float) p_93199_);
    }

    private void innerBlit(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferbuilder.vertex(p_93113_, (float) p_93114_, (float) p_93117_, (float) p_93118_).uv(p_93119_, p_93122_).endVertex();
        bufferbuilder.vertex(p_93113_, (float) p_93115_, (float) p_93117_, (float) p_93118_).uv(p_93120_, p_93122_).endVertex();
        bufferbuilder.vertex(p_93113_, (float) p_93115_, (float) p_93116_, (float) p_93118_).uv(p_93120_, p_93121_).endVertex();
        bufferbuilder.vertex(p_93113_, (float) p_93114_, (float) p_93116_, (float) p_93118_).uv(p_93119_, p_93121_).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
    }
}
