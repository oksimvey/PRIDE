package com.robson.pride.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.utils.math.Vec2i;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.gui.EntityIndicator;
import yesman.epicfight.client.gui.ModIngameGui;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.config.EpicFightOptions;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;

import java.util.List;

@Mixin(BattleModeGui.class)
@OnlyIn(Dist.CLIENT)
public abstract class BattleGuiMixin extends ModIngameGui {

    @Shadow private int sliding;

    @Shadow private boolean slidingToggle;

    @Shadow @Final private EpicFightOptions config;

    @Shadow public Font font;

    @Shadow @Final private List<SkillContainer> skillIcons;

    @Shadow protected abstract void drawWeaponInnateIcon(LocalPlayerPatch playerpatch, SkillContainer container, GuiGraphics guiGraphics, float partialTicks);

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void renderGui(LocalPlayerPatch playerpatch, GuiGraphics guiGraphics, float partialTicks) {
        if (((LocalPlayer)playerpatch.getOriginal()).isAlive() && ((LocalPlayer)playerpatch.getOriginal()).getVehicle() == null) {
            if (this.sliding <= 28) {
                if (this.sliding > 0) {
                    if (this.slidingToggle) {
                        this.sliding -= 2;
                    } else {
                        this.sliding += 2;
                    }
                }

                Window sr = Minecraft.getInstance().getWindow();
                int width = sr.getGuiScaledWidth();
                int height = sr.getGuiScaledHeight();
                boolean depthTestEnabled = GL11.glGetBoolean(2929);
                boolean blendEnabled = GL11.glGetBoolean(3042);
                if (depthTestEnabled) {
                    RenderSystem.disableDepthTest();
                }

                if (!blendEnabled) {
                    RenderSystem.enableBlend();
                }

                PoseStack poseStack = guiGraphics.pose();
                poseStack.pushPose();
                poseStack.setIdentity();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                float maxStamina = playerpatch.getMaxStamina();
                float stamina = playerpatch.getStamina();
                if (maxStamina > 0.0F && stamina < maxStamina) {
                    Vec2i pos = this.config.getStaminaPosition(width, height);
                    float prevStamina = playerpatch.getPrevStamina();
                    float ratio = (prevStamina + (stamina - prevStamina) * partialTicks) / maxStamina;
                    poseStack.pushPose();
                    poseStack.translate(0.0F, (float)this.sliding, 0.0F);
                    RenderSystem.setShaderColor(1.0F, ratio, 0.25F, 1.0F);
                    int size = (int) (maxStamina / 10);
                    guiGraphics.blit(EntityIndicator.BATTLE_ICON, pos.x, pos.y, (int) maxStamina, 4, size, 38.0F, 237, 9, 255, 255);
                    guiGraphics.blit(EntityIndicator.BATTLE_ICON, pos.x, pos.y, (int)(maxStamina * ratio), 4, size, 47.0F, (int)(237.0F * ratio), 9, 255, 255);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    poseStack.popPose();
                }

                if (playerpatch.isChargingSkill()) {
                    int chargeAmount = playerpatch.getChargingSkill().getChargingAmount(playerpatch);
                    int prevChargingAmount = playerpatch.getPrevChargingAmount();
                    float ratio = Math.min(((float)prevChargingAmount + (float)(chargeAmount - prevChargingAmount) * partialTicks) / (float)playerpatch.getChargingSkill().getMaxChargingTicks(), 1.0F);
                    Vec2i pos = this.config.getChargingBarPosition(width, height);
                    poseStack.pushPose();
                    poseStack.translate(0.0F, (float)this.sliding, 0.0F);
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    guiGraphics.blit(EntityIndicator.BATTLE_ICON, pos.x, pos.y, 1.0F, 71.0F, 238, 13, 255, 255);
                    guiGraphics.blit(EntityIndicator.BATTLE_ICON, pos.x, pos.y, 1.0F, 57.0F, (int)(238.0F * ratio), 13, 255, 255);
                    ResourceLocation rl = new ResourceLocation(playerpatch.getChargingSkill().toString());
                    String skillName = Component.translatable(String.format("skill.%s.%s", rl.getNamespace(), rl.getPath())).getString();
                    int stringWidth = this.font.width(skillName);
                    guiGraphics.drawString(this.font, skillName, (float)(pos.x + 120) - (float)stringWidth * 0.5F, (float)(pos.y - 12), 16777215, true);
                    poseStack.popPose();
                }

                for(int i = 0; i < SkillSlot.ENUM_MANAGER.universalValues().size(); ++i) {
                    SkillContainer container = playerpatch.getSkill(i);
                    if (!container.isEmpty() && !this.skillIcons.contains(container) && container.getSkill().shouldDraw(container)) {
                        this.skillIcons.add(container);
                    }
                }

                this.skillIcons.removeIf((skillContainer) -> skillContainer.isEmpty() || !skillContainer.getSkill().shouldDraw(skillContainer));
                SkillContainer innateSkillContainer = playerpatch.getSkill(SkillSlots.WEAPON_INNATE);
                if (!innateSkillContainer.isEmpty()) {
                    this.drawWeaponInnateIcon(playerpatch, playerpatch.getSkill(SkillSlots.WEAPON_INNATE), guiGraphics, partialTicks);
                }

                ClientConfig.AlignDirection alignDirection = (ClientConfig.AlignDirection)this.config.passivesAlignDirection.getValue();
                ClientConfig.HorizontalBasis horBasis = (ClientConfig.HorizontalBasis)this.config.passivesXBase.getValue();
                ClientConfig.VerticalBasis verBasis = (ClientConfig.VerticalBasis)this.config.passivesYBase.getValue();
                int passiveX = (Integer)horBasis.positionGetter.apply(width, (Integer)this.config.passivesX.getValue());
                int passiveY = (Integer)verBasis.positionGetter.apply(height, (Integer)this.config.passivesY.getValue());
                int icons = this.skillIcons.size();
                Vec2i slotCoord = alignDirection.startCoordGetter.get(passiveX, passiveY, 24, 24, icons, horBasis, verBasis);

                for(SkillContainer container : this.skillIcons) {
                    if (!container.isEmpty()) {
                        Skill skill = container.getSkill();
                        if (skill.shouldDraw(container)) {
                            RenderSystem.enableBlend();
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                            skill.drawOnGui((BattleModeGui)(Object) this, container, guiGraphics, (float)slotCoord.x, (float)slotCoord.y);
                            slotCoord = alignDirection.nextPositionGetter.getNext(horBasis, verBasis, slotCoord, 24, 24);
                        }
                    }
                }

                poseStack.popPose();
                if (depthTestEnabled) {
                    RenderSystem.enableDepthTest();
                }

                if (!blendEnabled) {
                    RenderSystem.disableBlend();
                }

            }
        }
    }
}
