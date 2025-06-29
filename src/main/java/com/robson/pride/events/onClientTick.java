package com.robson.pride.events;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.api.client.RenderingCore;
import com.robson.pride.api.entity.PrideMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber
public class onClientTick {

    public static PoseStack playerStack = null;

    public static MultiBufferSource playerBuffer = null;

    @SubscribeEvent
    public static void renderUI(RenderGuiOverlayEvent event){
        if (event.getOverlay().id() == VanillaGuiOverlay.HOTBAR.id()){
            event.setCanceled(true);
            renderGui(Minecraft.getInstance(), event.getGuiGraphics());
            return;
        }
        if (!shouldRender(event.getOverlay().id())){
            event.setCanceled(true);
        }
    }

    public static void renderGui(Minecraft mc, GuiGraphics guiGraphics){
        if (mc.player != null){
            BakedModel bakedmodel = mc.getItemRenderer().getModel(mc.player.getItemInHand(InteractionHand.MAIN_HAND), mc.level, mc.player, 0);
            PoseStack pose = guiGraphics.pose();
            pose.pushPose();
           pose.translate((float)(10 + 8), (float)(20 + 8), (float)(150 + (bakedmodel.isGui3d() ? 1 : 0)));

            try {
                pose.mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                pose.scale(32, 32, 32);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                mc.getItemRenderer().render(mc.player.getItemInHand(InteractionHand.MAIN_HAND), ItemDisplayContext.GUI, false, pose, guiGraphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                guiGraphics.flush();
                if (flag) {
                    Lighting.setupFor3DItems();
                }
            }
            catch (Throwable ignored) {
                      ;
                };
            pose.popPose();
            }
        }

    public static boolean shouldRender(ResourceLocation guiid){
        return guiid != VanillaGuiOverlay.ARMOR_LEVEL.id() &&
                guiid != VanillaGuiOverlay.EXPERIENCE_BAR.id() &&
                guiid != VanillaGuiOverlay.PLAYER_HEALTH.id() &&
                guiid != VanillaGuiOverlay.MOUNT_HEALTH.id() &&
                guiid != VanillaGuiOverlay.FOOD_LEVEL.id();
    }

    @SubscribeEvent
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (event != null && playerStack != event.getPoseStack() && playerBuffer != event.getMultiBufferSource()) {
            playerBuffer = event.getMultiBufferSource();
            playerStack = event.getPoseStack();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderEntity(RenderLivingEvent.Pre event) {
        Minecraft client = Minecraft.getInstance();
        if (!client.isPaused()) {
            if (event.getEntity() instanceof Player || event.getEntity() instanceof PrideMob) {
                RenderingCore.entityRenderer(event.getEntity());
            }
        }
    }
}

