package com.robson.pride.effect;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ImmunityEffect extends PrideEffectBase{

    public ImmunityEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void prideClientTick(LivingEntity ent){
    }

    public void onEffectStart(LivingEntity ent){

    }

    public void onEffectEnd(LivingEntity ent){

    }

    public static void onDamage(LivingAttackEvent event, Entity ent){
        if (event != null && ent != null){
            if (ent instanceof Player player){
                if (ManaUtils.getMana(player) < 10){
                    player.removeEffect(EffectRegister.IMMUNITY.get());
                    return;
                }
                ManaUtils.consumeMana(player, 10);
            }
            ElementalUtils.playSoundByElement(ElementalUtils.getElement(ent), ent, 0.5f);
            event.setCanceled(true);
            Joint joint = ArmatureUtils.getNearestJoint(Minecraft.getInstance().player, ent, event.getSource().getSourcePosition().add(0, event.getSource().getEntity().getBbHeight() / 2, 0));
            if (joint != null) {
                float max = 0.25f;
                float min = -0.25f;
                Random random = new Random();
                List<Vec3> positions = new ArrayList<>();
                Vec3 jointpos = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, joint);
                if (jointpos != null) {
                    for (int i = 0; i < ent.getBbHeight() * 10; i++) {
                            positions.add(jointpos.add(random.nextFloat(max - min) + min,
                                    random.nextFloat(max - min) + min,
                                    random.nextFloat(max - min) + min));
                    }
                }
                if (!positions.isEmpty()){
                    for (Vec3 pos : positions){
                        ParticleOptions particle = ElementalUtils.getParticleByElement(ElementalUtils.getElement(ent));
                        if (particle != null) {
                            loopParticleOnEnt(ent, Minecraft.getInstance().particleEngine.createParticle(particle, pos.x, pos.y, pos.z, ent.getDeltaMovement().x, ent.getDeltaMovement().y ,ent.getDeltaMovement().z), ent.position().subtract(pos));
                        }
                    }
                }
            }
        }
    }

    public static void loopParticleOnEnt(Entity ent, Particle particle, Vec3 posdifference){
        if (ent != null && particle != null){
            particle.setLifetime(5);
            Vec3 entdelta = ent.getDeltaMovement();
            Vec3 newpos = ent.position().subtract(posdifference);
            particle.setParticleSpeed(entdelta.x, entdelta.y, entdelta.z);
            particle.setPos(newpos.x, newpos.y, newpos.z);
           TimerUtil.schedule(()-> loopParticleOnEnt(ent, particle, posdifference), 50, TimeUnit.MILLISECONDS);
        }
    }
}
