package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.SpellUtils;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.command.SpellArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class CastSpellCommand implements Command<CommandSourceStack> {
    private static final CastSpellCommand COMMAND = new CastSpellCommand();

    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("cast")
                .then(Commands.argument("spell", SpellArgument.spellArgument())
                        .then(Commands.argument("power", IntegerArgumentType.integer())
                                .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String spellId = StringArgumentType.getString(context, "spell");
        int power = IntegerArgumentType.getInteger(context, "power");
        SpellUtils.castSpell((LivingEntity) ent, SpellRegistry.getSpell(IronsSpellbooks.MODID + ":" + spellId), power);
        return 1;
    }
}
