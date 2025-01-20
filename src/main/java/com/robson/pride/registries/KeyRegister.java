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
    public static KeyMapping keyActionRecharge;

    private static boolean wasPressedSpecial = false;
    private static boolean wasPressedSecondary = false;
    private static boolean wasPressedTertiary = false;
    private static boolean wasPressedJump = false;
    private static boolean wasPressedSwapHand = false;
    private static boolean wasPressedRecharge = false;

    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        keyActionSpecial = new KeyMapping("key.pride.special", InputConstants.Type.MOUSE, 0, "key.categories.misc");
        keyActionSecondary = new KeyMapping("key.pride.menu", InputConstants.Type.KEYSYM, InputConstants.KEY_M, "key.categories.misc");
        keyActionTertiary = new KeyMapping("key.pride.dodge", InputConstants.Type.KEYSYM, InputConstants.KEY_Q, "key.categories.misc");
        keyActionJump = new KeyMapping("key.pride.jump", InputConstants.Type.KEYSYM, InputConstants.KEY_SPACE, "key.categories.misc");
        keyActionSwapHand = new KeyMapping("key.pride.swaphand", InputConstants.Type.KEYSYM, InputConstants.KEY_F, "key.categories.misc");
        keyActionRecharge = new KeyMapping("key.pride.recharge", InputConstants.Type.KEYSYM, InputConstants.KEY_R, "key.categories.misc");

        event.register(keyActionSpecial);
        event.register(keyActionSecondary);
        event.register(keyActionTertiary);
        event.register(keyActionJump);
        event.register(keyActionSwapHand);
        event.register(keyActionRecharge);
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
        checkKeyAction(keyActionRecharge, wasPressedRecharge, "recharge");
    }

    private static void checkKeyAction(KeyMapping key, boolean wasPressed, String actionName) {
        boolean isCurrentlyPressed = key.isDown();

        if (isCurrentlyPressed && !wasPressed) {

            PacketRegister.sendToServer(new KeyActionPacket(actionName, 0));
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
        } else if (key == keyActionRecharge) {
            wasPressedRecharge = isCurrentlyPressed;
        }
    }
}