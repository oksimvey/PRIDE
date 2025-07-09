package com.robson.pride.api.data.manager;

import com.robson.pride.main.Pride;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ServerDataFileManager {

    public static final String BIOMES_DATA = "biomes";

    public static final String ENTITIES = "entities";

    public static final String ITEMS = "items";

    public static final String WEAPON_SKILLS = "weapon_skills";

    public static final String WEAPONS = "weapons";


    public static List<CompoundTag> readAll(String dataType) {
        List<CompoundTag> tags = new ArrayList<>();
        CompoundTag registries = readFile("pride_data/" + dataType + "_registries.dat");
        if (registries.contains("registries")) {
            ListTag list = registries.getList("registries", Tag.TAG_STRING);
            for (int i = 0; i < list.size(); ++i) {
                String path = list.getString(i);
                CompoundTag tag = readFile("pride_data/" + dataType + "/" + path);
                tags.add(tag);
            }
        }
        return tags;
    }

    public static CompoundTag readFile(String pathInJar){
        try (InputStream in = Pride.class.getClassLoader().getResourceAsStream(pathInJar)) {
            if (in == null) throw new RuntimeException("could not find the file: " + pathInJar);
            return NbtIo.readCompressed(in);

        } catch (IOException e) {
            throw new RuntimeException("error reading the nbt: " + pathInJar, e);
        }
    }

    private final String mobs = "data/mobs";

    private final String weapons = "data/weapons";

    private final String items = "data/items";


}
