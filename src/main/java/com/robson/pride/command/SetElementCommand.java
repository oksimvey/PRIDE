package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.ElementalUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class SetElementCommand implements Command<CommandSourceStack> {
    private static final SetElementCommand COMMAND = new SetElementCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("elemental")
                .then(Commands.argument("set", IntegerArgumentType.integer()).suggests(((commandContext, suggestionsBuilder) -> {
                           for (byte i = 1; i < 10; i++){
                               suggestionsBuilder.suggest(i);
                           }
                            return suggestionsBuilder.buildFuture();
                        }))
                        .executes(COMMAND));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        byte element = (byte) IntegerArgumentType.getInteger(context, "set");
        ElementalUtils.setElement(ent, element);
        return 1;
    }
}

