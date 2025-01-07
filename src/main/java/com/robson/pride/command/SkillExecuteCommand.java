package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.skillcore.SkillCore;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class SkillExecuteCommand implements Command<CommandSourceStack> {
    private static final SkillExecuteCommand COMMAND = new SkillExecuteCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("skill")
                .then(Commands.argument("skilltype", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("Flame Slash");
                            suggestionsBuilder.suggest("Darkness_Cut");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .then(Commands.argument("power", IntegerArgumentType.integer())
                                .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String skill =  StringArgumentType.getString(context, "skilltype");
        int power = IntegerArgumentType.getInteger(context, "power");
        if (ent instanceof LivingEntity liv){
            SkillCore.weaponArtCore(liv, skill.replace("_", " "));
        }
        return 1;
    }
}
