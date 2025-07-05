package com.robson.pride.gui;

import com.robson.pride.api.ai.actions.builder.AnswerBuilder;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.particles.StringParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class AnswerGUI extends Screen {

    private final List<AnswerBuilder> answers;

    private final PrideMobPatch<?> questionent;

    private final StringParticle subtitle;

    public AnswerGUI(PrideMobPatch<?> questionent, List<AnswerBuilder> answers, StringParticle subtitle) {
        super(Component.empty());
        this.answers = answers;
        this.questionent = questionent;
        this.minecraft = Minecraft.getInstance();
        this.font = this.minecraft.font;
        this.subtitle = subtitle;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void init() {
        if (this.questionent != null) {
            for (AnswerBuilder builder : this.answers) {
                this.addRenderableWidget(Button.builder(Component.literal(builder.getButtonText()), button -> {
                            onClose();
                            removeParticle();
                            onAnswer(builder);
                        })
                        .bounds(this.width / 2 - (this.answers.indexOf(builder) * 20), this.height, 200, 20)
                        .build());
            }
        }
    }

    public void removeParticle() {
        if (this.subtitle != null) {
            this.subtitle.remove();
        }
    }

    public void onAnswer(AnswerBuilder answerBuilder) {
        if (answerBuilder != null){
            answerBuilder.getAction().start(this.questionent);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}

