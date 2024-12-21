package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
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
                .then(Commands.argument("set", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("Darkness");
                            suggestionsBuilder.suggest("Light");
                            suggestionsBuilder.suggest("Thunder");
                            suggestionsBuilder.suggest("Sun");
                            suggestionsBuilder.suggest("Moon");
                            suggestionsBuilder.suggest("Blood");
                            suggestionsBuilder.suggest("Wind");
                            suggestionsBuilder.suggest("Nature");
                            suggestionsBuilder.suggest("Ice");
                            suggestionsBuilder.suggest("Water");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .executes(COMMAND));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String element = StringArgumentType.getString(context, "set");
        ElementalUtils.setElement(ent, element);
        return 1;
    }
}

