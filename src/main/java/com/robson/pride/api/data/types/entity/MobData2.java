package com.robson.pride.api.data.types.entity;

import com.robson.pride.api.ai.actions.builder.ActionsBuilder;
import com.robson.pride.api.entity.PrideMob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Map;

public abstract class MobData2 {

    private final ResourceLocation texture;

    private final ActionsBuilder combatActions;

    private final Map<EquipmentSlot, ItemStack> equipment;

    private final boolean humanoid;

    private final Armature armature;

    public final Map<StunType, AnimationManager.AnimationAccessor<? extends StaticAnimation>> stunMotions;

    protected MobData2(ResourceLocation texture, ActionsBuilder combatActions, Map<EquipmentSlot, ItemStack> equipment, boolean humanoid, Armature armature, Map<StunType, AnimationManager.AnimationAccessor<? extends StaticAnimation>> stunMotions) {
        this.texture = texture;
        this.combatActions = combatActions;
        this.equipment = equipment;
        this.humanoid = humanoid;
        this.armature = armature;
        this.stunMotions = stunMotions;
    }

    public Armature getArmature() {
        return armature;
    }

    public abstract void initAnimator(Animator animator);

    public boolean isHumanoid() {
        return humanoid;
    }

    public void equip(PrideMob mob){
        if (mob != null && equipment != null) {
            for (Map.Entry<EquipmentSlot, ItemStack> entry : equipment.entrySet()) {
                mob.setItemSlot(entry.getKey(), entry.getValue());
            }
        }
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ActionsBuilder getCombatActions() {
        return combatActions;
    }
}
