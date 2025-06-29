package com.robson.pride.gui;


import com.robson.pride.api.data.player.ClientData;
import com.robson.pride.api.data.player.ClientDataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ProgressionGUI extends Screen {

    private Player player;

    public ProgressionGUI(Player player,  Minecraft client) {
        super(Component.empty());
        this.player = player;
        this.minecraft = client;
        this.font = client.font;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        ClientData data = ClientDataManager.CLIENT_DATA_MANAGER.get(player);
        if (data != null && data.getProgressionData() != null) {
            for (byte i = 0; i < 5; i++) {
                String stat = switch (i){
                    case 1 -> "Dexterity";
                    case 2 -> "Vigor";
                    case 3 -> "Endurance";
                    case 4 -> "Mind";
                    default -> "Strength";
                };
                int xp = data.getProgressionData().getXP(i);
                int maxxp = data.getProgressionData().getMaxXP(i);
                byte level = data.getProgressionData().getLevel(i);
                int heightadjustment = i * 20;
                guiGraphics.drawString(this.font, stat + ": " + level, this.width / 2 - 100, this.height / 2 + heightadjustment, 0xFFFFFF);
                guiGraphics.drawString(this.font, xp + "/" + maxxp + " XP", this.width / 2 - 100, this.height / 2 + heightadjustment + 10, 0xFFFFFF);
            }
        }
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

