package com.robson.pride.skills.special;

import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.api.utils.MathUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Objects;

public class KillerAuraSkill {

    private static int tickcount;

    public static void auraDamage(Entity ent, String element, float radius, int power) {
        AABB minMax = new AABB(ent.getX() - radius, ent.getY() - (radius * 0.5), ent.getZ() - radius, ent.getX() + radius, ent.getY() + (radius * 1.5), ent.getZ() + radius);
        if (ent.level() != null) {
            List<Entity> listent = ent.level().getEntities(ent, minMax);
            for (Entity entko : listent) {
                tickcount++;
                if (tickcount > 25) {
                    tickcount = 0;
                    if (Objects.equals(element, "Darkness")) {
                        ElementalPassives.darknessPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Light")) {
                        ElementalPassives.lightPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Thunder")) {
                        ElementalPassives.thunderPassive(entko, ent, power, MathUtils.getRandomInt(999999999));
                    }
                    if (Objects.equals(element, "Sun")) {
                        ElementalPassives.sunPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Moon")) {
                        ElementalPassives.moonPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Blood")) {
                        ElementalPassives.bloodPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Wind")) {
                        ElementalPassives.windPassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Nature")) {
                        ElementalPassives.naturePassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Ice")) {
                        ElementalPassives.icePassive(entko, ent, power);
                    }
                    if (Objects.equals(element, "Water")) {
                        ElementalPassives.waterPassive(entko, ent, power);
                    }
                }
            }
        }
    }

}
