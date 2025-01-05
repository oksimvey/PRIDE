package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.TimeUnit;

public class CutsceneUtils {

    public static void cutsceneStart(Entity ent, byte duration, String mode) {
        if (ent != null) {
            CommandUtils.executeonClient(ent, "cam clear");
            CommandUtils.executeonClient(ent, "cam mode " + mode);
             CameraUtils.unlockCamera(Minecraft.getInstance().player, duration);
            if (!(Minecraft.getInstance().options.getCameraType().isFirstPerson())) {
                CameraUtils.putonFirstPerson(ent, duration);
            }
            TimerUtil.schedule(()-> CommandUtils.executeonClient(ent, "cam start " + duration + "s"), 10, TimeUnit.MILLISECONDS);
        }
    }

    public static void addPoint(Entity ent, String pointpos){
        CommandUtils.executeonClient(ent, "cam add " + pointpos);
    }

    public static void executionCutscene(Entity ent, Entity target){
        cutsceneStart(ent, (byte) 1, "outside");
        double offset = -target.getBbHeight();
        TimerUtil.schedule(()->   addPoint(ent, "^" + offset * 1.5 + " ^0.25 ^" + offset * 1.5 + " ~-45 ~-25"), 1, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->   addPoint(ent, "^" + offset * 2 + " ^0.5 ^" + offset + " ~-70 ~-25"), 2, TimeUnit.MILLISECONDS);
    }
}
