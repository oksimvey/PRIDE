package com.robson.pride.api.skillcore;

import com.robson.pride.api.data.PrideCapabilityReloadListener;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.TimerUtil;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.client.particle.Particle;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.registries.WeaponSkillRegister.WeaponSkills;

public class SkillCore {

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasweaponart")) {
                weaponArtCore(ent, ent.getMainHandItem().getTag().getString("weapon_art"));
            }
            else defaultSkillCore(ent, ent.getMainHandItem());
        }
    }

    public static void defaultSkillCore(LivingEntity ent, ItemStack weapon) {
       CompoundTag tag = PrideCapabilityReloadListener.CAPABILITY_WEAPON_DATA_MAP.get(weapon.getItem());
       if (tag != null){
           if (tag.contains("skill")){
               WeaponSkillBase skill = WeaponSkills.get(tag.getString("skill"));
               if (skill != null){
                   skill.tryToExecute(ent);
               }
           }
       }
    }

    public static void weaponArtCore(LivingEntity ent, String weaponart) {
        WeaponSkillBase skill = WeaponSkills.get(weaponart);
        if (skill != null){
            skill.tryToExecute(ent);
        }
    }

    public static void loopParticleHit(Entity dmgent, Entity target, Particle particle, List<Entity> hitentities, float particleradius, Runnable function){
        if (dmgent != null && target != null && particle != null && hitentities != null && function != null){
            if (canHit(dmgent, target, hitentities)){
                if (MathUtils.getTotalDistance(particle.getPos().x - target.getX(), particle.getPos().y - target.getY(), particle.getPos().z - target.getZ()) - target.getBbWidth() / 2 <= particleradius){
                    hitentities.add(target);
                    function.run();
                }
                else TimerUtil.schedule(()-> loopParticleHit(dmgent, target, particle, hitentities, particleradius, function), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static boolean canHit(Entity dmgent, Entity target, List<Entity> hitentities) {
        if (dmgent != null && target != null) {
            return target instanceof LivingEntity && !DamageSources.isFriendlyFireBetween(target, dmgent) && target != dmgent && !hitentities.contains(target);
        }
        return false;
    }
}
