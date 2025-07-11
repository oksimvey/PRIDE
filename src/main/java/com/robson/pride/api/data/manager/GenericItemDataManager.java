package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.GenericData;
import net.minecraft.world.item.ItemStack;

public abstract class GenericItemDataManager<B extends GenericData> extends DataManager<B> {

    public final B getByItem(ItemStack stack){
        return stack != null ? this.getByKey(stack.getOrCreateTag().getString("data_id")) : null;
    }

}
