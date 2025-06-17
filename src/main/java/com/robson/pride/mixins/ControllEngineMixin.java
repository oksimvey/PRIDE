package com.robson.pride.mixins;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillSlot;

@Mixin(ControllEngine.class)
@OnlyIn(Dist.CLIENT)
public abstract class ControllEngineMixin {

    @Shadow private LocalPlayerPatch playerpatch;

    @Shadow private LocalPlayer player;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private KeyMapping currentChargingKey;

    @Shadow public Options options;

    @Shadow private boolean attackLightPressToggle;

    @Shadow protected abstract void releaseAllServedKeys();

    @Shadow protected abstract void reserveKey(SkillSlot slot, KeyMapping keyMapping);

    @Shadow public abstract void lockHotkeys();

    @Shadow private boolean weaponInnatePressToggle;

    @Shadow private int weaponInnatePressCounter;

    @Shadow private boolean sneakPressToggle;

    @Shadow private boolean moverPressToggle;


    /**
     * @author
     * @reason
     */

}
