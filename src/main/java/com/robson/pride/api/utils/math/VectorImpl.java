package com.robson.pride.api.utils.math;

import net.minecraft.world.entity.LivingEntity;

public interface VectorImpl<T> {

        T add(float... values);
        T add(PrideVector<?> vector);
        T subtract(float... values);
        T subtract(PrideVector<?> vector);
        T scale(float scale);
        T divide(float divisor);
        T normalize();
        T rotate(float degrees);
        T rotate(LivingEntity entity);
}
