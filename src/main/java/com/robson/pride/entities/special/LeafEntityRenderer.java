package com.robson.pride.entities.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Objects;
import java.util.Random;

public class LeafEntityRenderer extends EntityRenderer<LeafEntity> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{IronsSpellbooks.id("textures/entity/flame_strike/flame_strike_1.png")};

    public LeafEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    public void render(LeafEntity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        poseStack.pushPose();
        PoseStack.Pose pose = poseStack.last();
        poseStack.mulPose(Axis.YP.rotationDegrees(90.0F - entity.getYRot()));
        poseStack.mulPose(Axis.ZP.rotationDegrees(entity.getXRot()));
        float randomZ = (float)(new Random(31L * (long)entity.getId())).nextInt(-8, 8);
        poseStack.mulPose(Axis.XP.rotationDegrees(randomZ));
        this.drawLeaf(pose, entity, bufferSource, entity.getBbWidth() * 1.5F, false);
        poseStack.popPose();
        super.render(entity, yaw, partialTicks, poseStack, bufferSource, light);
    }

    private void drawLeaf(PoseStack.Pose pose, LeafEntity entity, MultiBufferSource bufferSource, float width, boolean mirrored) {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(entity)));
        float halfWidth = width * 0.5F;
        float height = entity.getBbHeight() * 0.5F;
        consumer.vertex(poseMatrix, -halfWidth, height, -halfWidth).color(entity.getRed(), entity.getGreen(), entity.getBlue(), 255).uv(0.0F, mirrored ? 0.0F : 1.0F).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(normalMatrix, 0.0F, 1.0F, 0.0F).endVertex();
        consumer.vertex(poseMatrix, halfWidth, height, -halfWidth).color(entity.getRed(), entity.getGreen(), entity.getBlue(), 255).uv(1f, mirrored ? 0f : 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, halfWidth, height, halfWidth).color(entity.getRed(), entity.getGreen(), entity.getBlue(), 255).uv(1f, mirrored ? 1f : 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, -halfWidth, height, halfWidth).color(entity.getRed(), entity.getGreen(), entity.getBlue(), 255).uv(0f, mirrored ? 1f : 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();

    }

    public ResourceLocation getTextureLocation(LeafEntity entity) {
        int var10000 = entity.tickCount;
        Objects.requireNonNull(entity);
        int frame = var10000 / 2 % TEXTURES.length;
        return TEXTURES[frame];
    }
}
