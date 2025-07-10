package com.robson.pride.api.data.types;


import com.robson.pride.api.data.utils.GenericData;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GenericItemData extends GenericData {

    private final Component name;

    private final ResourceLocation model;

    private final Matrix2f collider;

    private final String element;

    private final byte stacks;

    public GenericItemData(CompoundTag tag){
        super(tag);
        this.name = tag.contains("name") ? Component.literal(tag.getString("name")) : Component.literal("");
        this.model = tag.contains("model") ? new ResourceLocation(tag.getString("model")) : ResourceLocation.fromNamespaceAndPath("pride", "item/item");
        this.collider = tag.contains("collider") && tag.getList("collider", CompoundTag.TAG_FLOAT).size() >= 6 ?
        new Matrix2f(tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(1),
                tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(2),
                tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(3),
                tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(4),
                tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(5),
                tag.getList("collider", CompoundTag.TAG_FLOAT).getFloat(6)) : new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f);
        this.element = tag.contains("element") ? tag.getString("element") : "";
        this.stacks = tag.contains("stacks") ? tag.getByte("stacks") : (byte) 1;
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

    public String getElement() {
        return element;
    }
}
