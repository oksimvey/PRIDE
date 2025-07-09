package com.robson.pride.api.data.manager;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.TagParser;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CompressServerData{

    public static void main(String[] args) throws IOException {
            Path inputDir = Paths.get("src/main/json_data");
            Path outputDir = Paths.get("src/main/resources/pride_data");
            compress(inputDir, outputDir);
    }

    public static void compress(Path inputDir, Path outputDir) throws IOException {
        if (inputDir == null || !Files.isDirectory(inputDir)) {
            throw new IllegalArgumentException("invalid input directory: " + inputDir);
        }

        if (outputDir == null) {
            throw new IllegalArgumentException("invalid output directory:");
        }

        Files.createDirectories(outputDir);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDir, "*.json")) {
            for (Path jsonPath : stream) {
                try {
                    compressFile(jsonPath, outputDir);
                } catch (Exception e) {
                    System.err.println("⚠️ error processing " + jsonPath + ": " + e.getMessage());
                }
            }
        }
    }

    public static void compressFile(Path jsonPath, Path outputDir) throws IOException, CommandSyntaxException {
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
    }
}
