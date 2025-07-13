package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.manager.DataFileManager;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DynamicMap<A, B> extends DynamicDataBase<A, B> {

    protected final DynamicDataParameter.DataType KEY_TYPE;

    protected final int CLEAN_TIME;

    private final String IDENTIFIER;

    private final File OUT_PUT;

    public DynamicMap(DynamicDataParameter.DataType keytype, DynamicDataParameter.DataType valuetype) {
        super(valuetype);
        this.KEY_TYPE = keytype;
        this.IDENTIFIER = UUID.randomUUID().toString();
        this.OUT_PUT = DataFileManager.DYNAMIC_MAP_OUTPUT.resolve(keytype.toString() + valuetype.toString() + IDENTIFIER + ".dat").toFile();
        this.CLEAN_TIME = switch (keytype){
            case BOOLEAN -> 250;
            case GENERIC_DATA, INTEGER, FLOAT -> 1000;
            case STRING, ITEM_STACK, ENTITY -> 2000;
            case SHORT -> 500;
            case VECTORS -> 1500;
        };
        try {
            NbtIo.writeCompressed(new CompoundTag(), OUT_PUT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected int calculateCleanTime(){
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("clean time: " + CLEAN_TIME));
        return (int) (CLEAN_TIME * Math.sqrt(KEY_SIZE << 5));
    }

    public B get(A key) {
        B value = super.get(key);
        if (value == null) {
            try {
                CompoundTag tag = NbtIo.readCompressed(OUT_PUT);
                String keyString = writeKeyToString(key);
                if (tag.contains(keyString)) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("restored"));
                    value = getValueFromTag(tag, keyString);
                    DATA.put(key, new DynamicDataParameter<>(value, VALUE_TYPE));
                    tag.remove(keyString);
                    try {
                        NbtIo.writeCompressed(tag, OUT_PUT);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return value;
    }


    public void removeForAllocation(A key, B value) {
        super.removeForAllocation(key, value);
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("removed"));
        try {
            CompoundTag tag = NbtIo.readCompressed(OUT_PUT);
            try {
                NbtIo.writeCompressed(writeToCompound(tag, key, value), OUT_PUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void remove(A key) {
        if (contains(key)) {
            DATA.remove(key);
            return;
        }
        try {
            CompoundTag tag = NbtIo.readCompressed(OUT_PUT);
            String string = writeKeyToString(key);
            if (tag.contains(string)) {
                tag.remove(string);
            }
            try {
                NbtIo.writeCompressed(tag, OUT_PUT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearMap() {
        super.clearMap();
        try {
            NbtIo.writeCompressed(new CompoundTag(), OUT_PUT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String writeKeyToString(A key) {
        switch (KEY_TYPE) {

            case BOOLEAN -> {
                if (key instanceof Boolean bool) return bool.toString();
            }

            case STRING -> {
                if (key instanceof String str) return str;
            }

            case INTEGER -> {
                if (key instanceof Integer integer) return integer.toString();
            }

            case FLOAT -> {
                if (key instanceof Float float1) return float1.toString();
            }

            case SHORT -> {
                if (key instanceof Short short1) return short1.toString();
            }

            case ITEM_STACK -> {
                if (key instanceof ItemStack stack) return ItemStackUtils.getNewParams(stack).getRandomIdentifier();
            }
            case ENTITY -> {
                if (key instanceof Entity entity) return entity.getId() + "";
            }
            case VECTORS -> {
                if (key instanceof PrideVec3f vec3f) {
                    return vec3f.x() + " " + vec3f.y() + " " + vec3f.z();
                }
            }
        }
        return "";
    }


    protected CompoundTag writeToCompound(CompoundTag tag, A key, B value) {

        String keyString = writeKeyToString(key);

        if (value instanceof Boolean bool) tag.putBoolean(keyString, bool);

        if (value instanceof String str) tag.putString(keyString, str);

        if (value instanceof Integer integer) tag.putInt(keyString, integer);

        if (value instanceof Float float1) tag.putFloat(keyString, float1);

        if (value instanceof Short short1) tag.putShort(keyString, short1);

        if (value instanceof ItemStack stack) tag.putString(keyString, ItemStackUtils.getNewParams(stack).getRandomIdentifier());

        if (value instanceof Entity entity) tag.putInt(keyString, entity.getId());

        if (value instanceof PrideVec3f vec3f) {
            tag.putString(keyString, vec3f.x() + " " + vec3f.y() + " " + vec3f.z());

        }
        return tag;
    }

    private B getValueFromTag(CompoundTag tag, String keyString){
        return switch (VALUE_TYPE){
            case BOOLEAN -> (B) (Object) tag.getBoolean(keyString);
            case STRING -> (B) (Object) tag.getString(keyString);
            case INTEGER -> (B) (Object) tag.getInt(keyString);
            case FLOAT -> (B) (Object) tag.getFloat(keyString);
            case SHORT -> (B) (Object) tag.getShort(keyString);
            case GENERIC_DATA -> (B) (Object) tag.getString(keyString);
            case ITEM_STACK -> (B) (Object) tag.getString(keyString);
            case ENTITY -> (B) (Object) Minecraft.getInstance().level.getEntity(tag.getInt(keyString));
            case VECTORS -> (B) (Object) new PrideVec3f(0 ,0, 0);
        };
    }

}
