package com.robson.pride.api.utils;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.client.ClientCommandHandler;

public class CommandUtils {

    public static void executeonEntity(Entity ent, String command) {
        if (ent != null) {
            if (!ent.level().isClientSide() && ent.getServer() != null) {
                ent.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, ent.position(), ent.getRotationVector(), ent.level() instanceof ServerLevel ? (ServerLevel) ent.level() : null, 4,
                        ent.getName().getString(), ent.getDisplayName(), ent.level().getServer(), ent), command);
            }
        }
    }

    public static void executeOnClient(LocalPlayer player, String command) {
        ClientCommandHandler.runCommand(command);
    }
}

