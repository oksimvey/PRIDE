package com.robson.pride.registries;

import com.robson.pride.main.Pride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundsRegister {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "pride");
    public static final RegistryObject<SoundEvent> SHIELDPARRY = registerSound("shieldparry");
    public static final RegistryObject<SoundEvent> PERILOUS = registerSound("perilous");
    public static final RegistryObject<SoundEvent> EXECUTION = registerSound("execution");

    public static final RegistryObject<SoundEvent> SHOGUN = registerSound("shogun");

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation res = new ResourceLocation(Pride.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));
    }
}