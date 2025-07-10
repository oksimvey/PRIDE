package com.robson.pride.events;

import com.robson.pride.api.data.types.item.ElementData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.data.types.skill.WeaponSkillData;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.progression.AttributeModifiers;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemAnvilEvent {

    @SubscribeEvent
    public static void anvilEvent(AnvilUpdateEvent event) {
        if (event != null) {
            ItemStack leftitem = event.getLeft();
            ItemStack rightitem = event.getRight();

        }
    }
}
