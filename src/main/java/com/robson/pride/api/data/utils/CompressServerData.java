package com.robson.pride.api.data.utils;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robson.pride.api.data.manager.DataFileManager;
import net.minecraft.nbt.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CompressServerData{

    private static final String INPUT_DIR = "src/main/json_data";

    private static final String OUTPUT_DIR = "src/main/resources/pride_data";

    public static void main(String[] args) throws IOException {
       compressWeaponData();
    }

    private static void compressBiomesData() throws IOException {
        compress(DataFileManager.BIOMES);
    }

    private static void compressEntitiesData() throws IOException {
        compress(DataFileManager.ENTITIES);
    }

    private static void compressItemsData() throws IOException {
        compress(DataFileManager.ITEMS);
    }

    private static void compressWeaponSkillData() throws IOException {
        compress(DataFileManager.WEAPON_SKILLS);
    }

    private static void compressWeaponData() throws IOException {
        compress(DataFileManager.WEAPONS);
    }

    private static void compress(String datatype) throws IOException {
        ListTag registries = new ListTag();
        Path inputDir = Paths.get(INPUT_DIR + "/" + datatype);
        Path outputDir = Paths.get(OUTPUT_DIR + "/" + datatype);
        if (!Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("invalid input directory: " + inputDir);
        }

        Files.createDirectories(outputDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir, "*.json")) {
            for (Path jsonPath : stream) {
                try {
                    registries.add(StringTag.valueOf(compressFile(jsonPath, outputDir)));
                } catch (Exception e) {
                    System.err.println("⚠️ error processing " + jsonPath + ": " + e.getMessage());
                }
            }
        }

        Path registryPath = Path.of(OUTPUT_DIR).resolve(datatype + "_registries.dat");

        try (OutputStream out = Files.newOutputStream(registryPath)) {
            CompoundTag registriesTag = new CompoundTag();
            registriesTag.put("registries", registries);
            NbtIo.writeCompressed(registriesTag, out);
        }
    }

    private static String compressFile(Path jsonPath, Path outputDir) throws IOException, CommandSyntaxException {
        if (jsonPath == null || !Files.exists(jsonPath)) {
            throw new IllegalArgumentException("invalid json file: " + jsonPath);
        }

        JsonElement jsonElement;
        try (Reader reader = Files.newBufferedReader(jsonPath)) {
            jsonElement = JsonParser.parseReader(reader);
        }

        if (jsonElement == null || jsonElement.isJsonNull()) {
            throw new IOException("empty or invalid json: " + jsonPath);
        }

        CompoundTag tag = TagParser.parseTag(jsonElement.toString());

        String filename = jsonPath.getFileName().toString().replace(".json", ".dat");
        Path outPath = outputDir.resolve(filename);

        try (OutputStream out = Files.newOutputStream(outPath)) {
            NbtIo.writeCompressed(tag, out);
        }
        return filename;
    }
}
