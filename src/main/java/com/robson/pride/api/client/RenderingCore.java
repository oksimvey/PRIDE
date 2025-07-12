package com.robson.pride.api.client;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.skill.DurationSkillData;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.LodTick;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.epicfight.styles.PrideStyles;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class RenderingCore {

    public static void entityRenderer(LivingEntity ent) {
        if (ent != null && LodTick.canTick(ent, 1)) {
            LivingEntityPatch<?> entityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (entityPatch != null) {
                if (ent.tickCount % 10 == 0) {
                    ParticleTracking.tickParticleMapping(ent.getMainHandItem(), ent);
                    ParticleTracking.tickParticleMapping(ent.getOffhandItem(), ent);
                }
                ElementData element = ParticleTracking.getItemElementForImbuement(ent.getMainHandItem());
                if (element != null) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, entityPatch, Armatures.BIPED.get().toolR, element.getNormalParticleType(), ParticleTracking.getAABBForImbuement(ent.getMainHandItem(), ent), element.getParticleAmount());
                }
                if (entityPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getStyle(entityPatch) == PrideStyles.DUAL_WIELD) {
                    ElementData element2 = ParticleTracking.getItemElementForImbuement(ent.getOffhandItem());
                    if (element2 != null) {
                        ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, entityPatch, Armatures.BIPED.get().toolL, element2.getNormalParticleType(), ParticleTracking.getAABBForImbuement(ent.getOffhandItem(), ent), element2.getParticleAmount());
                    }
                }
                for (byte value : SkillDataManager.getActiveSkills(ent)) {
                    DurationSkillData data = SkillDataManager.INSTANCE.getByID(value);
                    if (data != null) {
                        data.onClientTick(ent);
                    }
                }
                if (SkillDataManager.ACTIVE_WEAPON_SKILL.get(ent) != null) {

                }
            }
        }
    }
}
