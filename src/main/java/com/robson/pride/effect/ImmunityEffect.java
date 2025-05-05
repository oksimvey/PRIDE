package com.robson.pride.effect;

import com.robson.pride.api.utils.*;
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

    private byte tickcounter = 0;

    public ImmunityEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void prideClientTick(LivingEntity ent){
    }

    public static void onDamage(LivingAttackEvent event, Entity ent){
        if (event != null && ent != null){
            event.setCanceled(true);
            Joint joint = ArmatureUtils.getNearestJoint(Minecraft.getInstance().player, ent, event.getSource().getSourcePosition().add(0, event.getSource().getEntity().getBbHeight() / 2, 0));
            if (joint != null) {
                float max = 0.25f;
                float min = -0.25f;
                Random random = new Random();
                List<Vec3> positions = new ArrayList<>();
                for (int i = 0; i < ent.getBbHeight() * 10; i++){
                    Vec3 pos = ArmatureUtils.getJointWithTranslation(Minecraft.getInstance().player, ent, new Vec3f(random.nextFloat(max - min) + min,
                             random.nextFloat(max - min) + min,
                            random.nextFloat(max - min) + min), joint);
                    if (pos != null){
                        positions.add(pos);
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

    @Override
    public void prideServerTick(Player player){
        tickcounter++;
        if (tickcounter >= 5){
            tickcounter = 0;
            if (ManaUtils.getMana(player) >= 1){
                ManaUtils.consumeMana(player, 1);
            }
            else player.removeEffect(this);
        }
    }
}
