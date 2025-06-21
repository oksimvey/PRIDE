package com.robson.pride.api.data.item;

import com.robson.pride.api.utils.math.Matrix2f;

public class ElementalGemData extends ItemData{

    public ElementalGemData(String name, byte element) {
        super(name, "pride:item/" + name.toLowerCase() + "_gem", (byte) 1, element, new Matrix2f(-0.1f, -0.1f, -0.1f, 0.1f, 0.1f, 0.1f));
    }
}
