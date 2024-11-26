package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.mechanics.PerilousAttack;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class PerilousCommand implements Command<CommandSourceStack> {
    private static final PerilousCommand COMMAND = new PerilousCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("perilous")
                .then(Commands.argument("periloustype", StringArgumentType.string())
                        .then(Commands.argument("window", IntegerArgumentType.integer())
                                        .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String periloustype =  StringArgumentType.getString(context, "periloustype");
        int window = IntegerArgumentType.getInteger(context, "window");
        PerilousAttack.setPerilous(ent, periloustype, window);
        return 1;
    }
}