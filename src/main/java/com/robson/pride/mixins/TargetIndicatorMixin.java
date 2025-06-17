package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import yesman.epicfight.client.gui.EntityUI;
import yesman.epicfight.client.gui.TargetIndicator;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.effect.VisibleMobEffect;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;

@Mixin(TargetIndicator.class)
@OnlyIn(Dist.CLIENT)
public abstract class TargetIndicatorMixin extends EntityUI {
    private static final ResourceLocation STATUS_BAR = new ResourceLocation("pride", "textures/screens/bar.png");

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void draw(LivingEntity entityIn, @Nullable LivingEntityPatch<?> entitypatch, LocalPlayerPatch playerpatch, PoseStack matStackIn, MultiBufferSource bufferIn, float partialTicks) {
        float height = 0.5f ;
        Matrix4f mvMatrix = super.getModelViewMatrixAlignedToCamera(matStackIn, entityIn, 0.0F, entityIn.getBbHeight() + height, 0.0F, true, partialTicks);
        Collection<MobEffectInstance> activeEffects = entityIn.getActiveEffects();
        if (!activeEffects.isEmpty() && !entityIn.is(playerpatch.getOriginal())) {
            Iterator<MobEffectInstance> iter = activeEffects.iterator();
            int acives = activeEffects.size();
            int row = acives > 1 ? 1 : 0;
            int column = (acives - 1) / 2;
            float startX = -0.8F + -0.3F * (float)row;
            float startY = -0.15F + 0.15F * (float)column;
            for(int i = 0; i <= column; ++i) {
                for(int j = 0; j <= row; ++j) {
                    MobEffectInstance effectInstance = (MobEffectInstance)iter.next();
                    MobEffect effect = effectInstance.getEffect();
                    ResourceLocation rl;
                    if (effect instanceof VisibleMobEffect) {
                        VisibleMobEffect visibleMobEffect = (VisibleMobEffect)effect;
                        rl = visibleMobEffect.getIcon(effectInstance);
                    } else {
                        rl = new ResourceLocation(ForgeRegistries.MOB_EFFECTS.getKey(effect).getNamespace(), "textures/mob_effect/" + ForgeRegistries.MOB_EFFECTS.getKey(effect).getPath() + ".png");
                    }

                    Minecraft.getInstance().getTextureManager().bindForSetup(rl);
                    float x = startX + 0.3F * (float)j;
                    float y = startY + -0.3F * (float)i;
                    VertexConsumer vertexBuilder1 = bufferIn.getBuffer(EpicFightRenderTypes.entityUITexture(rl));
                    drawUIAsLevelModel(mvMatrix, STATUS_BAR, bufferIn, -0.1F, -0.1F, 0.1F, 0.1F, 97, 2, 128, 33, 256);
                    if (!iter.hasNext()) {
                        break;
                    }
                }
            }
        }

        VertexConsumer vertexBuilder = bufferIn.getBuffer(EpicFightRenderTypes.entityUITexture(STATUS_BAR));
        float ratio = Mth.clamp(entityIn.getHealth() / entityIn.getMaxHealth(), 0.0F, 1.0F);
        float healthRatio = -0.5F + ratio;
        int textureRatio = (int)(65.0F * ratio);
        drawUIAsLevelModel(mvMatrix, STATUS_BAR, bufferIn, -0.1F, -0.1F, 0.1F, 0.1F, 97, 2, 128, 33, 256);
        ;
        drawUIAsLevelModel(mvMatrix, STATUS_BAR, bufferIn, -0.1F, -0.1F, 0.1F, 0.1F, 97, 2, 128, 33, 256);

        if (entitypatch instanceof PlayerPatch playerPatch) {
            this.renderStamina(playerPatch, mvMatrix, bufferIn);
        }
    }


    private void renderStamina(PlayerPatch iac, Matrix4f mvMatrix, MultiBufferSource bufferIn) {
        float ratio = Mth.clamp(iac.getStamina() / iac.getMaxStamina(), 0.0F, 1.0F);
        float barRatio = -0.5F + ratio;
        int textureRatio = (int)(63.0F * ratio);
        drawUIAsLevelModel(mvMatrix, STATUS_BAR, bufferIn, -0.1F, -0.1F, 0.1F, 0.1F, 97, 2, 128, 33, 256);

    }
}
