package com.robson.pride.api.ai.dialogues;

import com.robson.pride.api.utils.ClientPlayerTagsAcessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.particle.Particle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class AnswerGUI extends Screen {

    private final Player player;

    private final Entity questionent;

    private final Particle stringparticle;

    public AnswerGUI(Player player, Entity questionent, Particle stringparticle, Minecraft client) {
        super(Component.literal(""));
        this.player = player;
        this.questionent = questionent;
        this.stringparticle = stringparticle;
        this.minecraft = client;
        this.font = client.font;
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        super.init();
        if (this.player != null && this.questionent != null) {
            CompoundTag tags = ClientPlayerTagsAcessor.playerTags.get(player);
            this.addRenderableWidget(Button.builder(Component.literal(tags.getString("pride_true_answer")), button -> {
                        onClose();
                        removeParticle();
                        JsonInteractionsReader.deserializeDialogues(questionent, player, tags.getList("pride_true_answer_dialogues", 10), (byte) 0);
                    })
                    .bounds(this.width / 2 - 100, this.height / 2 - 30, 200, 20)
                    .build());
            this.addRenderableWidget(Button.builder(Component.literal(tags.getString("pride_false_answer")), button -> {
                        onClose();
                        removeParticle();
                        JsonInteractionsReader.deserializeDialogues(questionent, player, tags.getList("pride_false_answer_dialogues", 10), (byte) 0);
                    })
                    .bounds(this.width / 2 - 100, this.height / 2 + 10, 200, 20)
                    .build());
        }
    }

    public void removeParticle(){
        if (this.stringparticle != null){
            this.stringparticle.remove();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

