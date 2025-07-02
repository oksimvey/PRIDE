package com.robson.pride.registries;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;

public class KeyRegister {

    public static KeyMapping SPECIAL;
    public static KeyMapping DODGE;
    public static KeyMapping MENU;
    public static KeyMapping SWAP_HAND;
    public static KeyMapping KILLER_AURA;
    public static KeyMapping IMBUEMENT;
    public static KeyMapping IMMUNITY;
    public static KeyMapping AWAKENING;
    public static KeyMapping MOBILITY;
    public static KeyMapping MOUNT;

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        SPECIAL = new KeyMapping("key.pride.special", InputConstants.Type.MOUSE, 0, "key.categories.misc");
        MENU = new KeyMapping("key.pride.menu", InputConstants.Type.KEYSYM, InputConstants.KEY_M, "key.categories.misc");
        DODGE = new KeyMapping("key.pride.dodge", InputConstants.Type.KEYSYM, InputConstants.KEY_Q, "key.categories.misc");
        SWAP_HAND = new KeyMapping("key.pride.swaphand", InputConstants.Type.KEYSYM, InputConstants.KEY_G, "key.categories.misc");
         IMBUEMENT = new KeyMapping("key.pride.imbuement", InputConstants.Type.KEYSYM, InputConstants.KEY_Z, "key.categories.misc");
        IMMUNITY = new KeyMapping("key.pride.immunity", InputConstants.Type.KEYSYM, InputConstants.KEY_X, "key.categories.misc");
        KILLER_AURA = new KeyMapping("key.pride.aura", InputConstants.Type.KEYSYM, InputConstants.KEY_C, "key.categories.misc");
        AWAKENING = new KeyMapping("key.pride.awakening", InputConstants.Type.KEYSYM, InputConstants.KEY_V, "key.categories.misc");
        MOBILITY = new KeyMapping("key.pride.mobility", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.misc");
        MOUNT = new KeyMapping("key.pride.mount", InputConstants.Type.KEYSYM, InputConstants.KEY_H, "key.categories.misc");
        event.register(MOUNT);
        event.register(SPECIAL);
        event.register(DODGE);
        event.register(MENU);
        event.register(SWAP_HAND);
        event.register(KILLER_AURA);
        event.register(IMBUEMENT);
        event.register(IMMUNITY);
        event.register(AWAKENING);
        event.register(MOBILITY);
    }
}