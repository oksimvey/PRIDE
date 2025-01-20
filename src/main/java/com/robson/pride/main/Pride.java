package com.robson.pride.main;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.robson.pride.api.ai.DataConditions;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.skillcore.WeaponArtRegister;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

@Mod("pride")
public class Pride {
    public static final Logger LOGGER = LogManager.getLogger(Pride.class);
    public static final String MODID = "pride";
    private static final String PROTOCOL_VERSION = "1";
    public static final String MOD_ID = "pride";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public Pride() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SoundsRegister.SOUNDS.register(bus);
        ItemsRegister.REGISTRY.register(bus);
        EntityRegister.ENTITIES.register(bus);
        AttributeRegister.register(bus);
        GUIRegister.REGISTRY.register(bus);
        MinecraftForge.EVENT_BUS.addListener(this::registerCommands);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(KeyRegister::registerKeyMappings);
        ParticleRegister.PARTICLES.register(bus);
        bus.addListener(AnimationsRegister::registerAnimations);
        WeaponCategory.ENUM_MANAGER.registerEnumCls(MOD_ID, WeaponCategoriesEnum.class);
        Style.ENUM_MANAGER.registerEnumCls(Pride.MODID, PrideStyles.class);
        EffectRegister.MOB_EFFECTS.register(bus);
        DataConditions.CONDITIONS.register(bus);
        PrideTabRegister.register(bus);
        SkillCore.WeaponSkill.ENUM_MANAGER.registerEnumCls(MODID, WeaponArtRegister.class);
    }

    private void registerCommands(final RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(Pride.MOD_ID)
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("living_entity", EntityArgument.entity())
                                .then(PerilousCommand.register())
                                .then(MobEatCommand.register())
                                .then(MikiriCommand.register())
                                .then(ShootProjectileCommand.register())
                                .then(SkillExecuteCommand.register())
                                .then(CastSpellCommand.register())
                                .then(SetElementCommand.register())));
    }


    private void setupCommon(FMLCommonSetupEvent event) {
        PacketRegister.register();
    }

    private void setupClient(FMLClientSetupEvent event) {
        KeyRegister.setupClient(event);
    }
}
