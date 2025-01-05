package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.mechanics.Eating;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.concurrent.TimeUnit;

public class MobEatCommand implements Command<CommandSourceStack> {
    private static final MobEatCommand COMMAND = new MobEatCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("mob_eat")
                .then(Commands.argument("type", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("eat");
                            suggestionsBuilder.suggest("equip");
                            return suggestionsBuilder.buildFuture();
                        }))
                .then(Commands.argument("hand", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("mainhand");
                            suggestionsBuilder.suggest("offhand");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .then(Commands.argument("item", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                                    suggestionsBuilder.suggest("target_hand");
                                    return suggestionsBuilder.buildFuture();
                                }))
                                .executes(COMMAND))));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String hand =  StringArgumentType.getString(context, "hand");
        String item = StringArgumentType.getString(context, "item");
        String type = StringArgumentType.getString(context, "type");
        if (ent instanceof LivingEntity living) {
            if (type.equals("equip")) {
                if (item.equals("target_hand")) {
                    if (hand.equals("mainhand")) {
                        living.getPersistentData().putBoolean("canrobmainhand", true);
                        TimerUtil.schedule(()->{
                            if (ent != null){
                                living.getPersistentData().putBoolean("canrobmainhand", false);
                            }
                        }, 1000, TimeUnit.MILLISECONDS);
                    }
                    if (hand.equals("offhand")) {
                        living.getPersistentData().putBoolean("canroboffhand", true);
                        TimerUtil.schedule(()->{
                            if (ent != null){
                                living.getPersistentData().putBoolean("canroboffhand", false);
                            }
                        }, 1000, TimeUnit.MILLISECONDS);
                    }
                }
                else ent.getPersistentData().putString("itemtoequip", item);
                if (hand.equals("mainhand")) {
                    living.getPersistentData().putString("previous_item", String.valueOf(BuiltInRegistries.ITEM.getKey(living.getMainHandItem().getItem())));
                }
                if (hand.equals("offhand")) {
                    living.getPersistentData().putString("previous_item", String.valueOf(BuiltInRegistries.ITEM.getKey(living.getOffhandItem().getItem())));;
                }
            }
            if (type.equals("eat")){
                if (hand.equals("mainhand")){
                    Eating.mobEat(ent, InteractionHand.MAIN_HAND, 10);
                }
                if (hand.equals("offhand")){
                    Eating.mobEat(ent, InteractionHand.OFF_HAND, 5);
                }
            }
        }
        return 1;
    }
}
