package com.robson.pride.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.utils.ProjectileUtil;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;

import java.util.concurrent.TimeUnit;

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
        if (projectile.equals("arrow")) {
            shoot(ent, EntityType.ARROW.create(ent.level()), speed);
        } else if (projectile.equals("cannon")) {
            shoot(ent, (Projectile) EntityType.byString("smallships:cannon_ball").orElse(null).create(ent.level()), speed);

        }
        return 1;
    }

    public void shoot(Entity ent, Projectile projectile, float speed) {
        if (ent != null && projectile != null) {
            Entity entity = TargetUtil.getTarget(ent);
            if (entity != null && ent.getVehicle() != null) {
                     }
            ProjectileUtil.shootProjectileFromEnt(projectile, ent, speed);
        }
    }
}

