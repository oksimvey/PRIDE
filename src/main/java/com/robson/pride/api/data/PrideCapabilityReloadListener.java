package com.robson.pride.api.data;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;

import com.robson.pride.registries.AttributeRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.data.conditions.EpicFightConditions;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.network.server.SPDatapackSync;
import yesman.epicfight.world.capabilities.item.ArmorCapability;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.TagBasedSeparativeCapability;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.capabilities.item.WeaponTypeReloadListener;
import yesman.epicfight.world.capabilities.provider.ItemCapabilityProvider;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class PrideCapabilityReloadListener extends SimpleJsonResourceReloadListener {
    public static final String DIRECTORY = "pride_capabilities";
    private static final Gson GSON = (new GsonBuilder()).create();
    private static final Map<Item, CompoundTag> CAPABILITY_ARMOR_DATA_MAP = Maps.newHashMap();
    public static Map<Item, CompoundTag> CAPABILITY_WEAPON_DATA_MAP = Maps.newHashMap();

    public PrideCapabilityReloadListener() {
        super(GSON, DIRECTORY);
    }

    @Override
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profileIn) {
        CAPABILITY_ARMOR_DATA_MAP.clear();
        CAPABILITY_WEAPON_DATA_MAP.clear();

        return super.prepare(resourceManager, profileIn);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation rl = entry.getKey();
            String path = rl.getPath();
            if (path.contains("/") && !path.contains("types")) {
                String[] str = path.split("/", 2);
                ResourceLocation registryName = new ResourceLocation(rl.getNamespace(), str[1]);

                if (!ForgeRegistries.ITEMS.containsKey(registryName)) {
                    new NoSuchElementException("Item Capability Exception: No Item named " + registryName).printStackTrace();
                    continue;
                }

                Item item = ForgeRegistries.ITEMS.getValue(registryName);
                CompoundTag tag = null;

                try {
                    tag = TagParser.parseTag(entry.getValue().toString());
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
                try {
                    if (str[0].equals("armors")) {
                        CapabilityItem capability = deserializeArmor(item, tag);
                        ItemCapabilityProvider.put(item, capability);
                        CAPABILITY_ARMOR_DATA_MAP.put(item, tag);
                    }
                    else if (str[0].equals("weapons")) {
                        CapabilityItem capability = deserializeWeapon(item, tag);
                        ItemCapabilityProvider.put(item, capability);
                        CAPABILITY_WEAPON_DATA_MAP.put(item, tag);
                    }
                } catch (Exception e) {
                    EpicFightMod.LOGGER.warn("Error while deserializing datapack for " + registryName);
                    e.printStackTrace();
                }
            }
        }

        ItemCapabilityProvider.addDefaultItems();
    }

    public static CapabilityItem deserializeArmor(Item item, CompoundTag tag) {
        ArmorCapability.Builder builder = ArmorCapability.builder();
        if (tag.contains("attributes")) {
            CompoundTag attributes = tag.getCompound("attributes");
            builder.weight(attributes.getDouble("weight")).stunArmor(attributes.getDouble("stun_armor"));
        }
        builder.item(item);

        return builder.build();
    }

    public static CapabilityItem deserializeWeapon(Item item, CompoundTag tag) {
        CapabilityItem capability;

        if (tag.contains("variations")) {
            ListTag jsonArray = tag.getList("variations", 10);
            List<Pair<Condition<ItemStack>, CapabilityItem>> list = Lists.newArrayList();
            CapabilityItem.Builder innerDefaultCapabilityBuilder = tag.contains("type") ? WeaponTypeReloadListener.getOrThrow(tag.getString("type")).apply(item) : CapabilityItem.builder();

            if (tag.contains("attributes")) {
                CompoundTag attributes = tag.getCompound("attributes");

                for (String key : attributes.getAllKeys()) {
                    Map<Attribute, AttributeModifier> attributeEntry = deserializeAttributes(attributes.getCompound(key));

                    for (Map.Entry<Attribute, AttributeModifier> attribute : attributeEntry.entrySet()) {
                        innerDefaultCapabilityBuilder.addStyleAttibutes(Style.ENUM_MANAGER.getOrThrow(key), Pair.of(attribute.getKey(), attribute.getValue()));
                    }
                }
            }

            for (Tag jsonElement : jsonArray) {
                CompoundTag innerTag = ((CompoundTag)jsonElement);
                Supplier<Condition<ItemStack>> conditionProvider = EpicFightConditions.getConditionOrThrow(new ResourceLocation(innerTag.getString("condition")));
                Condition<ItemStack> condition = conditionProvider.get().read(innerTag.getCompound("predicate"));

                list.add(Pair.of(condition, deserializeWeapon(item, innerTag)));
            }

            capability = new TagBasedSeparativeCapability(list, innerDefaultCapabilityBuilder.build());
        } else {
            CapabilityItem.Builder builder = tag.contains("type") ? WeaponTypeReloadListener.getOrThrow(tag.getString("type")).apply(item) : CapabilityItem.builder();

            if (tag.contains("attributes")) {
                CompoundTag attributes = tag.getCompound("attributes");

                for (String key : attributes.getAllKeys()) {
                    Map<Attribute, AttributeModifier> attributeEntry = deserializeAttributes(attributes.getCompound(key));

                    for (Map.Entry<Attribute, AttributeModifier> attribute : attributeEntry.entrySet()) {
                        builder.addStyleAttibutes(Style.ENUM_MANAGER.getOrThrow(key), Pair.of(attribute.getKey(), attribute.getValue()));
                    }
                }
            }

            if (tag.contains("collider") && builder instanceof WeaponCapability.Builder weaponCapBuilder) {
                CompoundTag colliderTag = tag.getCompound("collider");

                try {
                    Collider collider = ColliderPreset.deserializeSimpleCollider(colliderTag);
                    weaponCapBuilder.collider(collider);
                } catch (IllegalArgumentException e) {
                    EpicFightMod.LOGGER.warn("Cannot deserialize collider: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            capability = builder.build();
        }

        return capability;
    }

    private static Map<Attribute, AttributeModifier> deserializeAttributes(CompoundTag tag) {
        Map<Attribute, AttributeModifier> modifierMap = Maps.newHashMap();

        if (tag.contains("armor_negation")) {
            modifierMap.put(EpicFightAttributes.ARMOR_NEGATION.get(), EpicFightAttributes.getArmorNegationModifier(tag.getDouble("armor_negation")));
        }
        if (tag.contains("impact")) {
            modifierMap.put(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(tag.getDouble("impact")));
        }
        if (tag.contains("max_strikes")) {
            modifierMap.put(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(tag.getInt("max_strikes")));
        }
        if (tag.contains("damage_bonus")) {
            modifierMap.put(Attributes.ATTACK_DAMAGE, EpicFightAttributes.getDamageBonusModifier(tag.getDouble("damage_bonus")));
        }
        if (tag.contains("speed_bonus")) {
            modifierMap.put(Attributes.ATTACK_SPEED, EpicFightAttributes.getSpeedBonusModifier(tag.getDouble("speed_bonus")));
        }
        if (tag.contains("darkness_power")){
            modifierMap.put(AttributeRegister.DARKNESS_POWER.get(), new AttributeModifier(UUID.fromString("5975a582-14e0-4d16-b6ef-8cbe2c9593c0"), "epicfight:weapon_modifier", tag.getDouble("weight"), AttributeModifier.Operation.ADDITION));

        }
        if (tag.contains("weight")){
            modifierMap.put(EpicFightAttributes.WEIGHT.get(), new AttributeModifier(UUID.fromString("5975a582-14e0-4d16-b6ef-8cbe2c9593c0"), "epicfight:weapon_modifier", tag.getDouble("weight"), AttributeModifier.Operation.ADDITION));
        }
        return modifierMap;
    }
}