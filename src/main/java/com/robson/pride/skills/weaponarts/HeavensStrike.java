package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.*;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.entity.spells.sunbeam.SunbeamEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;
import reascer.wom.gameasset.WOMAnimations;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class HeavensStrike extends WeaponSkillBase {
    public HeavensStrike() {
        super("Legendary", "Light", 1, 1, 3000);
    }

    @Override
    public void twohandExecute(LivingEntity ent) {
        common(ent);
    }

    @Override
    public void onehandExecute(LivingEntity ent) {
        common(ent);
    }

    public void common(LivingEntity ent){
        if (ent != null){
            List<Vec3> spiralpoints = MathUtils.getVectorsForHorizontalSpiral(ent.getLookAngle().scale(2), (byte) 6, 50, 1);
            AnimUtils.playAnim(ent, WOMAnimations.AGONY_RISING_EAGLE, 0f);
            if (!spiralpoints.isEmpty()){
                summonSpiralPoint(ent.position(), spiralpoints, 1, ent.getBbHeight());
            }
            TimerUtil.schedule(()-> {
               hit(ent);
            }, 1000, TimeUnit.MILLISECONDS);
        }
    }

    public void hit(Entity ent){
        AnimUtils.playAnim(ent, WOMAnimations.AGONY_SKY_DIVE, 0);
        for (int i = 0; i < 50; i++){
            Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.WISP_PARTICLE.get(),
                    ent.getX() + new Random().nextFloat(0.5f) - 0.5f,
                    ent.getY() + 40 - i,
                    ent.getZ()+ new Random().nextFloat(0.5f) - 0.5f, 0, 0,0 ).scale(5);
        }
        TimerUtil.schedule(()-> {
            if (TargetUtil.getTarget(ent) != null){
                ent.setPos(TargetUtil.getTarget(ent).position());
                HealthUtils.dealBlockableDmg(ent, TargetUtil.getTarget(ent), 7);
            }
        }, 500, TimeUnit.MILLISECONDS);
    }

    public void summonSpiralPoint(Vec3 entpos, List<Vec3> points, int currentloop, float height){
        if (entpos != null && points != null && points.size() >= currentloop){
            Vec3 point = points.get(currentloop);
            Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.WISP_PARTICLE.get(), entpos.x + point.x, entpos.y + (currentloop / (10f / height)),
                    entpos.z + point.z, 0, 0, 0).scale(2);
            TimerUtil.schedule(()-> summonSpiralPoint(entpos, points, currentloop + 1, height), 10, TimeUnit.MILLISECONDS);
        }
    }
}