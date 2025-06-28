package com.robson.pride.api.data.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface ClientDataManager {

    ConcurrentHashMap<Player, ClientData> CLIENT_DATA_MANAGER = new ConcurrentHashMap<>();

    String CLIENT_DATA = "pride_data";


    static Path getPlayerCustomDatFile(ServerPlayer player) {
        MinecraftServer server = player.getServer();
        UUID uuid = player.getUUID();

        return server.getWorldPath(LevelResource.PLAYER_DATA_DIR).resolve(uuid + "." + CLIENT_DATA + ".dat");
    }

    static void savePlayerDat(ServerPlayer player, CompoundTag tag) {
        Path file = getPlayerCustomDatFile(player);
        try (OutputStream out = Files.newOutputStream(file)) {
            NbtIo.writeCompressed(tag, out);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static CompoundTag readPlayerDat(ServerPlayer player) {
        Path file = getPlayerCustomDatFile(player);
        if (!Files.exists(file)) return new CompoundTag();
        try (InputStream in = Files.newInputStream(file)) {
            return NbtIo.readCompressed(in);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new CompoundTag();
        }
    }


    static void deletePlayerDat(ServerPlayer player) {
        Path file = getPlayerCustomDatFile(player);
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
