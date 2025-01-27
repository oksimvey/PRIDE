package com.robson.pride.effect;

import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ParticleUtils;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

public class DivineProtectionEffect extends MobEffect {

    public DivineProtectionEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    public static void tick(LivingEntity ent){
        if (ent != null){


        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
