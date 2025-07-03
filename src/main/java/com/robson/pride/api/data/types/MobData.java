package com.robson.pride.api.data.types;

import com.robson.pride.api.ai.combat.ActionsBuilder;
import com.robson.pride.api.entity.PrideMob;
import net.minecraft.resources.ResourceLocation;

public abstract class MobData {

    private final ResourceLocation texture;

    private final ActionsBuilder combatActions;

    protected MobData(ResourceLocation texture, ActionsBuilder combatActions) {
        this.texture = texture;
        this.combatActions = combatActions;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ActionsBuilder getCombatActions() {
        return combatActions;
    }

    public abstract void onSpawn(PrideMob mob);

}
