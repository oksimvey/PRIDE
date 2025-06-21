package com.robson.pride.item.weapons;

import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CustomItem extends SwordItem {

    public CustomItem() {
        super(new Tier() {
            public int getUses() {
                return 100;
            }

            public float getSpeed() {
                return 4f;
            }

            public float getAttackDamageBonus() {
                return -2f;
            }

            @Override
            public int getLevel() {
                return 0;
            }


            public int getEnchantmentValue() {
                return 2;
            }

            public Ingredient getRepairIngredient() {
                return Ingredient.of();
            }
        }, 1, -3f, new Item.Properties());
    }

    public static ItemStack createItem(int id){
        ItemStack Item = new ItemStack(ItemsRegister.CUSTOM_WEAPON_ITEM.get());
        Item.getOrCreateTag().putInt("pride_id", id);
        return Item;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if (stack != null){
            ItemData data = ItemData.getItemData(stack);
            if (data != null){
                return data.getStacks();
            }
        }
        return super.getMaxStackSize(stack);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        ItemData data = ItemData.getItemData(stack);
        if (data != null) {
            ChatFormatting color = ChatFormatting.WHITE;
            if (ElementDataManager.INSTANCE.getByID(data.getElement()) != null) {
                color = ElementDataManager.INSTANCE.getByID(data.getElement()).getChatColor();
            }
            return Component.literal(data.getName()).withStyle(color);
        }
        return super.getName(stack);
    }
}
