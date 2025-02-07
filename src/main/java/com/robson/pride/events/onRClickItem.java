package com.robson.pride.events;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.mechanics.Parry;
import com.robson.pride.api.utils.ItemStackUtils;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.ClientCommandSourceStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onRClickItem {

    @SubscribeEvent
    public static void onRClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getEntity() != null) {
            Player player = event.getEntity();
            if (ItemStackUtils.checkWeapon(player, InteractionHand.MAIN_HAND)) {
                player.startUsingItem(InteractionHand.MAIN_HAND);
                Parry.ParryWindow(player);
            }
            if (ItemStackUtils.checkShield(player, InteractionHand.MAIN_HAND) || ItemStackUtils.checkShield(player, InteractionHand.OFF_HAND)){
                Parry.ParryWindow(player);
            }
        }
    }

    public static void executeOnClientPlayer(LocalPlayer player, String command) throws CommandSyntaxException {
        if (player != null) {
            CommandDispatcher<CommandSourceStack> commands = new CommandDispatcher<>();
            ClientCommandSourceStack clientCommandSourceStack = new ClientCommandSourceStack(player, player.position(), player.getRotationVector(), player.getPermissionLevel(), player.getName().getString(), player.getDisplayName(), player);
            StringReader reader = new StringReader(command);
            commands.execute(reader, clientCommandSourceStack);
        }
    }
}

