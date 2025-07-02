package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.skills.special.GuardSkill;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public interface SkillDataManager extends ServerDataManagerImpl<DurationSkillData> {

    ConcurrentHashMap<LivingEntity, List<Byte>> ACTIVE_SKILLS = new ConcurrentHashMap<>();

    ConcurrentHashMap<LivingEntity, WeaponSkillData> ACTIVE_WEAPON_SKILL = new ConcurrentHashMap<>();

    byte GUARD = 1;

   SkillDataManager INSTANCE = id -> switch (id){

           case GUARD -> GuardSkill.DATA;

           default -> null;
       };


   static List<Byte> getActiveSkills(LivingEntity ent) {
       if (ent != null) {
           return ACTIVE_SKILLS.getOrDefault(ent, new ArrayList<>());
       }
       return new ArrayList<>();
   }

   static boolean isSkillActive(LivingEntity ent, byte skillid) {
       return getActiveSkills(ent).contains(skillid);
   }

   static void removeSkill(LivingEntity ent, byte data) {
       if (ent != null){
           List<Byte> list = getActiveSkills(ent);
           list.remove((Byte) data);
           INSTANCE.getByID(data).onEnd(ent);
           ACTIVE_SKILLS.put(ent, list);
       }
   }

   static void addSkill(LivingEntity ent, byte data) {
       if (ent != null && INSTANCE.getByID(data) != null && !isSkillActive(ent, data)){
           List<Byte> list = getActiveSkills(ent);
           list.add(data);
           INSTANCE.getByID(data).onStart(ent);
           ACTIVE_SKILLS.put(ent, list);
       }
   }
}
