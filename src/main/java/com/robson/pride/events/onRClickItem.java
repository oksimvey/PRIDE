package com.robson.pride.events;

import com.robson.pride.api.utils.ItemColiderUtil;
import com.robson.pride.mechanics.Parry;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onRClickItem {
    @SubscribeEvent
    public static void onRClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() != null) {
            Player ent = event.getEntity();
            Parry.ParryWindow(ent);
            ItemColiderUtil.getWeaponColider(ent);
        }
    }
}