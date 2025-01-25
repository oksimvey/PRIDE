package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.registries.WeaponSkillRegister;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.registries.WeaponSkillRegister.*;

public class SkillExecuteCommand implements Command<CommandSourceStack> {
    private static final SkillExecuteCommand COMMAND = new SkillExecuteCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("skill")
                .then(Commands.argument("skilltype", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                    List<Map.Entry<String, WeaponSkillBase>> sortedEntries = WeaponSkills.entrySet()
                            .stream()
                            .sorted(Comparator
                                    .comparing((Map.Entry<String, WeaponSkillBase> entry) -> elements.indexOf(entry.getValue().getSkillElement()))
                                    .thenComparing(entry -> rarities.indexOf(entry.getValue().getSkillRarity())))
                            .toList();
                    for (Map.Entry<String, WeaponSkillBase> entry : sortedEntries) {
                        suggestionsBuilder.suggest(entry.getKey().replace(" ", "_").replace("'", "-"));;
                    }
                            return suggestionsBuilder.buildFuture();
                        }))
                                .executes(COMMAND));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String skill = StringArgumentType.getString(context, "skilltype");
        if (ent instanceof LivingEntity liv) {
            WeaponSkillBase ski = WeaponSkillRegister.WeaponSkills.get(skill.replace("_", " ").replace("-", "'"));
            if (ski != null){
               ski.tryToExecute(liv);
            }
        }
        return 1;
    }
}
