package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.utils.ProgressionUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class AddXpCommand implements Command<CommandSourceStack> {
    private static final AddXpCommand COMMAND = new AddXpCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("addxp")
                .then(Commands.argument("stat", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("Strength");
                            suggestionsBuilder.suggest("Dexterity");
                            suggestionsBuilder.suggest("Vigor");
                            suggestionsBuilder.suggest("Endurance");
                            suggestionsBuilder.suggest("Mind");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String stat = StringArgumentType.getString(context, "stat");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        byte statid = switch (stat){
            case "Strength" -> ClientSavedData.Strength;

            case "Dexterity" -> ClientSavedData.Dexterity;

            case "Vigor" -> ClientSavedData.Vigor;

            case "Endurance" -> ClientSavedData.Endurance;

            default -> ClientSavedData.Mind;
        };
        if (ent instanceof Player player) {
            ProgressionUtils.addXp(player, statid, amount);
        }
        return 1;
    }
}
