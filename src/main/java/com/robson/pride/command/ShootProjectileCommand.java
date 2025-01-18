package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.ProjectileUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;

public class ShootProjectileCommand implements Command<CommandSourceStack> {
    private static final ShootProjectileCommand COMMAND = new ShootProjectileCommand();


    public static ArgumentBuilder<CommandSourceStack, ?> register() {
        return Commands.literal("shoot")
                .then(Commands.argument("projectile", StringArgumentType.word()).suggests(((commandContext, suggestionsBuilder) -> {
                            suggestionsBuilder.suggest("arrow");
                            suggestionsBuilder.suggest("cannon");
                            return suggestionsBuilder.buildFuture();
                        }))
                        .then(Commands.argument("speed", FloatArgumentType.floatArg())
                        .executes(COMMAND)));
    }

    @Override
    public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Entity ent = EntityArgument.getEntity(context, "living_entity");
        String projectile = StringArgumentType.getString(context, "projectile");
        float speed = FloatArgumentType.getFloat(context, "speed");
        Projectile projectile1 = null;
        switch (projectile){
            case "arrow" -> projectile1 = EntityType.ARROW.create(ent.level());
            case "cannon" -> {
                EntityType<?> entitytype = EntityType.byString("smallships:cannon_ball").orElse(null);
                if (entitytype != null){
                    if (entitytype.create(ent.level()) instanceof Projectile pos){
                        projectile1 = pos;
                    }
                }
            }
        }
        if (projectile1 != null) {
            ProjectileUtil.shootProjectileFromEnt(projectile1, ent, speed);
        }
        return 1;
    }
}

