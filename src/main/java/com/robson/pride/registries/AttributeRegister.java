package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Pride.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeRegister {

    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, Pride.MODID);

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }

    public static final RegistryObject<Attribute> MAX_WEIGHT = ATTRIBUTES.register("max_weight", () -> new RangedAttribute("attribute." + Pride.MODID + ".max_weight", 50.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> ARROW_POWER  = ATTRIBUTES.register("arrow_power", ()-> new RangedAttribute("attribute." + Pride.MODID + ".arrow_power", 100.0D, 0.0D,  1024.0D).setSyncable(true));

    public static final RegistryObject<Attribute> DARKNESS_RESIST = newResistanceAttribute("darkness");
    public static final RegistryObject<Attribute> LIGHT_RESIST = newResistanceAttribute("light");
    public static final RegistryObject<Attribute> THUNDER_RESIST = newResistanceAttribute("thunder");
    public static final RegistryObject<Attribute> SUN_RESIST = newResistanceAttribute("sun");
    public static final RegistryObject<Attribute> MOON_RESIST = newResistanceAttribute("moon");
    public static final RegistryObject<Attribute> BLOOD_RESIST = newResistanceAttribute("blood");
    public static final RegistryObject<Attribute> WIND_RESIST = newResistanceAttribute("wind");
    public static final RegistryObject<Attribute> NATURE_RESIST = newResistanceAttribute("nature");
    public static final RegistryObject<Attribute> ICE_RESIST = newResistanceAttribute("ice");
    public static final RegistryObject<Attribute> WATER_RESIST = newResistanceAttribute("water");

    public static final RegistryObject<Attribute> DARKNESS_POWER = newPowerAttribute("darkness");
    public static final RegistryObject<Attribute> LIGHT_POWER = newPowerAttribute("light");
    public static final RegistryObject<Attribute> THUNDER_POWER = newPowerAttribute("thunder");
    public static final RegistryObject<Attribute> SUN_POWER = newPowerAttribute("sun");
    public static final RegistryObject<Attribute> MOON_POWER = newPowerAttribute("moon");
    public static final RegistryObject<Attribute> BLOOD_POWER = newPowerAttribute("blood");
    public static final RegistryObject<Attribute> WIND_POWER = newPowerAttribute("wind");
    public static final RegistryObject<Attribute> NATURE_POWER = newPowerAttribute("nature");
    public static final RegistryObject<Attribute> ICE_POWER = newPowerAttribute("ice");
    public static final RegistryObject<Attribute> WATER_POWER = newPowerAttribute("water");

    private static RegistryObject<Attribute> newResistanceAttribute(String id) {
        return ATTRIBUTES.register(id + "_resist", () -> (new MagicRangedAttribute("attribute.pride." + id + "_resist", 1.0D, -100, 100).setSyncable(true)));
    }

    private static RegistryObject<Attribute> newPowerAttribute(String id) {
        return ATTRIBUTES.register(id + "_power", () -> (new MagicRangedAttribute("attribute.pride." + id + "_power", 1.0D, -100, 100).setSyncable(true)));
    }

    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent e) {
        e.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> e.add(entity, attribute.get())));
    }

}