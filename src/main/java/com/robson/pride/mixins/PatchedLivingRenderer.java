package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.client.RenderingCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = PatchedLivingEntityRenderer.class, remap = false)
@OnlyIn(Dist.CLIENT)
public class PatchedLivingRenderer {

    @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;Lyesman/epicfight/world/capabilities/entitypatch/LivingEntityPatch;Lnet/minecraft/client/renderer/entity/EntityRenderer;Lnet/minecraft/client/renderer/MultiBufferSource;Lcom/mojang/blaze3d/vertex/PoseStack;IF)V",
    at = @At(value = "HEAD"))
    public void render(LivingEntity par1, LivingEntityPatch<?> par2, EntityRenderer<?> par3, MultiBufferSource par4, PoseStack par5, int par6, float par7, CallbackInfo ci) {
        if (!Minecraft.getInstance().isPaused()) {
            RenderingCore.entityRenderer(par2, par1);
        }
    }
}
