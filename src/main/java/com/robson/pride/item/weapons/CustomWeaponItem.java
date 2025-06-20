package com.robson.pride.item.weapons;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.registries.ItemsRegister;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class CustomWeaponItem extends SwordItem {

    public CustomWeaponItem() {
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

    public static ItemStack createWeapon(int id){
        ItemStack weaponItem = new ItemStack(ItemsRegister.CUSTOM_WEAPON_ITEM.get());
        weaponItem.getOrCreateTag().putInt("weaponid", id);
        return weaponItem;
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        WeaponData data = WeaponData.getWeaponData(stack);
        if (data != null) {
            return Component.literal(data.getName());
        }
        return super.getName(stack);
    }
}
