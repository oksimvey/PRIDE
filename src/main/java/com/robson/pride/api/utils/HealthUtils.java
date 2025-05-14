package com.robson.pride.api.utils;

import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.concurrent.TimeUnit;

public class HealthUtils {

    public static void hurtEntity(Entity ent, float amount, DamageSource dmg) {
        TimerUtil.schedule(() -> {
            if (ent != null) {
                ent.hurt(dmg, amount);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    public static void dealBlockableDmg(Entity dmgent, Entity ent, float amount){
        LivingEntityPatch playerpatch =  EpicFightCapabilities.getEntityPatch(dmgent, LivingEntityPatch.class);
        if (ent != null && playerpatch != null) {
            EpicFightDamageSource damage = playerpatch.getDamageSource(AnimationsRegister.ONEHAND_SHOOT, InteractionHand.MAIN_HAND);
            damage.setStunType(StunType.HOLD);
            damage.setImpact(0.5F);
            damage.addRuntimeTag(EpicFightDamageType.WEAPON_INNATE);
            ent.invulnerableTime = 0;
            float entity1damage = 5.0F;
            AttackResult attackResult = playerpatch.tryHarm(ent, damage, entity1damage);
            if (attackResult.resultType == AttackResult.ResultType.SUCCESS){
                ent.hurt(damage, amount);
            }
}
    }
}
