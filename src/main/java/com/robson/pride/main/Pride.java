package com.robson.pride.main;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.nameless.indestructible.server.network.SPCancelBossInfo;
import com.robson.pride.api.ai.DataConditions;
import com.robson.pride.api.biomesettings.BiomeSettingsManager;
import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.command.*;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import com.robson.pride.registries.*;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
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
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

@Mod("pride")
public class Pride {
    public static final Logger LOGGER = LogManager.getLogger(Pride.class);
    public static final String MODID = "pride";
    private static final String PROTOCOL_VERSION = "1";
    public static final String MOD_ID = "pride";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public Pride() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListnerEvent);
        SoundsRegister.SOUNDS.register(bus);
        ItemsRegister.REGISTRY.register(bus);
        EntityRegister.ENTITIES.register(bus);
        EntityRegister.SPECIAL_ENTITIES.register(bus);
        MinecraftForge.EVENT_BUS.addListener(this::onDatapackSync);
        AttributeRegister.register(bus);
        SchoolRegister.register(bus);
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
        bus.addListener(this::doCommonStuff);
    }

    private void registerCommands(final RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal(Pride.MOD_ID)
                        .requires(source -> source.hasPermission(2))
                        .then(Commands.argument("living_entity", EntityArgument.entity())
                                .then(PerilousCommand.register())
                                .then(MobEatCommand.register())
                                .then(AddXpCommand.register())
                                .then(ShootProjectileCommand.register())
                                .then(SkillExecuteCommand.register())
                                .then(CastSpellCommand.register())
                                .then(ResetLevelCommand.register())
                                .then(DialogueCommand.register())
                                .then(SetElementCommand.register())));
    }

    private void addReloadListnerEvent(final AddReloadListenerEvent event) {
        event.addListener(new PrideMobPatchReloader());
    }

    private void doCommonStuff(FMLCommonSetupEvent event) {
        EpicFightNetworkManager.INSTANCE.registerMessage(99, com.nameless.indestructible.server.network.SPDatapackSync.class, com.nameless.indestructible.server.network.SPDatapackSync::toBytes, com.nameless.indestructible.server.network.SPDatapackSync::fromBytes, com.nameless.indestructible.server.network.SPDatapackSync::handle);
        EpicFightNetworkManager.INSTANCE.registerMessage(98, SPCancelBossInfo.class, SPCancelBossInfo::toBytes, SPCancelBossInfo::fromBytes, SPCancelBossInfo::handle);
    }

    private void onDatapackSync(final OnDatapackSyncEvent event) {
        ServerPlayer player = event.getPlayer();
        if (player != null) {
            if (!player.getServer().isSingleplayerOwner(player.getGameProfile())) {
                com.nameless.indestructible.server.network.SPDatapackSync mobPatchPacket = new com.nameless.indestructible.server.network.SPDatapackSync(PrideMobPatchReloader.getTagCount());
                PrideMobPatchReloader.getDataStream().forEach(mobPatchPacket::write);
                EpicFightNetworkManager.sendToPlayer(mobPatchPacket, player);
            }
        } else {
            event.getPlayerList().getPlayers().forEach((serverPlayer) -> {
                com.nameless.indestructible.server.network.SPDatapackSync mobPatchPacket = new com.nameless.indestructible.server.network.SPDatapackSync(PrideMobPatchReloader.getTagCount());
                PrideMobPatchReloader.getDataStream().forEach(mobPatchPacket::write);
                EpicFightNetworkManager.sendToPlayer(mobPatchPacket, serverPlayer);
            });
        }
    }

    private void setupCommon(FMLCommonSetupEvent event) {
        PacketRegister.register();
        event.enqueueWork(() -> {
            DialogueConditionsRegister.register();
            ActionsRegister.register();
            StructureRegister.register();
            BiomeSettingsManager.register();
        });
    }

    private void setupClient(FMLClientSetupEvent event) {
        KeyRegister.setupClient(event);
        ItemPredicateRegister.registerPredicates(event);
    }
}
