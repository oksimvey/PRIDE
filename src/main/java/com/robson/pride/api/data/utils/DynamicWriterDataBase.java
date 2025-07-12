package com.robson.pride.api.data.utils;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class DynamicWriterDataBase {

    public static List<DynamicWriterDataBase> ALL = new ArrayList<>();

    private DynamicDataParameter.DataType type;

    int identifier;

    public DynamicWriterDataBase() {
        ALL.add(this);
    }

    public void delete(){
        ALL.remove(this);
    }

}
