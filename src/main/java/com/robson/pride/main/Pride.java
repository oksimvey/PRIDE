package com.robson.pride.main;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.robson.pride.command.*;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import com.robson.pride.registries.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

@Mod("pride")
public class Pride {

    public static final String MOD_ID = "pride";

    public Pride() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SoundsRegister.SOUNDS.register(bus);
        ItemsRegister.REGISTRY.register(bus);
        EntityRegister.ENTITIES.register(bus);
        EntityRegister.SPECIAL_ENTITIES.register(bus);
        bus.addListener(AnimationsRegister::registerAnimations);
        AttributeRegister.register(bus);
        SchoolRegister.register(bus);
        GUIRegister.REGISTRY.register(bus);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyRegister::registerKeyMappings);
        ParticleRegister.PARTICLES.register(bus);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, WeaponCategoriesEnum.class);
        Style.ENUM_MANAGER.registerEnumCls(Pride.MOD_ID, PrideStyles.class);
        EffectRegister.MOB_EFFECTS.register(bus);
        PrideTabRegister.register(bus);
    }

    private void registerCommands(final RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(Pride.MOD_ID)
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("living_entity", EntityArgument.entity())
                                .then(AddXpCommand.register())
                                .then(ResetLevelCommand.register())
                                .then(SetElementCommand.register())));
    }
}
