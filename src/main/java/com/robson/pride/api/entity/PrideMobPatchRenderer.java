package com.robson.pride.api.entity;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.forgeevent.PrepareModelEvent;
import yesman.epicfight.api.client.model.SkinnedMesh;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.renderer.patched.entity.PatchedEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@OnlyIn(Dist.CLIENT)
public class PrideMobPatchRenderer extends PatchedEntityRenderer<PrideMob, PrideMobPatch<PrideMob>, EntityRenderer<PrideMob>, SkinnedMesh> {
    private final AssetAccessor<SkinnedMesh> mesh;

    public PrideMobPatchRenderer(AssetAccessor<SkinnedMesh> mesh, EntityRendererProvider.Context context) {
        this.mesh = mesh;
    }

    public void render(PrideMob entity, PrideMobPatch<PrideMob> entitypatch, EntityRenderer<PrideMob> renderer, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        super.render(entity, entitypatch, renderer, buffer, poseStack, packedLight, partialTicks);
        Minecraft mc = Minecraft.getInstance();
        boolean isGlowing = mc.shouldEntityAppearGlowing(entity);
        ResourceLocation textureLocation = renderer.getTextureLocation(entity);
        RenderType renderType = isGlowing ? RenderType.outline(textureLocation) : RenderType.entityCutoutNoCull(textureLocation);
        Armature armature = entitypatch.getArmature();
        poseStack.pushPose();
        this.mulPoseStack(poseStack, armature, entity, entitypatch, partialTicks);
        this.setArmaturePose(entitypatch, armature, partialTicks);
        if (renderType != null) {
            SkinnedMesh mesh = (SkinnedMesh)this.mesh.get();
            PrepareModelEvent prepareModelEvent = new PrepareModelEvent(this, mesh, entitypatch, buffer, poseStack, packedLight, partialTicks);
            if (!MinecraftForge.EVENT_BUS.post(prepareModelEvent)) {
                mesh.draw(poseStack, buffer, renderType, packedLight, 1.0F, 1.0F, 1.0F, !entity.isInvisibleTo(mc.player) ? 0.15F : 1.0F, this.getOverlayCoord(entity, entitypatch, partialTicks), armature, armature.getPoseMatrices());
            }
        }

        if (Minecraft.getInstance().getEntityRenderDispatcher().shouldRenderHitBoxes()) {
            entitypatch.getClientAnimator().renderDebuggingInfoForAllLayers(poseStack, buffer, partialTicks);
        }

        poseStack.popPose();
    }

    public void renderHumanoid(){}

    protected int getOverlayCoord(PrideMob entity,PrideMobPatch<PrideMob> entitypatch, float partialTicks) {
        return OverlayTexture.pack(0, OverlayTexture.v(entity.hurtTime > 5));
    }

    public AssetAccessor<SkinnedMesh> getDefaultMesh() {
        return this.mesh;
    }
}
