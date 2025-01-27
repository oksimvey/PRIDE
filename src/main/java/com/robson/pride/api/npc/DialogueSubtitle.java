package com.robson.pride.api.npc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.robson.pride.progression.ProgressionGUIRender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class DialogueSubtitle extends Screen {

    CompoundTag tag = ProgressionGUIRender.playertags.get(Minecraft.getInstance().player);

    public DialogueSubtitle() {
        super(Component.literal("Conditional Overlay"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks); // Chama o método da classe base (Screen)
        Minecraft minecraft = Minecraft.getInstance();
        if (tag != null) {
            if (tag.contains("subtitle")) {
                String text = tag.getString("subtitle");
                int width = minecraft.getWindow().getGuiScaledWidth();
                int height = minecraft.getWindow().getGuiScaledHeight();
                int textWidth = minecraft.font.width(text);
                int x = (width / 2) - (textWidth / 2);
                int y = height / 2;
                guiGraphics.drawString(minecraft.font, text, x, y, 0xFFFFFF);
            }
        }
    }
}