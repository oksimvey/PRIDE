package com.robson.pride.progression;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.ProgressionUtils;
import com.robson.pride.api.utils.TagsUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class ProgressionGUIRender extends AbstractContainerScreen<ProgressionGUI> {

    private final Player player;

    public ProgressionGUIRender(ProgressionGUI container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.player = container.entity;
        this.imageWidth = 257;
        this.imageHeight = 192;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        guiGraphics.blit(new ResourceLocation("pride:textures/screens/progression_gui.png"), this.leftPos + -4, this.topPos + -14, 0, 0, 264, 195, 264, 195);

        guiGraphics.blit(new ResourceLocation("minecraft:textures/mob_effect/strength.png"), this.leftPos + 18, this.topPos + 13, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("minecraft:textures/mob_effect/resistance.png"), this.leftPos + 18, this.topPos + 40, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("minecraft:textures/mob_effect/health_boost.png"), this.leftPos + 18, this.topPos + 65, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("pride:textures/screens/tile033.png"), this.leftPos + 18, this.topPos + 92, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("pride:textures/screens/tile033.png"), this.leftPos + 16, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (player != null) {
            CompoundTag variables = TagsUtils.playerTags.get(player);
            byte element = ElementalUtils.getElement(player);
            if (variables == null){
                return;
            }
            guiGraphics.drawString(this.font,
                    ("Strength Level:" + variables.getInt("StrengthLvl")), 118, 14, -1, false);
            guiGraphics.drawString(this.font,

                    ("Money:" + variables.getLong("pride_money")), 118, 25, -1, false);
            guiGraphics.drawString(this.font,

                    (element + " Level:" + variables.getLong(element + "Lvl")), 118, 30, -1, false);
            guiGraphics.drawString(this.font,

                    (element + " XP" + variables.getInt(element + "Xp") + "/" + variables.getInt(element + "MaxXp")), 118, 35, -1, false);
            guiGraphics.drawString(this.font,

                    ("Dexterity Level:" + variables.getInt("DexterityLvl")), 119, 42, -1, false);
            guiGraphics.drawString(this.font,

                    ("Vigor Level:" + variables.getInt("VigorLvl")), 102, 68, -1, false);
            guiGraphics.drawString(this.font,

                    ("Endurance Level:" + variables.getInt("EnduranceLvl")), 125, 93, -1, false);
            guiGraphics.drawString(this.font,

                    ("Mind Level:" + variables.getInt("MindLvl")), 98, 121, -1, false);
            guiGraphics.drawString(this.font,

                    ("Total Level:" + ProgressionUtils.getTotalLevel(player) + "        Weight:"), 47, 167, -1, false);
            guiGraphics.drawString(this.font,

                    ("XP:" + variables.getInt("StrengthXp") + "/" + variables.getInt("StrengthMaxXp")), 62, 28, -1, false);
            guiGraphics.drawString(this.font,

                    ("XP:" + variables.getInt("DexterityXp") + "/" + variables.getInt("DexterityMaxXp")), 61, 55, -1, false);
            guiGraphics.drawString(this.font,

                    ("XP:" + variables.getInt("VigorXp") + "/" + variables.getInt("VigorMaxXp")), 63, 82, -1, false);
            guiGraphics.drawString(this.font,

                    ("XP:" + variables.getInt("EnduranceXp") + "/" + variables.getInt("EnduranceMaxXp")), 63, 107, -1, false);
            guiGraphics.drawString(this.font,

                    ("XP:" + variables.getInt("MindXp") + "/" + variables.getInt("MindMaxXp")), 63, 135, -1, false);
            guiGraphics.drawString(this.font,

                    (Math.round((AttributeUtils.getAttributeValue(player, "epicfight:weight")) - 39) + "/" + Math.round(AttributeUtils.getAttributeValue(player, "pride:max_weight"))), 188, 161, -1, false);
            guiGraphics.drawString(this.font,

                    (ProgressionUtils.getLoadPercentage(player) + "%"), 188, 173, -1, false);
        }
    }

    @Override
    public void init() {
        super.init();
    }
}
