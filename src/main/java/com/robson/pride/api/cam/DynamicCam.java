package com.robson.pride.api.cam;

import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ConcurrentHashMap;

public class DynamicCam {

    public static ConcurrentHashMap<Player, Vec3> camoffsetmap = new ConcurrentHashMap<>();

    public static void dynamicCamTick(Player player){
        if (player != null){
            if (camoffsetmap.get(player) != null){
                CameraUtils.changeCamOffset((float) camoffsetmap.get(player).x, (float) camoffsetmap.get(player).y, (float) camoffsetmap.get(player).z);
                return;
            }
            CameraUtils.changeCamOffset(-0.08f, -0.02f, (float) (2));
        }
    }

    public static void battleOffset(Player player, Entity ent) {
        if (player != null && ent != null && TargetUtil.getTarget(ent) == player) {
            camoffsetmap.put(player, new Vec3(0, ent.getBbHeight() / 50, (ent.getBbHeight() / 3) + 2));
        }
        else if (player != null) camoffsetmap.remove(player);
    }
}
