package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.mechanics.MikiriCounter;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class MikiriCommand implements Command<CommandSourceStack> {
    private static final MikiriCommand COMMAND = new MikiriCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("mikiri")
                .then(Commands.argument("mikiritype", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("Dodge");
                            suggestionsBuilder.suggest("Jump");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .then(Commands.argument("window", IntegerArgumentType.integer())
                                .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String mikiritype = StringArgumentType.getString(context, "mikiritype");
        int window = IntegerArgumentType.getInteger(context, "window");
        MikiriCounter.setMikiri(ent, mikiritype, 0, window);
        return 1;
    }
}