package com.robson.pride.api.utils;

import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

public class ParticleUtils {

    public static void bloodGuardBreak(Entity ent){
        CommandUtils.executeonEntity(ent, "particle epicfight:blood ^ ^0.75 ^");
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.15 ^1 ^0.5"),33, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.3 ^1.25 ^1"),66, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.45 ^1.5 ^1.5"),99, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.6 ^1.75 ^2"),133, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.75 ^2 ^2.5"),166, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> CommandUtils.executeonEntity(ent, "particle epicfight:blood ^0.9 ^2.25 ^3"),199, TimeUnit.MILLISECONDS);
    }

    public static void bloodStealth(Entity ent){

    }
}
