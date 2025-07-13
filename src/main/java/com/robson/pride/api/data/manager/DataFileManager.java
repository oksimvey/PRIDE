package com.robson.pride.api.data.manager;

import com.robson.pride.main.Pride;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataFileManager {

    public static final String BIOMES = "biomes";

    public static final String EQUIPMENT = "equipment";

    public static final String PARTICLES = "particles";

    public static final String ENTITIES = "entities";

    public static final String ITEMS = "items";

    public static final String WEAPON_SKILLS = "weapon_skills";

    public static final String WEAPONS = "weapons";

    public static final List<String> ALL_DATA = List.of(WEAPONS);

    public static final Path OUTPUT = Minecraft.getInstance().gameDirectory.toPath().resolve("pride_data");

    public static final Path DYNAMIC_MAP_OUTPUT = OUTPUT.resolve("dynamic");

    public static boolean validItem(String datatype, String id) {
        return id != null && !id.isEmpty();
    }

    public static CompoundTag getBiomeData(String id) {
        return getGenericData(id, BIOMES);
    }

    public static CompoundTag getEntityData(String id) {
        return getGenericData(id, ENTITIES);
    }

    public static CompoundTag getWeaponSkillData(String id) {
        return getGenericData(id, WEAPON_SKILLS);
    }

    public static CompoundTag getGenericItemData(String id) {
       return getGenericData(id, ITEMS);
    }

    public static CompoundTag getWeaponData(String id) {
       return getGenericData(id, WEAPONS);
    }

    private static CompoundTag getGenericData(String id, String dataType){
        Path file = OUTPUT.resolve(dataType + "/" + id + ".dat");
        try {
            InputStream data = Files.newInputStream(file);
            try {
                return NbtIo.readCompressed(data);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> getRegistries(String dataType) {
        List<String> registries = new ArrayList<>();
        InputStream registriesfile = readJarFile("pride_data/" + dataType + "_registries.dat");
        try {
            CompoundTag registriesnbt = NbtIo.readCompressed(registriesfile);
            if (registriesnbt.contains("registries")) {
                ListTag list = registriesnbt.getList("registries", Tag.TAG_STRING);
                for (int i = 0; i < list.size(); ++i) {
                    registries.add(list.getString(i));
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return registries;
    }


    @OnlyIn(Dist.CLIENT)
    public static void registerModels(ModelEvent.RegisterAdditional event) {
        for (String dataType : ALL_DATA) {
            for (String path : getRegistries(dataType)) {
                InputStream data = readJarFile("pride_data/" + dataType + "/" + path);
                try {
                    CompoundTag datanbt = NbtIo.readCompressed(data);
                    if (datanbt.contains("model")) {
                        event.register(new ResourceLocation(datanbt.getString("model")));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void writeToInstance() {
        try {
            Files.createDirectories(OUTPUT);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.createDirectories(DYNAMIC_MAP_OUTPUT);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(DYNAMIC_MAP_OUTPUT, "*.dat")) {
            for (Path file : stream) {
                Files.deleteIfExists(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String dataType : ALL_DATA) {
            for (String path : getRegistries(dataType)) {
                InputStream data = readJarFile("pride_data/" + dataType + "/" + path);
                try {
                    CompoundTag datanbt = NbtIo.readCompressed(data);
                    Path outputPath = OUTPUT.resolve(dataType + "/" + path);
                    try {
                        Files.createDirectories(outputPath.getParent());
                        try {
                            NbtIo.writeCompressed(datanbt, outputPath.toFile());
                        }
                        catch (IOException e) {
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
