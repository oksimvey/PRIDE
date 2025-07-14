package com.robson.pride.api.item;

import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.types.item.GenericItemData;
import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.utils.ItemStackNewParams;
import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.registries.EntityRegister;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
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

    public static ItemStack createItem(String id) {
        ItemStack Item = new ItemStack(ItemsRegister.CUSTOM_WEAPON_ITEM.get());
        Item.getOrCreateTag().putString("data_id", id);
        return Item;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        if (stack != null){

        }
        return super.getMaxStackSize(stack);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        GenericItemData data = WeaponDataManager.MANAGER.getByItem(stack);
        return data != null ? data.getName() : super.getName(stack);
    }
}
