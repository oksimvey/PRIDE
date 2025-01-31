package com.robson.pride.api.ai.actions.actions;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class MoveToPosAction extends ActionBase {

    @Override
    public void onActionStart(Entity ent, CompoundTag action) {
        if (ent != null && action != null) {
            if (ent instanceof PrideMobBase mob) {
                if (action.contains("x") && action.contains("y") && action.contains("z")) {
                    if (action.contains("speed")){
                        mob.setTravellingSpeed(action.getFloat("speed"));
                    }
                    if (action.contains("move_radius")){
                        mob.setMoveRadius(action.getFloat("move_radius"));
                    }
                    if (action.contains("relative")) {
                        if (action.getBoolean("relative")) {
                            mob.setTargetpos(new Vec3(action.getInt("x") + mob.getX(), action.getInt("y") + mob.getY(), action.getInt("z") + mob.getZ()));
                       }
                    }
                    mob.setTargetpos(new Vec3(action.getInt("x"), action.getInt("y"), action.getInt("z")));
                }
            }
        }
    }

    public static void moveToNearestNoRainingPlace(Mob ent){
        if (ent != null){
            Level level = ent.level();
            BlockPos currentPos = ent.blockPosition();
            int maxRadius = 50;
            for (int r = 0; r <= maxRadius; r++) {
                for (int x = -r; x <= r; x++) {
                    for (int z = -r; z <= r; z++) {
                        if (Math.abs(x) != r && Math.abs(z) != r) continue;
                        int checkX = currentPos.getX() + x;
                        int checkZ = currentPos.getZ() + z;
                        int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, checkX, checkZ);
                        BlockPos targetPos = new BlockPos(checkX, surfaceY, checkZ);
                        if (!level.isRainingAt(targetPos)) {
                            ent.moveTo(
                                    targetPos.getX() + 0.5,
                                    targetPos.getY(),
                                    targetPos.getZ() + 0.5,
                                    ent.getYRot(),
                                    ent.getXRot()
                            );
                            return;
                        }
                    }
                }
            }
        }
    }
}
