package com.robson.pride.effect;

import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.ParticleUtils;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

public class DarknessWrathEffect extends PrideEffectBase {

    public DarknessWrathEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void prideClientTick(LivingEntity ent){
        if (ent != null){
            float heightfactor = ent.getBbHeight() / 1.8f;
            Vec3 lEye = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(-0.11f * heightfactor, 0.175f * heightfactor, -0.3f * heightfactor), Armatures.BIPED.head);
            Vec3 rEye = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent,  new Vec3f(0.11f * heightfactor, 0.175f * heightfactor,  -0.3f * heightfactor), Armatures.BIPED.head);
            if (lEye != null && rEye != null) {
                Particle particle = ParticleUtils.spawnAuraParticle(ParticleRegistry.EMBER_PARTICLE.get(), lEye.x, lEye.y, lEye.z, ent.getDeltaMovement().x * 1.2, 0, ent.getDeltaMovement().z * 1.2);
                particle.scale(0.4f);
                particle.setColor(50, 0, 0);
                Particle particle1 = ParticleUtils.spawnAuraParticle(ParticleRegistry.EMBER_PARTICLE.get(), rEye.x, rEye.y, rEye.z, ent.getDeltaMovement().x * 1.2, 0, ent.getDeltaMovement().z * 1.2);
                particle1.scale(0.4f);
                particle1.setColor(50, 0, 0);
            }
        }
    }

    @Override
    public void prideServerTick(Player player){

    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
