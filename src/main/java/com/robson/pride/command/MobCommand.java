package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class MobCommand implements Command<CommandSourceStack> {

    private static final MobCommand COMMAND = new MobCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("mob")
                .then(Commands.argument("mob_action", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {

                            suggestionsBuilder.suggest("deserialize_path");

                            return suggestionsBuilder.buildFuture();
                        }))
                        .executes(COMMAND));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String mob_action = StringArgumentType.getString(context, "mob_action");
        switch (mob_action) {
            case "deserialize_path" -> {
                if (ent instanceof PrideMobBase prideMobBase) prideMobBase.deserializePassiveSkills();
            }
        }
        return 1;
    }
}
