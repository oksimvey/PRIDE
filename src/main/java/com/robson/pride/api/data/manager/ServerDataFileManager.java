package com.robson.pride.api.data.manager;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.io.InputStream;

public class ServerDataFileManager {

    ResourceLocation rl = new ResourceLocation("pride", "server/data/presets/ronin1.dat");
    InputStream stream = (InputStream) Minecraft.getInstance()
            .getResourceManager()
            .getResource(rl)
            .stream();

    private final String mobs = "data/mobs";

    private final String weapons = "data/weapons";

    private final String items = "data/items";


}
