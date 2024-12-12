package com.robson.pride.api.utils;

import com.github.exopandora.shouldersurfing.api.callback.ITargetCameraOffsetCallback;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;

import java.util.concurrent.TimeUnit;

public class CameraUtils implements ITargetCameraOffsetCallback {


    public static void unlockCamera(LocalPlayerPatch ent, byte duration){
        ent.setLockOn(false);
        TimerUtil.schedule(()-> ent.setLockOn(true), duration+1, TimeUnit.SECONDS);
    }

    public static void putonFirstPerson(Entity ent, byte duration){
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        TimerUtil.schedule(()->   Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK), duration+1, TimeUnit.SECONDS);
    }
}
