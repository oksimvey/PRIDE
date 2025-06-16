package com.robson.pride.mixins;


import com.robson.pride.api.data.WeaponData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = EpicFightCapabilities.class, remap = false)
public class EpicFightCapabilitiesMixin {
    @Shadow
    @Final
    public static Capability<CapabilityItem> CAPABILITY_ITEM;

    @Redirect(
            method = {"getItemStackCapability"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;"
            )
    )
    private static LazyOptional<CapabilityItem> getFromConfig(ItemStack itemStack, Capability<CapabilityItem> capability) {
        return WeaponData.getWeaponData(itemStack) != null ? LazyOptional.of(() -> WeaponData.getWeaponData(itemStack).getItemcap(itemStack)) : itemStack.getCapability(CAPABILITY_ITEM);
    }

    @Redirect(
            method = {"getItemStackCapabilityOr"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getCapability(Lnet/minecraftforge/common/capabilities/Capability;)Lnet/minecraftforge/common/util/LazyOptional;"
            )
    )
    private static LazyOptional<CapabilityItem> getFromConfigOr(ItemStack itemStack, Capability<CapabilityItem> capability) {
        return WeaponData.getWeaponData(itemStack) != null ? LazyOptional.of(() -> WeaponData.getWeaponData(itemStack).getItemcap(itemStack)) : itemStack.getCapability(CAPABILITY_ITEM);
    }
}
