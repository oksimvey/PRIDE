package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.entity.PrideMobPatch;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.gui.EntityUI;
import yesman.epicfight.client.gui.TargetIndicator;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(TargetIndicator.class)
@OnlyIn(Dist.CLIENT)
public abstract class TargetIndicatorMixin extends EntityUI {

    private static final ResourceLocation STATUS_BAR = new ResourceLocation("pride", "textures/screens/bar.png");

    @Inject(method = "draw", at = @At(value = "TAIL"), remap = false)
    public void draw(LivingEntity entity, LivingEntityPatch<?> entitypatch, LocalPlayerPatch playerpatch, PoseStack poseStack, MultiBufferSource buffers, float partialTicks, CallbackInfo ci) {
        Matrix4f mvMatrix = super.getModelViewMatrixAlignedToCamera(poseStack, entity, 0.0F, entity.getBbHeight() + 0.5f, 0.0F, true, partialTicks);
        if (entitypatch instanceof PrideMobPatch<?> pridemobpatch) {
            this.renderStamina(pridemobpatch, mvMatrix, buffers);
        }
    }


    private void renderStamina(PrideMobPatch iac, Matrix4f mvMatrix, MultiBufferSource bufferIn) {
        float ratio = Mth.clamp(iac.getStamina() / iac.getMaxStamina(), 0.0F, 1.0F);
        float barRatio = -0.5F + ratio;
        int textureRatio = (int)(63.0F * ratio);
        drawUIAsLevelModel(mvMatrix, STATUS_BAR,bufferIn, -0.5F, -0.15F, barRatio, -0.05F, 1, 5, 10, 15);
        drawUIAsLevelModel(mvMatrix, STATUS_BAR,bufferIn, barRatio, -0.15F, 0.5F, -0.05F, textureRatio, 0, 63, 7);
    }
}
