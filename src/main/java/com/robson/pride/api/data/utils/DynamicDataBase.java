package com.robson.pride.api.data.utils;

import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class DynamicDataBase<A, B, C> {

    /// A is the key/input
    /// B is the value/output
    /// C is the collection

    private static final ScheduledExecutorService THREADER = Executors.newScheduledThreadPool(1);

    private static final int SIZE_DIVISOR = (int) (MathUtils.EULER * 100f);

    private int SIZE;

    private short KEY_SIZE;

    protected final C DATA;

    protected final DynamicDataParameter.DataType TYPE;

    public DynamicDataBase(C data, DynamicDataParameter.DataType type) {
        this.DATA = data;
        this.TYPE = type;
        this.SIZE = 1;
        this.KEY_SIZE = 1;
        this.threadMap();
    }

    public abstract B get(A key);

    public final void put(A key, B value) {
        if (value == null) {
            return;
        }
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("created"));
        }
        DynamicDataParameter<B> data = new DynamicDataParameter<>(value, TYPE);
        this.SIZE += calculateSizeIncrement(data);
        this.KEY_SIZE++;
        putOnMap(key, data);
    }

    protected abstract void clear(A key);

    protected abstract void putOnMap(A key, DynamicDataParameter<B> data);

    protected abstract DynamicDataParameter<B> getParameter(A key);

    private void threadMap() {
        THREADER.schedule(this::threadMap, calculateCleanTime(), TimeUnit.MILLISECONDS);
        for (A key : getAllKeys()) {
            DynamicDataParameter<B> data = getParameter(key);
            if (data.accesses == 0) {
                removeKey(key);
                this.KEY_SIZE--;
                if (Minecraft.getInstance().player != null){
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("cleared"));
                }
                this.SIZE -= calculateSizeIncrement(data);
                continue;
            }
            if (System.currentTimeMillis() - data.lastUpdate > calculateExpirationTime(data)) {
               clear(key);
            }
        }
    }

    private int calculateExpirationTime(DynamicDataParameter<B> current) {
        return (int) ((current.getExpireTime() * Math.sqrt((current.accesses + 1))) / SIZE);
    }

    private int calculateSizeIncrement(DynamicDataParameter<B> data) {
        return data.getExpireTime() / SIZE_DIVISOR;
    }

    protected abstract List<A> getAllKeys();

    private int calculateCleanTime(){
        return (int) (1000 * Math.sqrt(KEY_SIZE << 5));
    }

    public abstract void removeKey(A key);

    public abstract void removeValue(DynamicDataParameter<B> value);

}
