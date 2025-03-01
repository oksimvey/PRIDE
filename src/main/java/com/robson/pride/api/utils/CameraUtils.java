package com.robson.pride.api.utils;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.concurrent.TimeUnit;

public class CameraUtils{

    public static void unlockCamera(LocalPlayer ent, byte duration) {
        LocalPlayerPatch player = EpicFightCapabilities.getEntityPatch(ent, LocalPlayerPatch.class);
        if (player != null) {
            player.setLockOn(false);
            TimerUtil.schedule(() -> player.setLockOn(true), duration + 1, TimeUnit.SECONDS);
        }
    }

    public static void lockCamera(LocalPlayer ent) {
        LocalPlayerPatch player = EpicFightCapabilities.getEntityPatch(ent, LocalPlayerPatch.class);
        if (player != null) {
            player.setLockOn(true);
        }
    }

    public static void putonFirstPerson(Entity ent, byte duration) {
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        TimerUtil.schedule(() -> Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK), duration + 1, TimeUnit.SECONDS);
    }
}
