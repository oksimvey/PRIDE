package com.robson.pride.mechanics;

import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.concurrent.TimeUnit;

public class MikiriCounter {

    public static void setMikiri(Entity ent, String MikiriType,int delay, int window){
        TimerUtil.schedule(()->  ent.getPersistentData().putString("Mikiri", MikiriType), delay, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()-> ent.getPersistentData().putString("Mikiri", ""), window, TimeUnit.MILLISECONDS);
    }

    public static void onPierceMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }

    public static void onKickMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }

    public static void onSweepMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }

    public static void onArrowMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }

    public static void onSpellMikiri(Entity ent, Entity ddmgent, LivingAttackEvent event){

    }
}
