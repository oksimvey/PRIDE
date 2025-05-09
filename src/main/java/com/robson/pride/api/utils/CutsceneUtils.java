package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import team.creative.cmdcam.common.math.point.CamPoint;
import team.creative.cmdcam.common.scene.CamScene;
import team.creative.cmdcam.common.scene.mode.CamMode;
import team.creative.cmdcam.common.scene.mode.OutsideMode;
import team.creative.cmdcam.common.scene.run.CamRun;

import java.util.ArrayList;
import java.util.List;

public class CutsceneUtils {

    public static void startCutscene(LocalPlayer ent, byte duration, CamMode camMode, List<CamPoint> points){
        if (ent != null){
            if (!(Minecraft.getInstance().options.getCameraType().isFirstPerson())) {
                CameraUtils.putonFirstPerson(ent, duration);
            }
            CamScene camScene = CamScene.createDefault();
            camScene.points = points;
            camScene.duration = duration;
            camScene.play();
            camScene.setServerSynced();
        }
    }

    public static CamPoint createPoint(Vec3 pos, float yaw, float pitch) {
        return new CamPoint(pos.x, pos.y, pos.z, yaw, pitch, 0 ,0);
    }

    public static void executionCutscene(LocalPlayer ent, Entity target) {
        List<CamPoint> points = new ArrayList<>();
        double offset = target != null ? target.getBbHeight() : 1;
        Vec3 pos1 = MathUtils.rotate2DVector(ent.getLookAngle(), -90);
        points.add(createPoint(new Vec3(pos1.scale(offset * 1.5).x + ent.getX(), pos1.y + 0.25 + ent.getY(), pos1.scale(offset * 1.5f).z + ent.getZ()), -45f, -25f));
        points.add(createPoint(new Vec3(pos1.scale(offset * 2).x + ent.getX(), pos1.y + 0.25 + ent.getY(), pos1.scale(offset).z + ent.getZ()), -70f, -25f));
        startCutscene(ent, (byte) 1, new OutsideMode(CamScene.createDefault()), points);
    }
}
