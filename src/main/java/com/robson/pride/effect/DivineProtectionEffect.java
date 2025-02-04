package com.robson.pride.effect;

import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.Random;

import static com.robson.pride.api.utils.ArmatureUtils.*;

public class DivineProtectionEffect extends PrideEffectBase {

    private static final Vec3f WING_OFFSET = new Vec3f(0.0F, 0.0F, 0.15F);
    private static final float WING_LENGTH = 1.2F;
    private static final float WING_CURVE = 0.3F;


    public DivineProtectionEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void pridetick(LivingEntity ent) {
        if (ent != null) {
            int particlesPerWing = 20;
            float animationSpeed = 0.05F;
            float time = System.currentTimeMillis() / 1000f;
            for (int side : new int[]{-1, 1}) {
                for (int i = 0; i < particlesPerWing; i++) {
                    float progress = (float) i / particlesPerWing;
                    Vec3f wingPoint = calculateWingPoint(progress, side, time, animationSpeed);
                    if (ent.getPersistentData().getBoolean("resurrecting")){
                        wingPoint.add(0, 0, -1.5f);
                    }
                    Vec3 pos = getJointWithTranslation(
                            Minecraft.getInstance().player,
                            ent,
                            wingPoint,
                            Armatures.BIPED.torso);
                   if (pos != null) {
                       float yoffset = new Random().nextFloat(0.5f);
                       Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.WISP_PARTICLE.get(), pos.x, pos.y + 0.4f - yoffset, pos.z, ent.getDeltaMovement().x, ent.getDeltaMovement().y, ent.getDeltaMovement().z);
                       if (particle != null) {
                           particle.scale(1.75f);
                           particle.setLifetime(1);
                       }
                   }
                }
            }
        }
    }

        private static Vec3f calculateWingPoint(float progress, int side, float time, float speed) {
            float x = side * (WING_OFFSET.x + progress * WING_LENGTH);
            float y = WING_OFFSET.y + (float) Math.sin(progress * Math.PI) * WING_CURVE;
            float z = WING_OFFSET.z + (float) Math.cos(progress * Math.PI * 2) * 0.1F;
            y += (float) Math.sin(time * speed + side * 2) * 0.05F;
            return new Vec3f(x, y, z);
        }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
