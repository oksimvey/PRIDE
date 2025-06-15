package com.robson.pride.mixins;

import com.robson.pride.api.maps.WeaponsMap;
import com.robson.pride.item.weapons.CustomWeaponItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.provider.ItemCapabilityProvider;

@Mixin(ItemCapabilityProvider.class)
public class ItemCapabilityProviderMixin {

    @Shadow private CapabilityItem capability;

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/world/item/ItemStack;)V")
    private void inject(ItemStack itemstack, CallbackInfo ci) {
       if (itemstack != null && itemstack.getItem() instanceof CustomWeaponItem){
           this.capability = WeaponsMap.WEAPONS.get(itemstack.getOrCreateTag().getString("weaponid")).getItemcap();
       }
    }
}
