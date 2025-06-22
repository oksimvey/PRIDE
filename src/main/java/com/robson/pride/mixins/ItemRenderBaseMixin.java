package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Pose;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.model.armature.types.ToolHolderArmature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;

@Mixin(RenderItemBase.class)
@OnlyIn(Dist.CLIENT)
public abstract class ItemRenderBaseMixin {

    @Shadow @Final private boolean alwaysInHand;

    @Shadow @Final protected Map<String, OpenMatrix4f> mainhandCorrectionTransforms;

    @Shadow @Final protected static Map<String, OpenMatrix4f> GLOBAL_MAINHAND_ITEM_TRANSFORMS;

    @Shadow @Final protected Map<String, OpenMatrix4f> offhandCorrectionTransforms;

    @Shadow @Final protected static Map<String, OpenMatrix4f> GLOBAL_OFFHAND_ITEM_TRANSFORMS;

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        HumanoidArmature armature = Armatures.BIPED.get();
        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(entitypatch, hand, poses);
        boolean isInMainhand = (hand == InteractionHand.MAIN_HAND);
        Joint holdingHand = isInMainhand ? armature.toolR : armature.toolL;
        modelMatrix.mulFront(poses[holdingHand.getId()]);
        poseStack.pushPose();
        this.mulPoseStack(poseStack, modelMatrix);
        ItemDisplayContext transformType = isInMainhand ? ItemDisplayContext.THIRD_PERSON_RIGHT_HAND : ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
        Minecraft mc = Minecraft.getInstance();
        mc.gameRenderer.itemInHandRenderer.renderItem(entitypatch.getOriginal(), stack, transformType, !isInMainhand, poseStack, buffer, packedLight);
        poseStack.popPose();
    }

    protected void mulPoseStack(PoseStack poseStack, OpenMatrix4f pose) {
        OpenMatrix4f transposed = pose.transpose(null);
        MathUtils.translateStack(poseStack, pose);
        MathUtils.rotateStack(poseStack, transposed.invert());
        MathUtils.scaleStack(poseStack, transposed);
    }


    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    protected OpenMatrix4f getCorrectionMatrix(LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses) {
        Joint parentJoint = this.alwaysInHand
                ? (entitypatch.getArmature() instanceof ToolHolderArmature toolArmature
                ? (hand == InteractionHand.MAIN_HAND ? toolArmature.rightToolJoint() : toolArmature.leftToolJoint())
                : entitypatch.getArmature().rootJoint)
                : entitypatch.getParentJointOfHand(hand);

        OpenMatrix4f baseMatrix = switch (hand) {
            case MAIN_HAND -> new OpenMatrix4f(this.mainhandCorrectionTransforms.getOrDefault(
                    parentJoint.getName(), GLOBAL_MAINHAND_ITEM_TRANSFORMS.get(parentJoint.getName())));
            case OFF_HAND -> new OpenMatrix4f(this.offhandCorrectionTransforms.getOrDefault(
                    parentJoint.getName(), GLOBAL_OFFHAND_ITEM_TRANSFORMS.get(parentJoint.getName())));
        };
        return baseMatrix;
    }
}
