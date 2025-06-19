package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.utils.math.Vec3f;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HeavensStrike extends WeaponSkillBase {
    public HeavensStrike() {
        super("Legendary", "Light", 1, 1, "total");
    }

    public List<SkillAnimation> defineMotions(LivingEntity ent) {
        return List.of(new SkillAnimation(Animations.AXE_AUTO1, () -> {
            List<Vec3f> spiralpoints = MathUtils.getVectorsForHorizontalSpiral(Vec3f.fromVec3(ent.getLookAngle().scale(2)), (byte) 6, 50, 1);
            if (!spiralpoints.isEmpty()) {
                summonSpiralPoint(ent.position(), spiralpoints, 1, ent.getBbHeight());
            }
        }), new SkillAnimation(Animations.AXE_AUTO1, () -> {
            for (int i = 0; i < 50; i++) {
                Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.WISP_PARTICLE.get(),
                        ent.getX() + new Random().nextFloat(0.5f) - 0.5f,
                        ent.getY() + 40 - i,
                        ent.getZ() + new Random().nextFloat(0.5f) - 0.5f, 0, 0, 0).scale(5);
            }
            TimerUtil.schedule(() -> {
                if (TargetUtil.getTarget(ent) != null) {
                    ent.setPos(TargetUtil.getTarget(ent).position());
                    HealthUtils.dealBlockableDmg(ent, TargetUtil.getTarget(ent), 7);
                }
            }, 500, TimeUnit.MILLISECONDS);
        }));
    }


    public void summonSpiralPoint(Vec3 entpos, List<Vec3f> points, int currentloop, float height) {
        if (entpos != null && points != null && points.size() >= currentloop) {
            Vec3f point = points.get(currentloop);
            Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.WISP_PARTICLE.get(), entpos.x + point.x, entpos.y + (currentloop / (10f / height)),
                    entpos.z + point.z, 0, 0, 0).scale(2);
            TimerUtil.schedule(() -> summonSpiralPoint(entpos, points, currentloop + 1, height), 10, TimeUnit.MILLISECONDS);
        }
    }
}