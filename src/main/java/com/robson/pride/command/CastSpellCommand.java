package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.SpellUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.entities.special.Shooter;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.command.SpellArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

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
        Shooter shooter = Shooter.summonShooter(ent, TargetUtil.getTarget(ent), true);
        TimerUtil.schedule(() -> SpellUtils.castSpellFromShooter(shooter, SpellRegistry.getSpell(IronsSpellbooks.MODID + ":" + spellId), power), 75, TimeUnit.MILLISECONDS);
        return 1;
    }
}
