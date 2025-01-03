package com.robson.pride.entities.special;

import com.robson.pride.api.utils.MathUtils;
import de.cheaterpaul.fallingleaves.util.Wind;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LeafEntity extends Monster {

    private int tickcount = 0;
float maxRotateTime = (3 + this.random.nextInt(5)) * 20;
        float maxRotateSpeed = (float)(this.random.nextBoolean() ? -1 : 1) * (0.1F + 2.4F * this.random.nextFloat()) * 6.2831855F / 20.0F;
    private int red = 255;
    private int green = 255;
    private int blue = 255;
     float windCoefficient = 0.6F + this.random.nextFloat() * 0.4F;
    float gravity = - 0.1f;
    int rotateTime = 0;
    float xd = 0;
    float yd = 0;
    float zd = 0;

    public void setColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public LeafEntity(EntityType<? extends LeafEntity> type, Level world) {
        super(type, world);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 100.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.ATTACK_DAMAGE, 40.0D);
    }
    @Override
    public void tick() {

        tickcount++;
        if (tickcount > 200){
            this.remove(RemovalReason.DISCARDED);
            return;
        }
        if (this.level().getFluidState(new BlockPos((int) this.getX(), (int) this.getY(), (int) this.getZ())).is(FluidTags.WATER)) {
            yd = 0;
            xd *= (float) (1 - 0.05);
            zd *= (float) (1 - 0.05);
        } else {

            yd -= 0.04 * gravity;

            if (onGround()) {
                rotateTime = (int) Math.min(rotateTime + 1, maxRotateTime);
                this.setXRot((rotateTime / maxRotateTime) * maxRotateSpeed);
            } else {
                rotateTime = 0;
            }
            xd += (Wind.windX - xd) * windCoefficient / 60.0f;
            zd += (Wind.windZ - zd) * windCoefficient / 60.0f;
        }
        this.setDeltaMovement(xd, yd, zd);
        AABB aabb = MathUtils.createAABBForCulling(12);
        List<Entity> listent = this.level().getEntities(this, aabb);
        for (Entity entko : listent) {
            if (entko == null || entko instanceof LeafEntity){
                return;
            }
        }
    }
    public boolean onGround(){
        return this.level().getBlockState(new BlockPos((int) this.getX(), (int) (this.getY() - 0.1), (int) this.getZ())).getBlock() instanceof AirBlock;
    }
}
