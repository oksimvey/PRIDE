package com.robson.pride.api.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.List;

public class PrideMobBase extends Monster {

    private String element;
    private List<String> skills;
    private byte variant;

    protected PrideMobBase(EntityType<?> p_33002_, Level p_33003_, String element, byte variant) {
        super((EntityType<? extends Monster>) p_33002_, p_33003_);
        this.element = element;
        this.variant = variant;
    }

    public boolean hasSkill(String skill) {
        return this.skills.contains(skill);
    }

    public String getElement() {
        return this.element;
    }
}
