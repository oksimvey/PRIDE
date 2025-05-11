package com.robson.pride.api.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import team.creative.cmdcam.client.CMDCamClient;
import team.creative.cmdcam.common.math.point.CamPoint;
import team.creative.cmdcam.common.scene.CamScene;
import team.creative.cmdcam.common.scene.mode.CamMode;
import team.creative.cmdcam.common.scene.mode.OutsideMode;

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
            camScene.mode = camMode;
            CMDCamClient.start(camScene);
        }
    }

    public static CamPoint createPoint(Vec3 pos, float yaw, float pitch) {
        return new CamPoint(pos.x, pos.y, pos.z, yaw, pitch, 0 ,0);
    }

    public static void executionCutscene(LocalPlayer ent, Entity target) {
        if (ent != null) {
            List<CamPoint> points = new ArrayList<>();
            double offset = target != null ? target.getBbHeight() : 1;
            Vec3 pos1 = MathUtils.rotate2DVector(ent.getLookAngle().scale(10), -45);
           points.add(createPoint(new Vec3(pos1.scale(offset * 1.5).x + ent.getX(),0.25 + ent.getY(), pos1.scale(offset * 1.5f).z + ent.getZ()),  ent.getViewYRot(1) + 160, 0));
            startCutscene(ent, (byte) 1, new OutsideMode(CamScene.createDefault()), points);
        }
    }
}
