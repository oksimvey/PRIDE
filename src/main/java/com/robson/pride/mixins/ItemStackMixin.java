package com.robson.pride.mixins;

import com.robson.pride.api.data.utils.ItemStackNewParams;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = ItemStack.class, remap = false)
public abstract class ItemStackMixin implements ItemStackNewParams {

    @Unique
    private String randomIdentifier;
    @Unique
    private String dataID;
    @Unique
    private String element;
    @Unique
    private String skill;

    @Override
    public String getRandomIdentifier() {
        if (randomIdentifier == null){
            randomIdentifier = UUID.randomUUID().toString();
        }
        return randomIdentifier;
    }


    @Override
    public String getDataID() {
        return dataID;
    }

    @Override
    public void setDataID(String String) {
        this.dataID = String;
    }

    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public void setElement(String String) {
        this.element = String;
    }

    @Override
    public String getSkill() {
        return this.skill;
    }

    @Override
    public void setSkill(String String) {
        this.skill = String;
    }
}
