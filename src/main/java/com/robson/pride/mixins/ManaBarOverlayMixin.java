package com.robson.pride.mixins;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.config.ClientConfigs;
import io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import static io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay.TEXTURE;
import static io.redspace.ironsspellbooks.gui.overlays.ManaBarOverlay.shouldShowManaBar;

@Mixin(ManaBarOverlay.class)
public abstract class ManaBarOverlayMixin implements IGuiOverlay {

    @Shadow @Final
    static int TEXT_COLOR;

    private static int getBarX(ManaBarOverlay.Anchor anchor, int screenWidth) {
        if (anchor == ManaBarOverlay.Anchor.XP) {
            return screenWidth / 2 - 91 - 3;
        } else if (anchor != ManaBarOverlay.Anchor.Hunger && anchor != ManaBarOverlay.Anchor.Center) {
            return anchor != ManaBarOverlay.Anchor.TopLeft && anchor != ManaBarOverlay.Anchor.BottomLeft ? screenWidth - 20 - 98 : 20;
        } else {
            return screenWidth / 2 - 49 + (anchor == ManaBarOverlay.Anchor.Center ? 0 : 50);
        }
    }

    private static int getBarY(ManaBarOverlay.Anchor anchor, int screenHeight, ForgeGui gui) {
        if (anchor == ManaBarOverlay.Anchor.XP) {
            return screenHeight - 32 + 3 - 8;
        } else if (anchor == ManaBarOverlay.Anchor.Hunger) {
            return screenHeight - (getAndIncrementRightHeight(gui) - 2) - 10;
        } else if (anchor == ManaBarOverlay.Anchor.Center) {
            return screenHeight - 25 - 27 - 10;
        } else {
            return anchor != ManaBarOverlay.Anchor.TopLeft && anchor != ManaBarOverlay.Anchor.TopRight ? screenHeight - 20 - 21 : 20;
        }
    }

    private static int getAndIncrementRightHeight(ForgeGui gui) {
        int x = gui.rightHeight;
        gui.rightHeight += 10;
        return x;
    }

    public void render(ForgeGui gui, GuiGraphics guiHelper, float partialTick, int screenWidth, int screenHeight) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (shouldShowManaBar(player)) {
            int maxMana = (int)player.getAttributeValue((Attribute) AttributeRegistry.MAX_MANA.get());
            int mana = ClientMagicData.getPlayerMana();
            int configOffsetY = (Integer) ClientConfigs.MANA_BAR_Y_OFFSET.get();
            int configOffsetX = (Integer)ClientConfigs.MANA_BAR_X_OFFSET.get();
            ManaBarOverlay.Anchor anchor = (ManaBarOverlay.Anchor)ClientConfigs.MANA_BAR_ANCHOR.get();
            if (anchor != ManaBarOverlay.Anchor.XP || !(player.getJumpRidingScale() > 0.0F)) {
                int barX = this.getBarX(anchor, screenWidth) + configOffsetX;
                int barY = getBarY(anchor, screenHeight, gui) - configOffsetY;
                int imageWidth = maxMana / 2;
                int spriteX = anchor == ManaBarOverlay.Anchor.XP ? 68 : 0;
                int spriteY = anchor == ManaBarOverlay.Anchor.XP ? 40 : 0;
                guiHelper.blit(TEXTURE, barX, barY, (float)spriteX, (float)spriteY, imageWidth, 21, 256, 256);
                guiHelper.blit(TEXTURE, barX, barY, spriteX, spriteY + 21, (int)((double)imageWidth * Math.min((double)mana / (double)maxMana, (double)1.0F)), 21);
                String manaFraction = mana + "/" + maxMana;
                int textX = (Integer)ClientConfigs.MANA_TEXT_X_OFFSET.get() + barX + imageWidth / 2 - (int)(((double)("" + mana).length() + (double)0.5F) * (double)6.0F);
                int textY = (Integer)ClientConfigs.MANA_TEXT_Y_OFFSET.get() + barY + (anchor == ManaBarOverlay.Anchor.XP ? 3 : 11);
                if ((Boolean)ClientConfigs.MANA_BAR_TEXT_VISIBLE.get()) {
                    guiHelper.drawString(gui.getFont(), manaFraction, textX, textY, TEXT_COLOR);
                }

            }
        }
    }
}
