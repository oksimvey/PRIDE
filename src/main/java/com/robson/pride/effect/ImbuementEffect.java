package com.robson.pride.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ImbuementEffect extends PrideEffectBase{

    public ImbuementEffect() {
        super(MobEffectCategory.NEUTRAL, 0x57CDFD);
    }

    @Override
    public void prideClientTick(LivingEntity ent){
    }

}
