package com.robson.pride.events;

import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.player.ClientSavedData;
import com.robson.pride.api.mechanics.MikiriCounter;
import com.robson.pride.api.utils.TagsUtils;
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
                byte dex = ClientDataManager.CLIENT_DATA_MANAGER.get(player).getProgressionData().getLevel(ClientSavedData.Dexterity);
                    player.addDeltaMovement(new Vec3(0, dex/ 1000f, 0));
            }
        }
    }
}
