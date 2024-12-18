package com.robson.pride.skills.magic;

import com.robson.pride.api.utils.EquipUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.registries.EntityRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class CloneSkill {

    public static void summonPassiveClone(Entity owner) {
        if (owner != null) {
            EntityType entityType;
            if (owner instanceof Player) {
                entityType = EntityRegister.CLONE_ENTITY.get();
            } else entityType = owner.getType();
            Entity clone = entityType.create(owner.level());
            if (clone != null) {
                clone.setYRot(owner.getYRot());
                clone.setPos(owner.getX(), owner.getY(), owner.getZ());
                owner.level().addFreshEntity(clone);
                Entity target = TargetUtil.getTarget(owner);
                equipClone(owner, clone);
                clone.setInvulnerable(true);
                clone.getPersistentData().putBoolean("passive_clone", true);
                if (target != null && clone instanceof Mob mob && target instanceof LivingEntity targetl){
                   mob.setTarget(targetl);
                }
            }
        }
    }

    public static void despawnClone(Entity clone){
        if (clone != null){
            for  (int i = 0; i < 20; i++){
                ParticleUtils.spawnParticleRelativeToEntity(ParticleTypes.DRAGON_BREATH, clone, 0, clone.getBbHeight() / 2, 0, 3, 0.1, 0.1, 0.1, 0.1f);
            }
            clone.remove(Entity.RemovalReason.DISCARDED);
        }
    }

    public static void equipClone(Entity owner, Entity clone) {
        if (owner instanceof LivingEntity ownerl && clone instanceof LivingEntity  clonel) {
            EquipUtils.equipArmorSet(clonel, ownerl.getItemBySlot(EquipmentSlot.HEAD), ownerl.getItemBySlot(EquipmentSlot.CHEST), ownerl.getItemBySlot(EquipmentSlot.LEGS), ownerl.getItemBySlot(EquipmentSlot.FEET));
            EquipUtils.equipMainHand(clonel, ownerl.getMainHandItem());
            EquipUtils.equipOffHand(clonel, ownerl.getOffhandItem());
        }
    }
}
