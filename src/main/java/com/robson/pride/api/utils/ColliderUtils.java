package com.robson.pride.api.utils;

import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.utils.math.Vec3f;

import java.util.Random;

public class ColliderUtils {

    public static Vec3f getAABBForImbuement(ItemStack item){
        if (item != null){
            if (TagCheckUtils.itemsTagCheck(item, "tracking_aabb/longsword")){
                return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.8F, ((new Random()).nextFloat() - 2.5F) * 0.2F);
            }
        }
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.8F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
