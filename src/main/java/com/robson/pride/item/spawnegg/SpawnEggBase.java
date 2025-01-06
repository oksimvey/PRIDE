package com.robson.pride.item.spawnegg;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class SpawnEggBase extends Item {

    public SpawnEggBase(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext useContext) {
        Level worldIn = useContext.getLevel();
        if (!(worldIn instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = useContext.getItemInHand();
            BlockPos pos = useContext.getClickedPos();
            Direction facing = useContext.getClickedFace();
            BlockState blockState = worldIn.getBlockState(pos);
            BlockPos spawnPos = blockState.getCollisionShape(worldIn, pos).isEmpty() ? pos : pos.relative(facing);
            EntityType<?> entity = getEntity(itemStack.getTag().getString("spawn_egg"));
            if (entity != null){
                entity.create(worldIn);
                entity.spawn((ServerLevel) worldIn, spawnPos, MobSpawnType.BUCKET);
                 }
        }
        return InteractionResult.CONSUME;
    }


    private static EntityType<?> getEntity(String spawneggname) {
        return EntityType.byString("pride:" + spawneggname.replace(" ", "_").toLowerCase()).orElse(null);
    }


    @Override
    public Component getName(ItemStack stack) {
        Component defaultName = super.getName(stack);
        if (stack.hasTag() && stack.getTag().contains("spawn_egg")) {
            String spellName = stack.getTag().getString("spawn_egg");
            return Component.literal(spellName + " " + defaultName.getString());
        }

        return defaultName;
    }
}