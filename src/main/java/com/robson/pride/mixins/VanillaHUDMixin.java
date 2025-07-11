package com.robson.pride.mixins;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
@OnlyIn(Dist.CLIENT)
public class VanillaHUDMixin {
}
