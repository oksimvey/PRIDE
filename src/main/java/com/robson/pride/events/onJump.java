package com.robson.pride.events;

import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.utils.ClientPlayerTagsAcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class onJump {

    @SubscribeEvent
    public static void onJump(LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() != null) {
            if (event.getEntity() instanceof Player player) {
                    MikiriCounter.setMikiri(player, "Jump", 0, 350);
                    CompoundTag playertags = ClientPlayerTagsAcessor.playerTags.get(player);
                    if (playertags != null) {
                        int dexlevel = playertags.getInt("DexterityLvl");
                        player.addDeltaMovement(new Vec3(0, dexlevel / 1000f, 0));
                    }
                }
        }
    }
}
