package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class PerilousCommand implements Command<CommandSourceStack> {
    private static final PerilousCommand COMMAND = new PerilousCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("perilous")
                .then(Commands.argument("periloustype", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            for (String periloustypes : PerilousAttack.periloustypes) {
                                suggestionsBuilder.suggest(periloustypes);
                            }
                            return suggestionsBuilder.buildFuture();
                        }))
                        .executes(COMMAND));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String periloustype = StringArgumentType.getString(context, "periloustype");
        StaticAnimation currentmotion = AnimUtils.getCurrentAnimation(ent);
        if (currentmotion != null) {
            AnimUtils.addPerilousToAnim(ent, currentmotion, periloustype);
        }
        return 1;
    }
}