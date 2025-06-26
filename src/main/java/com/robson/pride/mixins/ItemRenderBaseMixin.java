package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;

@Mixin(RenderItemBase.class)
@OnlyIn(Dist.CLIENT)
public abstract class ItemRenderBaseMixin {

    @Redirect(method = "renderItemInHand", at = @At(value = "INVOKE", target = "Lyesman/epicfight/api/utils/math/MathUtils;mulStack(Lcom/mojang/blaze3d/vertex/PoseStack;Lyesman/epicfight/api/utils/math/OpenMatrix4f;)V"), remap = false)
    protected void mulPoseStack(PoseStack poseStack, OpenMatrix4f pose) {
        OpenMatrix4f transposed = pose.transpose(null);
        MathUtils.translateStack(poseStack, pose);
        MathUtils.rotateStack(poseStack, transposed.invert());
        MathUtils.scaleStack(poseStack, transposed);
    }
}