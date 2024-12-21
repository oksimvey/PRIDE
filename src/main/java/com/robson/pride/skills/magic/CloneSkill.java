package com.robson.pride.skills.magic;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.EntityRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.TimeUnit;

public class CloneSkill {

    public static void summonPassiveClone(Entity owner,  Entity target, boolean ispassive) {
        if (owner != null) {
            EntityType entityType;
            if (owner instanceof Player) {
                entityType = EntityRegister.CLONE_ENTITY.get();
            } else entityType = owner.getType();
            Entity clone = entityType.create(owner.level());
            if (clone != null) {
                clone.setYRot(owner.getYRot());
                clone.setPos(owner.getX(), owner.getY(), owner.getZ() );
                owner.level().addFreshEntity(clone);
                equipClone(owner, clone);
                TargetUtil.setTarget(clone, target);
                setTargetToOwnerAgain(target, clone, owner);
                if (ispassive) {
                    clone.setInvulnerable(true);
                    clone.getPersistentData().putBoolean("passive_clone", true);
                    TimerUtil.schedule(()->{if(clone.isAlive()){despawnClone(clone);}}, 2, TimeUnit.SECONDS);
                }
            }
        }
    }

    public static void despawnClone(Entity clone){
        if (clone != null){
            for  (int i = 0; i < clone.getBbHeight() * 10; i++){
                ParticleUtils.spawnParticleRelativeToEntity(ParticleTypes.DRAGON_BREATH, clone, 0, clone.getBbHeight() / 2, 0, 3, 0.1, 0.1, 0.1, 0.1f);
            }
            PlaySoundUtils.playSound(clone, SoundEvents.ENDERMAN_TELEPORT, 1, 1);
            clone.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public static void setTargetToOwnerAgain(Entity ent, Entity target, Entity owner){
        if (target != null && owner != null && ent != null) {
            if (!target.isAlive()){
                TargetUtil.setTarget(ent, owner);
            }
            else {
                setTargetToOwnerAgainLooper(ent, target, owner);
                TargetUtil.setTarget(ent, target);
            }
        }
    }

    public static void setTargetToOwnerAgainLooper(Entity ent, Entity target, Entity owner){
        TimerUtil.schedule(()-> {
            if (target != null && owner != null && ent != null) {
                setTargetToOwnerAgain(ent, target, owner);
            }
        }, 1, TimeUnit.SECONDS);
    }

    public static void equipClone(Entity owner, Entity clone) {
        if (owner instanceof LivingEntity ownerl && clone instanceof LivingEntity  clonel) {
            EquipUtils.equipArmorSet(clonel, ownerl.getItemBySlot(EquipmentSlot.HEAD), ownerl.getItemBySlot(EquipmentSlot.CHEST), ownerl.getItemBySlot(EquipmentSlot.LEGS), ownerl.getItemBySlot(EquipmentSlot.FEET));
            EquipUtils.equipMainHand(clonel, ownerl.getMainHandItem());
            EquipUtils.equipOffHand(clonel, ownerl.getOffhandItem());
        }
    }
}
