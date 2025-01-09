package com.robson.pride.skills.weaponarts;

import com.robson.pride.api.mechanics.ElementalPassives;
import com.robson.pride.api.mechanics.PerilousAttack;
import com.robson.pride.api.skillcore.SkillBases;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.*;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class DarknessCut extends WeaponSkillBase {

    public DarknessCut() {
        super( "Mythical", 50, 6);
    }

    @Override
    public void twohandExecute(Entity ent) {
                DarknessSlash((LivingEntity) ent);
    }

    public static void DarknessSlash(LivingEntity ent){
        if (ent != null){
            StaticAnimation animation = Animations.TACHI_AUTO3;
               AnimUtils.playAnim(ent, animation, 0);
            PerilousAttack.setPerilous(ent, "total", 1500);
            TimerUtil.schedule(()->{
            int id = MathUtils.getRandomInt(999999999);
            String skill = "darkness_slash";
            Vec3 lookangle = ent.getLookAngle();
            ent.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 15, 255));
            PlaySoundUtils.playSound(ent, SoundRegistry.TELEKINESIS_CAST.get(), 1, 1);
            if (!ent.level().isClientSide()){
            List<Entity> list = ((ServerLevel)ent.level()).getEntities(ent, new AABB(ent.getX() - 25, ent.getY() - 10, ent.getZ() - 25, ent.getX() + 25, ent.getY() + 20, ent.getZ() + 25));
                for (int i = -20; i < 20; i++) {
                    Vec3 vec = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(0, (float) i / 2, 0), Armatures.BIPED.chest);
                    double particleposx = vec.x;
                    double particleposy = vec.y;
                    double particleposz = vec.z;
                    Particle particle = Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, particleposx, particleposy, particleposz, lookangle.x, -0.1, lookangle.z);
                    for (Entity entko : list) {
                        if (particle != null && entko != null) {
                            particle.setLifetime(200);
                            particle.scale(1.25f);
                            checkHit(ent, entko, particle, skill, id);
                        }
                    }
                }
                }
            }, 300, TimeUnit.MILLISECONDS);
        }
    }

    public static void checkHit(Entity dmgent, Entity entko, Particle particle, String skill, int id){
        if (particle != null && entko != null && dmgent != null) {
            if (SkillBases.canHit(dmgent, entko, skill, id)) {
                double distance = MathUtils.getTotalDistance(entko.getX() - particle.getPos().x, entko.getY() - particle.getPos().y, entko.getZ() - particle.getPos().z);
                if (distance < 0.5) {
                    entko.getPersistentData().putInt(skill, id);
                    ElementalPassives.darknessPassive(entko, dmgent, 10);
                }
                else TimerUtil.schedule(()->loopCheckHit(dmgent, entko, particle, skill, id), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void loopCheckHit(Entity dmgent, Entity entko, Particle particle, String skill, int id){
        if (dmgent  != null && entko != null && particle != null){
            checkHit(dmgent, entko, particle, skill, id);
        }
    }

}
