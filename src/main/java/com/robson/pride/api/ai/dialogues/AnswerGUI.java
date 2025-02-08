package com.robson.pride.api.ai.dialogues;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.particle.Particle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.isAnswering;

@OnlyIn(Dist.CLIENT)
public class AnswerGUI extends Screen {

    private final Player player;

    private final Entity questionent;

    private final Particle stringparticle;

    private final CompoundTag playerTags;

    public AnswerGUI(Player player, Entity questionent, Particle stringparticle, CompoundTag playerTags, Minecraft client) {
        super(Component.empty());
        this.player = player;
        this.questionent = questionent;
        this.stringparticle = stringparticle;
        this.playerTags = playerTags;
        this.minecraft = client;
        this.font = client.font;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        if (this.player != null && this.questionent != null && this.playerTags != null) {
            this.addRenderableWidget(Button.builder(Component.literal(this.playerTags.getString("pride_true_answer")), button -> {
                        onClose();
                        removeParticle();
                        JsonInteractionsReader.deserializeDialogues(questionent, player, this.playerTags.getList("pride_true_answer_dialogues", 10), (byte) 0);
                        onAnswer();
                    })
                    .bounds(this.width / 2 - 100, this.height / 2 - 30, 200, 20)
                    .build());
            this.addRenderableWidget(Button.builder(Component.literal(this.playerTags.getString("pride_false_answer")), button -> {
                        onClose();
                        removeParticle();
                        JsonInteractionsReader.deserializeDialogues(questionent, player, this.playerTags.getList("pride_false_answer_dialogues", 10), (byte) 0);
                        onAnswer();
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

    public void onAnswer(){
        isAnswering.remove(player);
        player.getPersistentData().remove("pride_true_answer");
        player.getPersistentData().remove("pride_false_answer");
        player.getPersistentData().remove("pride_true_answer_dialogues");
        player.getPersistentData().remove("pride_false_answer_dialogues");
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

