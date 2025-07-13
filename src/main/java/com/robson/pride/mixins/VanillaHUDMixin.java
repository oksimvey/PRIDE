package com.robson.pride.mixins;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.gui.PrideOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
@OnlyIn(Dist.CLIENT)
public abstract class VanillaHUDMixin {

    @Shadow
    @Final
    protected Minecraft minecraft;

    @Shadow protected abstract Player getCameraPlayer();

    @Shadow protected int screenHeight;

    @Shadow protected int screenWidth;


    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    public void renderHotbar(float p_283031_, GuiGraphics graphics, CallbackInfo ci) {
        Player player = getCameraPlayer();
        if (minecraft == null || graphics == null || player == null){
            return;
        }
        int selectedslot = player.getInventory().selected;
        int previousslot = selectedslot != 0 ? selectedslot - 1 : 8;
        int nextslot = selectedslot != 8 ? selectedslot + 1 : 0;
        ItemStack itemstack = player.getInventory().getItem(selectedslot);
        if (itemstack.isEmpty() || itemstack.getItem() instanceof AirItem) {
            return;
        }
        int height = this.screenHeight - 50;
        int width = this.screenWidth / 10;
        renderItem(minecraft, graphics, itemstack, player, width, height, true);
    }

    private void renderItem(Minecraft minecraft, GuiGraphics graphics, ItemStack stack, Player player, int x, int y, boolean main) {
        if (minecraft == null) {
            return;
        }
        float scale = main ? 2.5f : 1.5f;
        BakedModel bakedmodel = minecraft.getItemRenderer().getModel(stack, minecraft.level, player, 0);
        PoseStack pose = new PoseStack();
        pose.pushPose();
        pose.translate((float) (x + 8), (float) (y + 8), (float) (150));
        pose.scale(scale, scale, scale);
        try {
            pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
            pose.scale(16.0F, 16.0F, 16.0F);
            boolean flag = !bakedmodel.usesBlockLight();
            if (flag) {
                Lighting.setupForFlatItems();
            }

            minecraft.getItemRenderer().render(stack, ItemDisplayContext.GUI, false, pose, graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
            graphics.flush();
            if (flag) {
                Lighting.setupFor3DItems();
            }
        } catch (Throwable ignored) {
        }
    }

}
