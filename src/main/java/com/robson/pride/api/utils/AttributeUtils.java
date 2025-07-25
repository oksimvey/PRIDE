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

    public static float getAttributeValue(Entity ent, String attribute) {
        if (ent instanceof LivingEntity livingEntity) {
            if (livingEntity.getAttribute(requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute)))) != null) {
                return (float) livingEntity.getAttributeValue(requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attribute))));
            }
        }
        return 1f;
    }

    public static float getAttributeValue(Entity ent, Attribute attribute) {
        if (ent instanceof LivingEntity && attribute != null){
            AttributeInstance attributeInstance = ((LivingEntity) ent).getAttribute(attribute);
            if (attributeInstance != null) {
                return (float) attributeInstance.getValue();
            }
        }
        return 0;
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

    public static void addModifier(LivingEntity ent, Attribute attribute, String uuid, double amount, AttributeModifier.Operation operation) {
        if (ent != null) {
            AttributeInstance attributeInstance = ent.getAttribute(attribute);
            if (attributeInstance != null) {
                removeModifier(ent, attribute, uuid);
                attributeInstance.addPermanentModifier(((new AttributeModifier(UUID.fromString(uuid), "modifier", amount, operation))));
            }
        }
    }

    public static void removeModifier(LivingEntity ent, Attribute attribute, String uuid) {
        if (ent != null) {
            AttributeInstance attributeInstance = ent.getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.removePermanentModifier(UUID.fromString(uuid));

            }
        }
    }
}