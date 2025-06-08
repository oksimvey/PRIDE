package com.robson.pride.registries;

import com.mojang.blaze3d.platform.InputConstants;
import com.robson.pride.keybinding.KeyActionPacket;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class KeyRegister {
    public static KeyMapping keyActionSpecial;
    public static KeyMapping keyActionSecondary;
    public static KeyMapping keyActionTertiary;
    public static KeyMapping keyActionJump;
    public static KeyMapping keyActionSwapHand;
    public static KeyMapping keyActionAura;
    public static KeyMapping keyActionImbuement;
    public static KeyMapping keyActionImmunity;
    public static KeyMapping keyActionAwakening;
    public static KeyMapping keyActionMobility;

    private static boolean wasPressedSpecial = false;
    private static boolean wasPressedSecondary = false;
    private static boolean wasPressedTertiary = false;
    private static boolean wasPressedJump = false;
    private static boolean wasPressedSwapHand = false;
    private static boolean wasPressedAura = false;
    private static boolean wasPressedImbuement = false;
    private static boolean wasPressedImmunity = false;
    private static boolean wasPressedAwakening = false;
    private static boolean wasPressedMobility = false;

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        keyActionSpecial = new KeyMapping("key.pride.special", InputConstants.Type.MOUSE, 0, "key.categories.misc");
        keyActionSecondary = new KeyMapping("key.pride.menu", InputConstants.Type.KEYSYM, InputConstants.KEY_M, "key.categories.misc");
        keyActionTertiary = new KeyMapping("key.pride.dodge", InputConstants.Type.KEYSYM, InputConstants.KEY_Q, "key.categories.misc");
        keyActionJump = new KeyMapping("key.pride.jump", InputConstants.Type.KEYSYM, InputConstants.KEY_SPACE, "key.categories.misc");
        keyActionSwapHand = new KeyMapping("key.pride.swaphand", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.misc");
        keyActionAura = new KeyMapping("key.pride.aura", InputConstants.Type.KEYSYM, InputConstants.KEY_X, "key.categories.misc");
        keyActionImbuement = new KeyMapping("key.pride.imbuement", InputConstants.Type.KEYSYM, InputConstants.KEY_Z, "key.categories.misc");
        keyActionImmunity = new KeyMapping("key.pride.immunity", InputConstants.Type.KEYSYM, InputConstants.KEY_C, "key.categories.misc");
        keyActionAwakening = new KeyMapping("key.pride.awakening", InputConstants.Type.KEYSYM, InputConstants.KEY_V, "key.categories.misc");
        keyActionMobility = new KeyMapping("key.pride.mobility", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.misc");
        event.register(keyActionSpecial);
        event.register(keyActionSecondary);
        event.register(keyActionTertiary);
        event.register(keyActionJump);
        event.register(keyActionSwapHand);
        event.register(keyActionAura);
        event.register(keyActionImbuement);
        event.register(keyActionImmunity);
        event.register(keyActionAwakening);
        event.register(keyActionMobility);
    }

    public static void setupClient(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.addListener(KeyRegister::onClientTick);
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        checkKeyAction(keyActionSpecial, wasPressedSpecial, "special");
        checkKeyAction(keyActionSecondary, wasPressedSecondary, "menu");
        checkKeyAction(keyActionTertiary, wasPressedTertiary, "dodge");
        checkKeyAction(keyActionJump, wasPressedJump, "jump");
        checkKeyAction(keyActionSwapHand, wasPressedSwapHand, "swaphand");
        checkKeyAction(keyActionAura, wasPressedAura, "aura");
        checkKeyAction(keyActionImbuement, wasPressedImbuement, "imbuement");
        checkKeyAction(keyActionImmunity, wasPressedImmunity, "immunity");
        checkKeyAction(keyActionAwakening, wasPressedAwakening, "awakening");
        checkKeyAction(keyActionMobility, wasPressedMobility, "mobility");
    }

    private static void checkKeyAction(KeyMapping key, boolean wasPressed, String actionName) {
        boolean isCurrentlyPressed = key.isDown();
        if (isCurrentlyPressed && !wasPressed) {
            PacketRegister.sendKeyToServer(new KeyActionPacket(actionName, 0));
        }
        if (key == keyActionSpecial) {
            wasPressedSpecial = isCurrentlyPressed;
        } else if (key == keyActionSecondary) {
            wasPressedSecondary = isCurrentlyPressed;
        } else if (key == keyActionTertiary) {
            wasPressedTertiary = isCurrentlyPressed;
        } else if (key == keyActionJump) {
            wasPressedJump = isCurrentlyPressed;
        } else if (key == keyActionSwapHand) {
            wasPressedSwapHand = isCurrentlyPressed;
        } else if (key == keyActionAura) {
            wasPressedAura = isCurrentlyPressed;
        } else if (key == keyActionImbuement) {
            wasPressedImbuement = isCurrentlyPressed;
        } else if (key == keyActionImmunity) {
            wasPressedImmunity = isCurrentlyPressed;
        } else if (key == keyActionAwakening) {
            wasPressedAwakening = isCurrentlyPressed;
        } else if (key == keyActionMobility) {
            wasPressedMobility = isCurrentlyPressed;
        }
    }
}