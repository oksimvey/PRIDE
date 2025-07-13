package com.robson.pride.api.data.types.item;


import com.robson.pride.api.data.types.GenericNBTData;
import com.robson.pride.api.utils.math.Matrix2f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GenericItemData extends GenericNBTData {

    private final Component name;

    private final BakedModel model;

    private final Matrix2f collider;

    private final String element;

    private final byte stacks;

    public GenericItemData(CompoundTag tag){
        super(tag);
        this.name = tag.contains("name") ? Component.literal(tag.getString("name")) : Component.literal("");
          if(tag.contains("collider")) {
            CompoundTag colliderTag = tag.getCompound("collider");
            float minX = colliderTag.getFloat("minX");
            float minY = colliderTag.getFloat("minY");
            float minZ = colliderTag.getFloat("minZ");
            float maxX = colliderTag.getFloat("maxX");
            float maxY = colliderTag.getFloat("maxY");
            float maxZ = colliderTag.getFloat("maxZ");
            this.collider = new Matrix2f(minX, minY, minZ, maxX, maxY, maxZ);
        }
        else this.collider = new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f);
        this.element = tag.contains("element") ? tag.getString("element") : "";
        this.stacks = tag.contains("stacks") ? tag.getByte("stacks") : (byte) 1;
        ResourceLocation modelrl = tag.contains("model") ? new ResourceLocation(tag.getString("model")) : ResourceLocation.fromNamespaceAndPath("pride", "item/item");
        this.model = Minecraft.getInstance().getModelManager().getModel(modelrl);

    }

    public Component getName() {
        return name;
    }

    public BakedModel getModel() {
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
