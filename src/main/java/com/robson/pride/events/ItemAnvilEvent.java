package com.robson.pride.events;

import com.google.common.eventbus.Subscribe;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemAnvilEvent {

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event){
        if (event !=  null){
            if (event.getRight().getItem() == ItemsRegister.FLAME_SLASH_WEAPON_ART.get()){
                ItemStack output = event.getLeft().copy();

                output.getOrCreateTag().putString("WeaponArt", "flame_slash");
                output.getOrCreateTag().putBoolean("hasWeaponArt", true);

                event.setOutput(output);

                event.setCost(5);

                event.setMaterialCost(1);
            }
        }
    }
}
