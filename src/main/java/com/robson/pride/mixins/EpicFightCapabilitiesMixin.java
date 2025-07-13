package com.robson.pride.mixins;


import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.types.item.WeaponData;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = EpicFightCapabilities.class, remap = false)
public class EpicFightCapabilitiesMixin {

    @Inject(method = "getItemStackCapability", at = @At(value = "TAIL"), cancellable = true)
    private static void getFromConfig(ItemStack stack, CallbackInfoReturnable<CapabilityItem> cir) {
        WeaponData data = WeaponDataManager.MANAGER.getByItem(stack);
        if (data != null){
            cir.setReturnValue(data.getItemcap(stack));
            return;
        }
        cir.setReturnValue(CapabilityItem.EMPTY);
    }

    @Inject(method = "getItemStackCapabilityOr", at = @At(value = "TAIL"), cancellable = true)
    private static void getFromConfigOr(ItemStack stack, CapabilityItem defaultCap, CallbackInfoReturnable<CapabilityItem> cir) {
        WeaponData data = WeaponDataManager.MANAGER.getByItem(stack);
        if (data != null){
           cir.setReturnValue(data.getItemcap(stack));
           return;
        }
       cir.setReturnValue(defaultCap);
      }
}
