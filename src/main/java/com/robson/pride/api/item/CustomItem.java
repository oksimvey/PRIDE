package com.robson.pride.api.item;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.GenericItemData;
import com.robson.pride.api.data.types.entity.MobData;
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

    public static ItemStack createItem(String id){
        ItemStack Item = new ItemStack(ItemsRegister.CUSTOM_WEAPON_ITEM.get());
        Item.getOrCreateTag().putString("pride_id", id);
        return Item;
    }

    @Override
    public InteractionResult useOn(UseOnContext useContext) {
        if (false) {
            return InteractionResult.FAIL;
        }
        Level worldIn = useContext.getLevel();
        if (!(worldIn instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        else {
            ItemStack itemStack = useContext.getItemInHand();
            BlockPos pos = useContext.getClickedPos();
            PrideMob entity = new PrideMob(EntityRegister.PRIDE_MOB.get(), worldIn);
            entity.setType(itemStack.getTag().getShort("pride_id"));
            entity.setPos(pos.above().getCenter());
            worldIn.addFreshEntity(entity);

        }
        return InteractionResult.CONSUME;
    }


    @Override
    public int getMaxStackSize(ItemStack stack) {
        if (stack != null){

        }
        return super.getMaxStackSize(stack);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        GenericItemData data = ServerDataManager.getWeaponData(stack);
        return data != null ? data.getName() : super.getName(stack);
    }
}
