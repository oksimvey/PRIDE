package com.robson.pride.api.ai.trade;

import net.minecraft.world.item.ItemStack;

public record SellOffer(ItemStack offer, int price) {

    public ItemStack getOffer() {
        return offer;
    }

    public int getPrice() {
        return price;
    }
}
