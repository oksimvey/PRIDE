package com.robson.pride.api.entity;

import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.world.capability.AdvancedCustomHumanoidMobPatch;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.world.capabilities.entitypatch.Faction;

public class PrideMobPatch<T extends PathfinderMob> extends AdvancedCustomHumanoidMobPatch<T> {

    public AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider provider;

    public PrideMobPatch(Faction faction, AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider provider) {
        super(faction, provider);
        this.provider = provider;
    }

}
