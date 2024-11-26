package com.robson.pride.main.registries;

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

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation res = new ResourceLocation(Pride.MODID, name);
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(res));

    }
}