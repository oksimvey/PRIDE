package com.robson.pride.main.registries;

import com.robson.pride.main.Pride;
import com.robson.pride.progression.ProgressionGUI;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GUIRegister {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Pride.MODID);
    public static final RegistryObject<MenuType<ProgressionGUI>> PROGRESSION_GUI = REGISTRY.register("progression_gui", () -> IForgeMenuType.create(ProgressionGUI::new));
}
