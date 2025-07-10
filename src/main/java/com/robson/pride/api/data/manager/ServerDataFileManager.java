package com.robson.pride.api.data.manager;

import com.robson.pride.main.Pride;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ServerDataFileManager {

    public static final String BIOMES_DATA = "biomes";

    public static final String ENTITIES = "entities";

    public static final String ITEMS = "items";

    public static final String WEAPON_SKILLS = "weapon_skills";

    public static final String WEAPONS = "weapons";

    public static final List<String> ALL_DATA = List.of(WEAPONS);

    public static final Path output = Minecraft.getInstance().gameDirectory.toPath().resolve("pride_data");

    public static CompoundTag getRegistries(String datatype){
        Path file = output.resolve(datatype + "_registries.dat");
        try {
            InputStream data = Files.newInputStream(file);
            try {
                return NbtIo.readCompressed(data);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static CompoundTag getWeaponData(String id){
        Path file = output.resolve(WEAPONS + "/" + id + ".dat");
        try {
            InputStream data = Files.newInputStream(file);
            try {
                return NbtIo.readCompressed(data);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelEvent.RegisterAdditional event){
        for (String dataType : ALL_DATA) {
            InputStream registries = readJarFile("pride_data/" + dataType + "_registries.dat");
            try {
                CompoundTag registriesnbt = NbtIo.readCompressed(registries);
                if (registriesnbt.contains("registries")) {
                    ListTag list = registriesnbt.getList("registries", Tag.TAG_STRING);
                    for (int i = 0; i < list.size(); ++i) {
                        String path = list.getString(i);
                        InputStream data = readJarFile("pride_data/" + dataType + "/" + path);
                        try {
                            CompoundTag datanbt = NbtIo.readCompressed(data);
                            if (datanbt.contains("model")) {
                                event.register(new ResourceLocation(datanbt.getString("model")));
                            }
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void writeToInstance(){
        try {
            Files.createDirectories(output);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (String dataType : ALL_DATA) {
            InputStream registries = readJarFile("pride_data/" + dataType + "_registries.dat");
            try {
                CompoundTag registriesnbt = NbtIo.readCompressed(registries);
                if (registriesnbt.contains("registries")) {
                    ListTag list = registriesnbt.getList("registries", Tag.TAG_STRING);
                    for (int i = 0; i < list.size(); ++i) {
                        String path = list.getString(i);
                        InputStream data = readJarFile("pride_data/" + dataType + "/" + path);
                        try {
                            CompoundTag datanbt = NbtIo.readCompressed(data);
                            Path outputPath = output.resolve(dataType + "/" + path);
                            try {
                                Files.createDirectories(outputPath.getParent());
                                try {
                                    NbtIo.writeCompressed(datanbt, outputPath.toFile());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                try {
                    NbtIo.writeCompressed(registriesnbt, output.resolve(dataType + "_registries.dat").toFile());
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static InputStream readJarFile(String pathInJar){
        try (InputStream in = Pride.class.getClassLoader().getResourceAsStream(pathInJar)) {
            if (in == null) throw new RuntimeException("could not find the file: " + pathInJar);
            return in;

        } catch (IOException e) {
            throw new RuntimeException("error reading the nbt: " + pathInJar, e);
        }
    }
}
