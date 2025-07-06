package com.robson.pride.entities;

import com.robson.pride.api.ai.actions.combat.ExecuteWeaponSkillAction;
import com.robson.pride.api.ai.actions.interaction.PlayAnimationsAction;
import com.robson.pride.api.ai.actions.builder.ActionsBuilder;
import com.robson.pride.api.ai.actions.builder.ConditionalAction;
import com.robson.pride.api.ai.conditions.CooldownCondition;
import com.robson.pride.api.ai.conditions.DistanceCondition;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.MobData;
import com.robson.pride.api.data.types.MobTypeData;
import com.robson.pride.api.data.types.ServerConstants;
import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.api.item.CustomItem;
import com.sun.tools.javac.Main;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import yesman.epicfight.gameasset.Animations;

import java.util.List;
import java.util.Map;

public interface Ronin {

    MobData MOB_DATA = new MobData(ResourceLocation.fromNamespaceAndPath("pride", "textures/entities/japanese/ronin1.png"), new ActionsBuilder(
            List.of(new ConditionalAction(List.of(new CooldownCondition(7000)),
                    new ExecuteWeaponSkillAction(ServerDataManager.LONGSWORD_PIERCE)),
                    new ConditionalAction(
                    List.of(new DistanceCondition((byte) 0, (byte) 10)),
                    new PlayAnimationsAction(List.of(
                            Animations.UCHIGATANA_AUTO1,
                            Animations.UCHIGATANA_AUTO2,
                            Animations.UCHIGATANA_AUTO3
                    ), 0.15f)),
                    new ConditionalAction(
                            List.of(new DistanceCondition((byte) 10, (byte) 20)),
                            new PlayAnimationsAction(List.of(Animations.UCHIGATANA_SHEATHING_DASH), 0)
                    ))),
            Map.ofEntries(
                    Map.entry(EquipmentSlot.MAINHAND, CustomItem.createItem(ServerDataManager.KURONAMI))
            )){};

    MobTypeData DATA = new MobTypeData("Ronin", (byte) 0, ServerConstants.ASIAN_MUSIC, (byte) 1) {

        @Override
        public MobData getDataByVariant(byte variant) {
            return MOB_DATA;
        }
    };
}
