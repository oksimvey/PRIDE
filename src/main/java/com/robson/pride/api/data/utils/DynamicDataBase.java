package com.robson.pride.api.data.utils;

import com.robson.pride.api.utils.math.MathUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class DynamicDataBase<A, B> {

    /// A is the key/input
    /// B is the value/output

    private static final ScheduledExecutorService THREADER = Executors.newScheduledThreadPool(1);

    private static List<DynamicDataBase<?, ?>> ALL = new ArrayList<>();

    private static final int SIZE_DIVISOR = (int) (MathUtils.EULER * 100f);

    protected int SIZE;

    protected int KEY_SIZE;

    protected final ConcurrentHashMap<A, DynamicDataParameter<B>> DATA;

    protected final DynamicDataParameter.DataType VALUE_TYPE;

    public static void clearAll() {
        for (DynamicDataBase<?, ?> data : ALL) {
            data.clearMap();
        }
    }

    public DynamicDataBase(DynamicDataParameter.DataType type) {
        this.VALUE_TYPE = type;
        this.SIZE = 1;
        this.KEY_SIZE = 1;
        this.DATA = new ConcurrentHashMap<>();
        this.threadMap();
        ALL.add(this);
    }

    public B get(A key){
        DynamicDataParameter<B> data = getParameter(key);
        if (data != null) {
            return data.getData();
        }
        return null;
    }

    protected DynamicDataParameter<B> getParameter(A key){
        return DATA.get(key);
    }


    public final void put(A key, B value) {
        if (value == null) {
            return;
        }
        DynamicDataParameter<B> data = new DynamicDataParameter<>(value, VALUE_TYPE);
        this.SIZE += calculateSizeIncrement(data);
        this.KEY_SIZE++;
        putOnMap(key, data);
    }

    public final boolean contains(A key){
        return get(key) != null;
    }

    protected void clearMap(){
        DATA.clear();
    }

    protected void resetAccess(A key){
        DATA.get(key).resetAccesses();
    }

    protected final void putOnMap(A key, DynamicDataParameter<B> data){
        DATA.put(key, data);
    }

    private void threadMap() {
        THREADER.schedule(this::threadMap, calculateCleanTime(), TimeUnit.MILLISECONDS);
        for (Map.Entry<A, DynamicDataParameter<B>> entry : DATA.entrySet()) {
            A key = entry.getKey();
            DynamicDataParameter<B> data = entry.getValue();
            if (data.accesses == 0) {
                removeForAllocation(key, data.getData());
                this.KEY_SIZE--;
                this.SIZE -= calculateSizeIncrement(data);
                continue;
            }
            if (System.currentTimeMillis() - data.lastUpdate > calculateExpirationTime(data)) {
                resetAccess(key);
            }
        }
    }

    private int calculateExpirationTime(DynamicDataParameter<B> current) {
        return (int) ((current.size * Math.sqrt((current.accesses + 1))) / SIZE);
    }

    private int calculateSizeIncrement(DynamicDataParameter<B> data) {
        return data.size / SIZE_DIVISOR;
    }

    protected int calculateCleanTime(){
        return (int) (1000 * Math.sqrt(KEY_SIZE << 5));
    }

    protected void removeForAllocation(A key, B value){
        DATA.remove(key);
    }

    public void remove(A key){
        DATA.remove(key);
    }
}
