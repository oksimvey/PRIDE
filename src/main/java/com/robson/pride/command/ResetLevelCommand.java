package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.progression.NewCap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class ResetLevelCommand implements Command<CommandSourceStack> {
    private static final ResetLevelCommand COMMAND = new ResetLevelCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("reset")
                .executes(COMMAND);
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        if (ent instanceof Player player) {
            NewCap.startVariables(player, player.getPersistentData());
        }
        return 1;
    }
}