package com.robson.pride.api.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class AttributeUtils {
    public static float getAttributeValue(LivingEntity ent, String attribute) {
        if (ent.getAttribute(requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute)))) != null) {
            return (float) ent.getAttributeValue(requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute))));
        }
        return 0f;
    }

    public static float getAttributeBaseValue(LivingEntity ent, String attribute) {
        return (float) ent.getAttributeBaseValue(requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute))));
    }

    public static float findAttributeModifierbyUUID(LivingEntity ent, String UUID, String attributename) {
        Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attributename));
        if (attribute != null) {
            AttributeInstance attributeInstance = ent.getAttribute(attribute);
            if (attributeInstance != null) {
                AttributeModifier modifier = attributeInstance.getModifier(java.util.UUID.fromString(UUID));
                if (modifier != null) {
                    return (float) modifier.getAmount();
                }
            }
        }
        return 0f;
    }

    public static void addModifier(LivingEntity ent, String attributename, String uuid, double amount, AttributeModifier.Operation operation) {
        Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attributename));
        if (attribute != null) {
            AttributeInstance attributeInstance = ent.getAttribute(attribute);
            if (attributeInstance != null) {
                removeModifier(ent, attributename, uuid);
                attributeInstance.addPermanentModifier(((new AttributeModifier(UUID.fromString(uuid), "modifier", amount, operation))));
            }
        }
    }
    public static void removeModifier(LivingEntity ent, String attributename, String uuid) {
        if (ent != null) {
            Attribute attribute = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attributename));
            if (attribute != null) {
                AttributeInstance attributeInstance = ent.getAttribute(attribute);
                if (attributeInstance != null) {
                    attributeInstance.removePermanentModifier(UUID.fromString(uuid));
                }
            }
        }
    }

    public static void addModifiertoAnim(Entity ent, String attributename, float amount, int duration, AttributeModifier.Operation operation){
        addModifier((LivingEntity) ent, attributename, "dc35bbaf-6bcd-4eb6-8836-d3d09812ea84", -1000, AttributeModifier.Operation.ADDITION);
        TimerUtil.schedule(()-> addModifier((LivingEntity) ent, attributename, "63104183-c72f-4f0b-9c98-b06743e886de", amount, operation), 10, TimeUnit.MILLISECONDS);
        TimerUtil.schedule(()->{
            removeModifier((LivingEntity) ent, attributename, "63104183-c72f-4f0b-9c98-b06743e886de" );
            removeModifier((LivingEntity) ent, attributename, "dc35bbaf-6bcd-4eb6-8836-d3d09812ea84" );
        }, duration, TimeUnit.MILLISECONDS);
    }
}