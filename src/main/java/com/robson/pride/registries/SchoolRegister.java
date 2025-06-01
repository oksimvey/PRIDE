package com.robson.pride.registries;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;
import java.util.function.Supplier;

public class SchoolRegister {

    public static final ResourceKey<Registry<SchoolType>> SCHOOL_REGISTRY_KEY = ResourceKey.createRegistryKey(new ResourceLocation("irons_spellbooks", "schools"));
    private static final DeferredRegister<SchoolType> SCHOOLS;
    public static final Supplier<IForgeRegistry<SchoolType>> REGISTRY;
    public static final RegistryObject<SchoolType> DARKNESS;
    public static final RegistryObject<SchoolType> LIGHT;
    public static final RegistryObject<SchoolType> THUNDER;
    public static final RegistryObject<SchoolType> SUN;
    public static final RegistryObject<SchoolType> MOON;
    public static final RegistryObject<SchoolType> BLOOD;
    public static final RegistryObject<SchoolType> WIND;
    public static final RegistryObject<SchoolType> NATURE;
    public static final RegistryObject<SchoolType> ICE;
    public static final RegistryObject<SchoolType> WATER;

    public static void register(IEventBus eventBus) {
        SCHOOLS.register(eventBus);
        eventBus.addListener(SchoolRegistry::clientSetup);
    }

    private static RegistryObject<SchoolType> registerSchool(SchoolType schoolType) {
        return SCHOOLS.register(schoolType.getId().getPath(), () -> schoolType);
    }

    static {
        SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, "pride");
        REGISTRY = SCHOOLS.makeRegistry(() -> (new RegistryBuilder()).disableSaving().disableOverrides());
        DARKNESS = registerSchool(new SchoolType(new ResourceLocation("pride:darkness"), ItemTags.create(new ResourceLocation("pride:darkness_focus")),
                Component.literal("Darkness").withStyle(ChatFormatting.BLACK), LazyOptional.of(AttributeRegister.DARKNESS_POWER::get),
                LazyOptional.of(AttributeRegister.DARKNESS_RESIST::get), LazyOptional.of(SoundRegistry.CLEANSE_CAST::get), ISSDamageTypes.ELDRITCH_MAGIC));
        LIGHT = registerSchool(new SchoolType(new ResourceLocation("pride:light"), ItemTags.create(new ResourceLocation("pride:light_focus")),
                Component.literal("Light").withStyle(ChatFormatting.YELLOW), LazyOptional.of(AttributeRegister.LIGHT_POWER::get),
                LazyOptional.of(AttributeRegister.LIGHT_RESIST::get), LazyOptional.of(SoundRegistry.HOLY_CAST::get), ISSDamageTypes.HOLY_MAGIC));
        THUNDER = registerSchool(new SchoolType(new ResourceLocation("pride:thunder"), ItemTags.create(new ResourceLocation("pride:thunder_focus")),
                Component.literal("Thunder").withStyle(ChatFormatting.AQUA), LazyOptional.of(AttributeRegister.THUNDER_POWER::get),
                LazyOptional.of(AttributeRegister.THUNDER_RESIST::get), LazyOptional.of(SoundRegistry.LIGHTNING_CAST::get), ISSDamageTypes.LIGHTNING_MAGIC));
        SUN = registerSchool(new SchoolType(new ResourceLocation("pride:sun"), ItemTags.create(new ResourceLocation("pride:sun_focus")),
                Component.literal("Sun").withStyle(ChatFormatting.GOLD), LazyOptional.of(AttributeRegister.SUN_POWER::get),
                LazyOptional.of(AttributeRegister.SUN_RESIST::get), LazyOptional.of(SoundRegistry.FIRE_CAST::get), ISSDamageTypes.FIRE_MAGIC));
        MOON = registerSchool(new SchoolType(new ResourceLocation("pride:moon"), ItemTags.create(new ResourceLocation("pride:moon_focus")),
                Component.literal("Moon").withStyle(ChatFormatting.DARK_PURPLE), LazyOptional.of(AttributeRegister.MOON_POWER::get),
                LazyOptional.of(AttributeRegister.MOON_RESIST::get), LazyOptional.of(SoundRegistry.ENDER_CAST::get), ISSDamageTypes.ENDER_MAGIC));
        BLOOD = registerSchool(new SchoolType(new ResourceLocation("pride:blood"), ItemTags.create(new ResourceLocation("pride:blood_focus")),
                Component.literal("Blood").withStyle(ChatFormatting.DARK_RED), LazyOptional.of(AttributeRegister.BLOOD_POWER::get),
                LazyOptional.of(AttributeRegister.BLOOD_RESIST::get), LazyOptional.of(SoundRegistry.BLOOD_CAST::get), ISSDamageTypes.BLOOD_MAGIC));
        WIND = registerSchool(new SchoolType(new ResourceLocation("pride:wind"), ItemTags.create(new ResourceLocation("pride:wind_focus")),
                Component.literal("Wind").withStyle(ChatFormatting.WHITE), LazyOptional.of(AttributeRegister.WIND_POWER::get),
                LazyOptional.of(AttributeRegister.WIND_RESIST::get), LazyOptional.of(SoundRegistry.GUST_CAST::get), ISSDamageTypes.EVOCATION_MAGIC));
        NATURE = registerSchool(new SchoolType(new ResourceLocation("pride:nature"), ItemTags.create(new ResourceLocation("pride:nature_focus")),
                Component.literal("Nature").withStyle(ChatFormatting.DARK_GREEN), LazyOptional.of(AttributeRegister.NATURE_POWER::get),
                LazyOptional.of(AttributeRegister.NATURE_RESIST::get), LazyOptional.of(SoundRegistry.NATURE_CAST::get), ISSDamageTypes.NATURE_MAGIC));
        ICE = registerSchool(new SchoolType(new ResourceLocation("pride:ice"), ItemTags.create(new ResourceLocation("pride:ice_focus")),
                Component.literal("Ice").withStyle(ChatFormatting.DARK_AQUA), LazyOptional.of(AttributeRegister.ICE_POWER::get),
                LazyOptional.of(AttributeRegister.ICE_RESIST::get), LazyOptional.of(SoundRegistry.ICE_CAST::get), ISSDamageTypes.ICE_MAGIC));
        WATER = registerSchool(new SchoolType(new ResourceLocation("pride:water"), ItemTags.create(new ResourceLocation("pride:water_focus")),
                Component.literal("Water").withStyle(ChatFormatting.DARK_BLUE), LazyOptional.of(AttributeRegister.WATER_POWER::get),
                LazyOptional.of(AttributeRegister.WATER_RESIST::get), LazyOptional.of(SoundRegistry.ACID_ORB_CAST::get), DamageTypes.DROWN));

    }
}
