package com.robson.pride.registries;

import com.robson.pride.keybinding.KeyActionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketRegister {

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("pride", "network_channel"),
            () -> "1.0",
            s -> true,
            s -> true
    );

    public static void register(){
        int id = 0;
        CHANNEL.registerMessage(id++, KeyActionPacket.class, KeyActionPacket::encode, KeyActionPacket::decode, KeyActionPacket::handle);
    }

    public static void sendKeyToServer(KeyActionPacket packet) {
        CHANNEL.sendToServer(packet);
    }
}