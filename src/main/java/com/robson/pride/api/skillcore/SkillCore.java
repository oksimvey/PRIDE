package com.robson.pride.api.skillcore;
import com.robson.pride.api.data.types.WeaponData;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.particles.StringParticle;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SkillCore {

    public enum WeaponArtTier {
        MYTHICAL,
        LEGENDARY,
        EPIC,
        RARE,
        UNCOMMON,
        COMMON;
    }

    public static void onSkillExecute(LivingEntity ent) {
        if (ent != null) {
            if (ent.getMainHandItem().getTag().getBoolean("hasweaponart")) {
                weaponArtCore(ent, ent.getMainHandItem().getTag().getShort("weapon_art"));
            }
            else defaultSkillCore(ent, ent.getMainHandItem());
        }
    }



    public static void defaultSkillCore(LivingEntity ent, ItemStack weapon) {
        WeaponData weaponData = ServerDataManager.getWeaponData(weapon);
        if (weaponData != null) {
                WeaponSkillData skill = weaponData.getSkill();
                if (skill != null) {
                    skill.tryToExecute(ent);
                }
        }
    }

    public static boolean stackablePassiveBase(Entity ent, float amounttoadd, String tag, StringParticle.StringParticleTypes type) {
        if (ent != null) {
            ent.getPersistentData().putByte(tag, (byte) (ent.getPersistentData().getByte(tag) + amounttoadd));
            if (ent.getPersistentData().getByte(tag) >= 100) {
                ParticleUtils.spawnStringParticle(ent, 100 + "%", type, 60);
                ent.getPersistentData().putByte(tag, (byte) 0);
                ent.getPersistentData().putBoolean(tag, true);
                return true;
            }
            ParticleUtils.spawnStringParticle(ent, ent.getPersistentData().getByte(tag) + "%", type, 60);
        }
        return false;
    }

    public static void weaponArtCore(LivingEntity ent, short weaponart) {
        WeaponSkillData skill = ServerDataManager.getWeaponSkillData(weaponart);
        if (skill != null) {
            skill.tryToExecute(ent);
        }
    }

    public static void loopParticleHit(Entity dmgent, Entity target, Particle particle, List<Entity> hitentities, float particleradius, Runnable function) {
        if (dmgent != null && target != null && particle != null && hitentities != null && function != null) {
            if (canHit(dmgent, target, hitentities)) {
                if (MathUtils.getTotalDistance((float) (particle.getPos().x - target.getX()), (float) (particle.getPos().y - target.getY()), (float) (particle.getPos().z - target.getZ())) <= particleradius + (target.getBbWidth() / 2)) {
                    hitentities.add(target);
                    function.run();
                } else
                    TimerUtil.schedule(() -> loopParticleHit(dmgent, target, particle, hitentities, particleradius, function), 50, TimeUnit.MILLISECONDS);
            }
        }
    }

    public static void loopEntityHit(Entity dmgent, Entity target, Entity ent, List<Entity> hitentities, float particleradius, Runnable function) {
        if (dmgent != null && target != null && ent != null && hitentities != null && function != null) {
            if (canHit(dmgent, target, hitentities)) {
                if (MathUtils.getTotalDistance(target.position(), ent.position()) <= particleradius + (target.getBbWidth() / 2)) {
                    hitentities.add(target);
                    function.run();
                } else
                    TimerUtil.schedule(() -> loopEntityHit(dmgent, target, ent, hitentities, particleradius, function), 50, TimeUnit.MILLISECONDS);
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
