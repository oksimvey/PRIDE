package com.robson.pride.entities;

import com.robson.pride.api.ai.actions.ExecuteWeaponSkillAction;
import com.robson.pride.api.ai.actions.PlayAnimationsAction;
import com.robson.pride.api.ai.combat.*;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.MobData;
import com.robson.pride.api.data.types.MobTypeData;
import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.api.item.CustomItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public interface Ronin {

    MobData MOB_DATA = new MobData(ResourceLocation.fromNamespaceAndPath("pride", "textures/entities/japanese/ronin1.png"), new ActionsBuilder(
            List.of(new ConditionalAction(
                    List.of(new DistanceCondition((byte) 0, (byte) 10)),
                    new PlayAnimationsAction(List.of(
                            Animations.UCHIGATANA_AUTO1,
                            Animations.UCHIGATANA_AUTO2,
                            Animations.UCHIGATANA_AUTO3
                    ), 0.15f)),
                    new ConditionalAction(
                            List.of(new DistanceCondition((byte) 10, (byte) 20)),
                            new PlayAnimationsAction(List.of(Animations.UCHIGATANA_SHEATHING_DASH), 0)
                    ),
                    new ConditionalAction(List.of(new ChanceCondition((byte) 50)),
                            new ExecuteWeaponSkillAction(ServerDataManager.LONGSWORD_PIERCE))))) {
        @Override
        public void onSpawn(PrideMob mob){
            mob.setItemSlot(EquipmentSlot.MAINHAND, CustomItem.createItem(ServerDataManager.KURONAMI));
        }
    };

    MobTypeData DATA = new MobTypeData("Ronin", (byte) 0) {

        @Override
        public MobData getDataByVariant(byte variant) {
            return MOB_DATA;
        }
    };
}
