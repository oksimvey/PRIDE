package com.robson.pride.api.utils;

import com.github.leawind.thirdperson.ThirdPerson;
import com.github.leawind.thirdperson.config.AbstractConfig;
import com.github.leawind.thirdperson.config.Config;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.concurrent.TimeUnit;

public interface CameraUtils {

    static void unlockCamera(LocalPlayer ent, byte duration) {
        LocalPlayerPatch player = EpicFightCapabilities.getEntityPatch(ent, LocalPlayerPatch.class);
        if (player != null) {
            player.setLockOn(false);
            TimerUtil.schedule(() -> player.setLockOn(true), duration + 1, TimeUnit.SECONDS);
        }
    }

    static void changeRotateMode(AbstractConfig.PlayerRotateMode mode){
        Config config = ThirdPerson.getConfig();
        changeRotateMode(config, mode);
    }


    static void changeRotateMode(Config config, AbstractConfig.PlayerRotateMode mode){
        if (config.normal_rotate_mode != mode) {
            config.normal_rotate_mode = mode;
        }
    }

    static void correctCamera() {
        Config config = ThirdPerson.getConfig();
        changeRotateMode(config, AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA);
            TimerUtil.schedule(() -> {
                changeRotateMode(config, AbstractConfig.PlayerRotateMode.INTEREST_POINT);
            }, 500, TimeUnit.MILLISECONDS);
        }

    static void lockCamera(LocalPlayer ent) {
        LocalPlayerPatch player = EpicFightCapabilities.getEntityPatch(ent, LocalPlayerPatch.class);
        if (player != null) {
            player.setLockOn(true);
        }
    }

    static void putonFirstPerson(Entity ent, byte duration) {
        Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
        TimerUtil.schedule(() -> Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK), duration, TimeUnit.SECONDS);
    }

    static void changeCamOffset(float x, float y, float z) {
        Config config = ThirdPerson.getConfig();
        config.normal_max_distance = z;
        config.normal_offset_y = y;
        config.normal_offset_x = x;
    }
}
