package com.robson.pride.mixins;

import com.robson.pride.api.utils.ServerTask;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {

    @Inject(at = @At(value = "TAIL"), method = "tick", remap = false)
    public void callEvent(BooleanSupplier p_8794_, CallbackInfo ci) {
        ServerTask.runTasks();
    }
}
