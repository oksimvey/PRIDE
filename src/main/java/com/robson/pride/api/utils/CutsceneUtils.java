package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class CutsceneUtils {

    public static void cutsceneStart(Player ent, byte duration, String mode) {
        if (ent != null) {
            LocalPlayer player1 = (LocalPlayer) ent;
            player1.connection.sendCommand("say cam clear");
            player1.connection.sendCommand("cam mode " + mode);
            TimerUtil.schedule(() -> player1.connection.sendCommand("cam start " + duration + "s"), 10, TimeUnit.MILLISECONDS);
            CameraUtils.unlockCamera(Minecraft.getInstance().player, duration);
            if (!(Minecraft.getInstance().options.getCameraType().isFirstPerson())) {
                CameraUtils.putonFirstPerson(ent, duration);
            }
        }
    }

    public static void addPoint(Player ent, String pointpos) {
        LocalPlayer player1 = (LocalPlayer) ent;
        player1.connection.sendCommand("cam add " + pointpos);
    }

    public static void executionCutscene(Player ent, Entity target) {
        cutsceneStart(ent, (byte) 1, "outside");
        double offset = -target.getBbHeight();
        TimerUtil.schedule(() -> addPoint(ent, "^" + offset * 1.5 + " ^0.25 ^" + offset * 1.5 + " ~-45 ~-25"), 1, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(() -> addPoint(ent, "^" + offset * 2 + " ^0.5 ^" + offset + " ~-70 ~-25"), 2, TimeUnit.MILLISECONDS);
    }
}
