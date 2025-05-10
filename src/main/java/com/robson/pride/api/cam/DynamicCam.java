package com.robson.pride.api.cam;

import com.github.leawind.thirdperson.ThirdPerson;
import com.robson.pride.api.customtick.CustomTickManager;
import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.TargetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ConcurrentHashMap;

public class DynamicCam {

    public static void dynamicCamTick(Player player){
        if (player != null){
            battleOffset(player);
        }
    }

    public static void battleOffset(Player player) {
        if (player != null) {
            if (CustomTickManager.targeting_entities.get(player) != null && !(CustomTickManager.targeting_entities.get(player).isEmpty())){
                for (Entity ent : CustomTickManager.targeting_entities.get(player)) {
                    if (ent != null && ent.getBbHeight() / 100 > ThirdPerson.getConfig().normal_offset_y) {
                        CameraUtils.changeCamOffset(0, 0 + ent.getBbHeight() / 100, getDefaultZModifier(player) + ent.getBbHeight() / 5);
                    }
                }
            }
            else CameraUtils.changeCamOffset(-0.08f, -0.02f, getDefaultZModifier(player));
     }
    }

     public static float getDefaultZModifier(Player player){
        if (player != null){
            return (float) ((1 + ItemStackUtils.getColliderSize(player.getMainHandItem()) / 2) + ((player.getDeltaMovement().x + player.getDeltaMovement().z) * 2));
        }
        return 2;
     }
}
