package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.enums.ElementsEnum;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DarknessCut extends WeaponSkillBase {

    public DarknessCut() {
        super("Mythical", "Darkness", 50, 6, "total");
    }

    public List<SkillAnimation> defineMotions(LivingEntity ent) {
        return List.of(new SkillAnimation(Animations.TACHI_AUTO3.get(), () -> {
            TimerUtil.schedule(() -> {
                Vec3 lookangle = ent.getLookAngle();
                ent.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 15, 255));
                PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_CAST.get(), 1, 1);
                if (!ent.level().isClientSide()) {
                    List<Entity> list = ent.level().getEntities(ent, new AABB(ent.getX() - 25, ent.getY() - 10, ent.getZ() - 25, ent.getX() + 25, ent.getY() + 20, ent.getZ() + 25));
                    for (int i = -20; i < 20; i++) {
                        Vec3 vec = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(0, (float) i / 2, 0), Armatures.BIPED.get().chest);
                        double particleposx = vec.x;
                        double particleposy = vec.y;
                        double particleposz = vec.z;
                        Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, particleposx, particleposy, particleposz, lookangle.x, -0.1, lookangle.z);
                        for (Entity entko : list) {
                            if (particle != null && entko != null) {
                                particle.setLifetime(200);
                                particle.scale(1.25f);
                                SkillCore.loopParticleHit(ent, entko, particle, new ArrayList<>(), 0.5f, () -> ElementsEnum.darkness.getElement().onHit(entko, ent, 10, false));
                            }
                        }
                    }
                }
            }, 300, TimeUnit.MILLISECONDS);
        }));
    }
}
