package com.robson.pride.skills.special;

import com.robson.pride.api.data.types.DurationSkillData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface GuardSkill {

    DurationSkillData DATA = new DurationSkillData() {

        @Override
        public void onStart(LivingEntity ent) {
            if (ent instanceof Player player){
                player.startUsingItem(InteractionHand.MAIN_HAND);
            }
        }

        @Override
        public void onAttacked(LivingEntity ent, LivingAttackEvent event) {

        }

        @Override
        public void onHurt(LivingEntity ent, LivingHurtEvent event) {


        }

        @Override
        public void onClientTick(LivingEntity ent) {

        }

        @Override
        public void onEnd(LivingEntity ent) {
           if (ent instanceof Player player){
               player.stopUsingItem();
           }
        }
    };
}
