package com.robson.pride.api.data.types;


import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GenericData {

    private final Component name;

    private final ResourceLocation model;

    private final Matrix2f collider;

    private final byte element;

    private final byte stacks;

    public GenericData(Component name, String model, Matrix2f collider, byte element, byte stacks){
        this.name = name;
        this.model = new ResourceLocation(model);
        this.collider = collider;
        this.element = element;
        this.stacks = stacks;
    }

    public Component getName() {
        return name;
    }

    public ResourceLocation getModel() {
        return model;
    }

    public Matrix2f getCollider() {
        return collider;
    }

    public byte getStacks() {
        return stacks;
    }

    public byte getElement() {
        return element;
    }
}
