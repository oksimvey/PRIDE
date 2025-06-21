package com.robson.pride.api.data.item;

import com.robson.pride.api.data.manager.ItemDataManager;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.item.weapons.CustomItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ItemData {

    private final String name;

    private final ResourceLocation model;

    private final byte stacks;

    private final byte element;

    private final Matrix2f collider;

    public ItemData(String name, String model, byte stacks, byte element, Matrix2f collider){
        this.name = name;
        this.model = new ResourceLocation(model);
        this.stacks = stacks;
        this.element = element;
        this.collider = collider;
    }

    public String getName() {
        return this.name;
    }

    public ResourceLocation getModel() {
        return this.model;
    }

    public byte getStacks() {
        return this.stacks;
    }

    public byte getElement() {
        return this.element;
    }

    public Matrix2f getCollider() {
        return this.collider;
    }

    public static ItemData getItemData(ItemStack itemStack){
        if (itemStack != null && itemStack.getItem() instanceof CustomItem){
            return ItemDataManager.INSTANCE.getByID(itemStack.getOrCreateTag().getInt("pride_id"));
        }
        return null;
    }


}
