package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.concurrent.TimeUnit;

public class CutsceneUtils {

    public static void cutsceneStart(Entity ent, byte duration, String mode) {
        if (ent != null) {
            CommandUtils.executeonClient(ent, "cam clear");
            CommandUtils.executeonClient(ent, "cam mode " + mode);
                LocalPlayerPatch player = EpicFightCapabilities.getEntityPatch(Minecraft.getInstance().player, LocalPlayerPatch.class);
                if (player != null) {
                    CameraUtils.unlockCamera(player, duration);
            }
            if (!(Minecraft.getInstance().options.getCameraType().isFirstPerson())) {
                CameraUtils.putonFirstPerson(ent, duration);
            }
            TimerUtil.schedule(()-> CommandUtils.executeonClient(ent, "cam start " + duration + "s"), 10, TimeUnit.MILLISECONDS);
        }
    }

    public static void addPoint(Entity ent, String pointpos){
        CommandUtils.executeonClient(ent, "cam add " + pointpos);
    }

    public static void executionCutscene(Entity ent){
        cutsceneStart(ent, (byte) 1, "outside");
        TimerUtil.schedule(()->   addPoint(ent, "^-3 ^0.25 ^-2.5 ~-45 ~-25"), 1, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->   addPoint(ent, "^-3.5 ^1 ^ ~-70 ~-25"), 2, TimeUnit.MILLISECONDS);
    }
}
