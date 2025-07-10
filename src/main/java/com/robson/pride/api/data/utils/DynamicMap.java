package com.robson.pride.api.data.utils;

import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.concurrent.*;

public class DynamicMap<A extends String, B extends GenericData> {

    private static final ScheduledExecutorService MAP_THREADER = Executors.newScheduledThreadPool(1);

    private static final float sizeDivisor = MathUtils.EULER * 1000f;

    private final ConcurrentHashMap<A, DynamicMapParameter<B>> MAP;

    public float size;

    public DynamicMap(){
        this.MAP = new ConcurrentHashMap<>();
        this.size = 1;
    }

    public B get(A key) {
        DynamicMapParameter<B> param = MAP.get(key);
        if (param == null) return null;
        MAP.get(key).accesses++;
        return param.getData();
    }

    public void put(A key, B value, long expiretime){
       put(key, new DynamicMapParameter<>(value, expiretime));
    }

    public void put(A key, DynamicMapParameter<B> data){
        MAP.put(key, data);
        this.size += data.expiretime / sizeDivisor;
        MAP_THREADER.schedule(()->thread(key, data), data.expiretime, TimeUnit.MILLISECONDS);
        if (Minecraft.getInstance().player != null){
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("created data"));
        }
    }

    private void thread(A key, DynamicMapParameter<B> data) {
        DynamicMapParameter<B> current = MAP.get(key);
        if (current == data) {
            if (current.accesses == 0) {
                MAP.remove(key);
                this.size -= current.expiretime / sizeDivisor;
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("removed data"));
                }
            } else {
                MAP_THREADER.schedule(() -> thread(key, current), (long) ((long) (current.expiretime * (Math.log((current.accesses - 1) + MathUtils.EULER))) / this.size), TimeUnit.MILLISECONDS);
                MAP.get(key).accesses = 0;
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("updated data" + current.expiretime));
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("rescheduled data"));
                }
            }
        }
    }

    public static class DynamicMapParameter<C extends GenericData> {

        private final C data;
        public final long expiretime;
        public long accesses;

        public DynamicMapParameter(C data, long expiretime) {
            this.data = data;
            this.expiretime = expiretime;
            this.accesses = 0;
        }

        public C getData() {
            return data;
        }
    }
}
