package com.robson.pride.api.utils.math;

import net.minecraft.world.entity.LivingEntity;

import static com.robson.pride.api.utils.math.MathUtils.degreeToRadians;

public abstract class PrideVector<T extends PrideVector<T>> implements VectorImpl<T> {

    public final float[] dimensions;

    public PrideVector(float... dimensions){
        this.dimensions = dimensions;
    }

    protected abstract T self(float... values);

    public T add(float... values) {
        float[] result = dimensions.clone();
        for (int i = 0; i < values.length; i++) {
            result[i] += values[i];
        }
        return self(result);
    }

    public T add(PrideVector<?> vector) {
        float[] result = dimensions.clone();
        for (int i = 0; i < vector.dimensions.length; i++) {
            result[i] += vector.dimensions[i];
        }
        return self(result);
    }

    public T subtract(float... values) {
        float[] result = dimensions.clone();
        for (int i = 0; i < values.length; i++) {
            result[i] -= values[i];
        }
        return self(result);
    }

    public T subtract(PrideVector<?> vector) {
        float[] result = dimensions.clone();
        for (int i = 0; i < vector.dimensions.length; i++) {
            result[i] -= vector.dimensions[i];
        }
        return self(result);
    }

    public T scale(float scale){
        float[] result = dimensions.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] *= scale;
        }
        return self(result);
    }

    public T divide(float divisor) {
        if (divisor == 0) {
            return self(0, 0, 0);
        }
        float[] result = dimensions.clone();
        for (int i = 0; i < result.length; i++) {
            result[i] /= divisor;
        }
        return self(result);
    }

    public float length(){
        float squares = 0;
        for (float dimension : dimensions) {
            squares += dimension * dimension;
        }
        return (float) Math.sqrt(squares);
    }

    public T normalize(){
        return this.divide(this.length());
    }

    public T rotate(float degrees){
        float[] result = dimensions.clone();
        float theta = degreeToRadians(degrees);
        float d1 = result[0];
        float d2 = result[result.length - 1];
        result[0] = (float) ((d1 * Math.cos(theta)) - (d2 * Math.sin(theta)));
        result[result.length - 1] = (float) ((d1 * Math.sin(theta)) + (d2 * Math.cos(theta)));
        return self(result);
    }

    public T rotate(LivingEntity entity){
        return self(entity.yBodyRotO + MathUtils.CORRECT_HALF);
    }
}
