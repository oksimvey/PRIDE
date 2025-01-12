package com.robson.pride.progression;

import com.robson.pride.main.Pride;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class PlayerProgressionData {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        Pride.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new, PlayerVariablesSyncMessage::handler);
    }

    @SubscribeEvent
    public static void init(RegisterCapabilitiesEvent event) {
        event.register(PlayerVariables.class);
    }

    @Mod.EventBusSubscriber
    public static class EventBusVariableHandlers {
        @SubscribeEvent
        public static void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
            if (!event.getEntity().level().isClientSide())
                ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables())).syncPlayerVariables(event.getEntity());
        }

        @SubscribeEvent
        public static void clonePlayer(PlayerEvent.Clone event) {
            event.getOriginal().revive();
            PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
            PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
            clone.StrengthLvl = original.StrengthLvl;
            clone.DexterityLvl = original.DexterityLvl;
            clone.VigorLvl = original.VigorLvl;
            clone.EnduranceLvl = original.EnduranceLvl;
            clone.MindLvl = original.MindLvl;
            clone.StrengthXp = original.StrengthXp;
            clone.DexterityXp = original.DexterityXp;
            clone.VigorXp = original.VigorXp;
            clone.EnduranceXp = original.EnduranceXp;
            clone.MindXp = original.MindXp;
            clone.StrengthMaxXp = original.StrengthMaxXp;
            clone.DexterityMaxXp = original.DexterityMaxXp;
            clone.VigorMaxXp = original.VigorMaxXp;
            clone.EnduranceMaxXp = original.EnduranceMaxXp;
            clone.MindMaxXp = original.MindMaxXp;
            clone.Element = original.Element;
        }
    }

    public static final Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = CapabilityManager.get(new CapabilityToken<PlayerVariables>() {
    });

    @Mod.EventBusSubscriber
    private static class PlayerVariablesProvider implements ICapabilitySerializable<Tag> {
        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer))
                event.addCapability(new ResourceLocation("pride", "player_variables"), new PlayerVariablesProvider());
        }

        private final PlayerVariables playerVariables = new PlayerVariables();
        private final LazyOptional<PlayerVariables> instance = LazyOptional.of(() -> playerVariables);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public Tag serializeNBT() {
            return playerVariables.writeNBT();
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            playerVariables.readNBT(nbt);
        }
    }

    public static class PlayerVariables {
        private int StrengthLvl = 1;
        private int DexterityLvl = 1;
        private int VigorLvl = 1;
        private int EnduranceLvl = 1;
        private int MindLvl = 1;
        private int StrengthXp = 0;
        private int DexterityXp = 0;
        private int VigorXp = 0;
        private int EnduranceXp = 0;
        private int MindXp = 0;
        private int StrengthMaxXp = 100;
        private int DexterityMaxXp = 100;
        private int VigorMaxXp = 100;
        private int EnduranceMaxXp = 100;
        private int MindMaxXp = 100;
        private String Element = "";

        public int[] addXp(int lvl, int xp, int maxxp, int amount) {
            xp = xp + amount;
            if (xp >= maxxp) {
                xp = xp - maxxp;
                lvl = lvl + 1;
                maxxp = setMaxXp(lvl);
                if (lvl >= 100) {
                    lvl = 100;
                    xp = 0;
                    maxxp = 0;
                }
            }
            return new int[]{lvl, xp, maxxp};
        }

        public int setMaxXp(int StatLvl){
            return (int) Math.round(100 * Math.pow(1.05, (StatLvl - 1)));
        }

        public int getTotalLvl(){
            return ((StrengthLvl + DexterityLvl + VigorLvl + EnduranceLvl + MindLvl) / 5);
        }

        public int getStrengthLvl(){
            return StrengthLvl;
        }

        public int getDexterityLvl(){
            return DexterityLvl;
        }

        public int getVigorLvl(){
            return VigorLvl;
        }

        public int getEnduranceLvl(){
            return EnduranceLvl;
        }

        public int getMindLvl(){
            return MindLvl;
        }

        public String getStrengthXp(){
            return(StrengthXp + "/" + StrengthMaxXp);
        }

        public String getDexterityXp(){
            return(DexterityXp + "/" + DexterityMaxXp);
        }

        public String getVigorXp(){
            return(VigorXp + "/" + VigorMaxXp);
        }

        public String getEnduranceXp(){
            return(EnduranceXp + "/" + EnduranceMaxXp);
        }

        public String getMindXp(){
            return(MindXp + "/" + MindMaxXp);
        }

        public void addStrengthXp(int amount) {
            StrengthLvl = addXp(StrengthLvl, StrengthXp, StrengthMaxXp, amount)[0];
            StrengthXp = addXp(StrengthLvl, StrengthXp, StrengthMaxXp, amount)[1];
            StrengthMaxXp = addXp(StrengthLvl, StrengthXp, StrengthMaxXp, amount)[2];
        }

        public void addDexterityXp(int amount) {
            DexterityLvl = addXp(DexterityLvl, DexterityXp, DexterityMaxXp, amount)[0];
            DexterityXp = addXp(DexterityLvl, DexterityXp, DexterityMaxXp, amount)[1];
            DexterityMaxXp = addXp(DexterityLvl, DexterityXp, DexterityMaxXp, amount)[2];
        }

        public void addVigorXp(int amount) {
            VigorLvl = addXp(VigorLvl, VigorXp, VigorMaxXp, amount)[0];
            VigorXp = addXp(VigorLvl, VigorXp, VigorMaxXp, amount)[1];
            VigorMaxXp = addXp(VigorLvl, VigorXp, VigorMaxXp, amount)[2];
        }

        public void addEnduranceXp(int amount) {
            EnduranceLvl = addXp(EnduranceLvl, EnduranceXp, EnduranceMaxXp, amount)[0];
            EnduranceXp = addXp(EnduranceLvl, EnduranceXp, EnduranceMaxXp, amount)[1];
            EnduranceMaxXp = addXp(EnduranceLvl, EnduranceXp, EnduranceMaxXp, amount)[2];
        }


        public void addMindXp(int amount) {
            MindLvl = addXp(MindLvl, MindXp, MindMaxXp, amount)[0];
            MindXp = addXp(MindLvl, MindXp, MindMaxXp, amount)[1];
            MindMaxXp = addXp(MindLvl, MindXp, MindMaxXp, amount)[2];
        }

        public void syncPlayerVariables(Entity entity) {
            if (entity instanceof ServerPlayer serverPlayer)
                Pride.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new PlayerVariablesSyncMessage(this));
        }

        public String getElement(){
            return Element;
        }

        public void setElement(String element){
            Element =  element;
        }

        public Tag writeNBT() {
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("StrengthLvl", StrengthLvl);
            nbt.putInt("DexterityLvl", DexterityLvl);
            nbt.putInt("VigorLvl", VigorLvl);
            nbt.putInt("EnduranceLvl", EnduranceLvl);
            nbt.putInt("MindLvl", MindLvl);
            nbt.putInt("StrengthXp", StrengthXp);
            nbt.putInt("DexterityXp", DexterityXp);
            nbt.putInt("VigorXp", VigorXp);
            nbt.putInt("EnduranceXp", EnduranceXp);
            nbt.putInt("MindXp", MindXp);
            nbt.putInt("StrengthMaxXp", StrengthMaxXp);
            nbt.putInt("DexterityMaxXp", DexterityMaxXp);
            nbt.putInt("VigorMaxXp", VigorMaxXp);
            nbt.putInt("EnduranceMaxXp", EnduranceMaxXp);
            nbt.putInt("MindMaxXp", MindMaxXp);
            nbt.putString("Element", Element);
            return nbt;
        }

        public void readNBT(Tag tag) {
            CompoundTag nbt = (CompoundTag) tag;
            StrengthLvl = nbt.getInt("StrengthLvl");
            DexterityLvl = nbt.getInt("DexterityLvl");
            VigorLvl = nbt.getInt("VigorLvl");
            EnduranceLvl = nbt.getInt("EnduranceLvl");
            MindLvl = nbt.getInt("MindLvl");
            StrengthXp = nbt.getInt("StrengthXp");
            DexterityXp = nbt.getInt("DexterityXp");
            VigorXp = nbt.getInt("VigorXp");
            EnduranceXp = nbt.getInt("EnduranceXp");
            MindXp = nbt.getInt("MindXp");
            StrengthMaxXp = nbt.getInt("StrengthMaxXp");
            DexterityMaxXp = nbt.getInt("DexterityMaxXp");
            VigorMaxXp = nbt.getInt("VigorMaxXp");
            EnduranceMaxXp = nbt.getInt("EnduranceMaxXp");
            MindMaxXp = nbt.getInt("MindMaxXp");
            Element = nbt.getString("Element");
        }
    }

    public static class PlayerVariablesSyncMessage {
        private final PlayerVariables data;

        public PlayerVariablesSyncMessage(FriendlyByteBuf buffer) {
            this.data = new PlayerVariables();
            this.data.readNBT(buffer.readNbt());
        }

        public PlayerVariablesSyncMessage(PlayerVariables data) {
            this.data = data;
        }

        public static void buffer(PlayerVariablesSyncMessage message, FriendlyByteBuf buffer) {
            buffer.writeNbt((CompoundTag) message.data.writeNBT());
        }

        public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                if (!context.getDirection().getReceptionSide().isServer()) {
                    PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
                    variables.StrengthLvl = message.data.StrengthLvl;
                    variables.DexterityLvl = message.data.DexterityLvl;
                    variables.VigorLvl = message.data.VigorLvl;
                    variables.EnduranceLvl = message.data.EnduranceLvl;
                    variables.MindLvl = message.data.MindLvl;
                    variables.StrengthXp = message.data.StrengthXp;
                    variables.DexterityXp = message.data.DexterityXp;
                    variables.VigorXp = message.data.VigorXp;
                    variables.EnduranceXp = message.data.EnduranceXp;
                    variables.MindXp = message.data.MindXp;
                    variables.StrengthMaxXp = message.data.StrengthMaxXp;
                    variables.DexterityMaxXp = message.data.DexterityMaxXp;
                    variables.VigorMaxXp = message.data.VigorMaxXp;
                    variables.EnduranceMaxXp = message.data.EnduranceMaxXp;
                    variables.MindMaxXp = message.data.MindMaxXp;
                    variables.Element = message.data.Element;
                }
            });
            context.setPacketHandled(true);
        }
    }
}
