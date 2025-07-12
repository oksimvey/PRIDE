package com.robson.pride.api.data.utils;

import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.utils.math.MathUtils;

import java.util.Map;
import java.util.concurrent.*;

public class DynamicMap<A, B extends GenericData> {

    private static final ScheduledExecutorService MAP_THREADER = Executors.newScheduledThreadPool(1);

    private static final int SIZE_DIVISOR = (int) (MathUtils.EULER * 100f);

    private final ConcurrentHashMap<A, DynamicMapParameter<B>> MAP;

    private int MAP_SIZE;

    public DynamicMap() {
        this.MAP = new ConcurrentHashMap<>();
        this.MAP_SIZE = 1;
        this.threadMap();
    }

    public B get(A key) {
        DynamicMapParameter<B> param = MAP.get(key);
        if (param == null) return null;
        return param.getData();
    }

    public void put(A key, B value) {
        if (value == null) {
            return;
        }
        put(key, new DynamicMapParameter<>(value));
    }

    private void put(A key, DynamicMapParameter<B> data) {
        MAP.put(key, data);
        this.MAP_SIZE += calculateSizeIncrement(data);
    }

    private void threadMap() {
        MAP_THREADER.schedule(this::threadMap, calculateCleanTime(), TimeUnit.MILLISECONDS);
        for (Map.Entry<A, DynamicMapParameter<B>> entry : this.MAP.entrySet()) {
            A key = entry.getKey();
            DynamicMapParameter<B> data = entry.getValue();
            if (data.accesses == 0) {
                MAP.remove(key);
                this.MAP_SIZE -= calculateSizeIncrement(data);
                continue;
            }
            if (System.currentTimeMillis() - data.lastUpdate > calculateExpirationTime(data)) {
                this.MAP.get(key).resetAccesses();
            }
        }
    }

    private int calculateCleanTime() {
        return (int) (1000 * Math.sqrt(this.MAP_SIZE));
    }

    private int calculateSizeIncrement(DynamicMapParameter<B> data) {
        return data.getExpireTime() / SIZE_DIVISOR;
    }

    private int calculateExpirationTime(DynamicMapParameter<B> current) {
        return (int) ((current.getExpireTime() * Math.sqrt((current.accesses + 1))) / MAP_SIZE);
    }

    static class DynamicMapParameter<C extends GenericData> {

        private final C data;

        private int accesses;

        private long lastUpdate;

        public DynamicMapParameter(C data) {
            this.data = data;
            this.accesses = 0;
            this.lastUpdate = System.currentTimeMillis();
        }

        public int getExpireTime() {
            return data.getSize();
        }

        public C getData() {
            accesses++;
            return data;
        }

        private void resetAccesses() {
            this.lastUpdate = System.currentTimeMillis();
            this.accesses = 0;
        }
    }
}
