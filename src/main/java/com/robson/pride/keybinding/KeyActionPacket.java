package com.robson.pride.keybinding;

import com.mojang.blaze3d.shaders.Effect;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.effect.ImbuementEffect;
import com.robson.pride.progression.ProgressionGUI;
import com.robson.pride.registries.EffectRegister;
import com.robson.pride.skills.special.KillerAuraSkill;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Supplier;

public class KeyActionPacket {

    private final String action;
    private final long pressDuration;


    public KeyActionPacket(String action, long pressDuration) {
        this.action = action;
        this.pressDuration = pressDuration;
    }


    public static void encode(KeyActionPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.action);
        buf.writeLong(packet.pressDuration);
    }

    public static KeyActionPacket decode(FriendlyByteBuf buf) {
        return new KeyActionPacket(buf.readUtf(), buf.readLong());
    }

    public static void handle(KeyActionPacket packet, Supplier<NetworkEvent.Context> context) {

        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player != null) {

                executeonServer(player, packet);
            }
        });

        context.get().setPacketHandled(true);
    }

    private static void executeonServer(Player player, KeyActionPacket packet) {

        if (packet.action.equals("special")) {
            OnLeftClick.onLClick(player);
        }

        if (packet.action.equals("immunity")){
            if (player.hasEffect(EffectRegister.IMMUNITY.get())){
                player.removeEffect(EffectRegister.IMMUNITY.get());
            }
            else player.addEffect(new MobEffectInstance(EffectRegister.IMMUNITY.get(), 999999999));
        }
        if (packet.action.equals("swaphand")) {
            onFPress.swapHand(player);
        }

        if (packet.action.equals("dodge")) {
            if (!(player.getPersistentData().getBoolean("isDodging")))
                onQPress.checkDodgeType(player);
        }

        if (packet.action.equals("jump")) {
            onSpacePress.onPress(player);
        }
        if (packet.action.equals("aura")){
            KillerAuraSkill.skillStart(player);
        }
        if (packet.action.equals("imbuement")){
            if (!player.hasEffect(EffectRegister.IMBUEMENT.get())) {
                player.addEffect(new MobEffectInstance(EffectRegister.IMBUEMENT.get(), 999999999));
                if (player.getEffect(EffectRegister.IMBUEMENT.get()).getEffect() instanceof ImbuementEffect imbuementEffect){
                    imbuementEffect.setElement(ElementalUtils.getElement(player));
                }
            }
            else player.removeEffect(EffectRegister.IMBUEMENT.get());
        }

            if (packet.action.equals("menu")) {
                if (player != null) {
                    BlockPos _bpos = player.getOnPos();
                    NetworkHooks.openScreen((ServerPlayer) player, new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return Component.literal("ProgressionGUI");
                        }

                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                            return new ProgressionGUI(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(_bpos));
                        }
                    }, _bpos);
                }
            }
        }
    }