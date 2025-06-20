package com.robson.pride.api.ai.trade;

import net.minecraft.world.item.Item;

public record BuyOffer(Item item, int price) {

    public Item getItem() {
        return item;
    }

    public int getPrice() {
        return price;
    }
}
