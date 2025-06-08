package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.ai.dialogues.JsonInteractionsReader;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

public class DialogueCommand implements Command<CommandSourceStack> {

    private static final DialogueCommand COMMAND = new DialogueCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("dialogue")
                .then(Commands.argument("delay", IntegerArgumentType.integer())
                        .then(Commands.argument("dialogue", StringArgumentType.string())
                                .then(Commands.argument("duration", IntegerArgumentType.integer())
                                        .then(Commands.argument("sound", StringArgumentType.string())
                                                .then(Commands.argument("volume", FloatArgumentType.floatArg())
                                                        .executes(COMMAND))))));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        int delay = IntegerArgumentType.getInteger(context, "delay");
        String dialogue = StringArgumentType.getString(context, "dialogue");
        int duration = IntegerArgumentType.getInteger(context, "duration");
        String sound = StringArgumentType.getString(context, "sound");
        float volume = FloatArgumentType.getFloat(context, "volume");
        TimerUtil.schedule(() -> {
            ListTag dialoguetags = new ListTag();
            CompoundTag dialogue1 = new CompoundTag();
            dialogue1.putString("subtitle", dialogue);
            dialogue1.putInt("duration", duration);
            dialogue1.putString("sound", sound);
            dialogue1.putDouble("volume", volume);
            dialoguetags.add(dialogue1);
            JsonInteractionsReader.deserializeDialogues(ent, TargetUtil.getTarget(ent), dialoguetags, (byte) 0);
        }, delay, TimeUnit.MILLISECONDS);
        return 1;
    }
}

