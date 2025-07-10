package com.robson.pride.api.data.utils;

import java.util.concurrent.ConcurrentHashMap;

public class DynamicMap<A extends String, B extends GenericData> {

    private final short max_entries;

    private final ConcurrentHashMap<A, DynamicMapParameter<B>> MAP;

    public DynamicMap(short max_entries){
        this.max_entries = max_entries;
        this.MAP = new ConcurrentHashMap<>();
    }

    public B get(A key){
        if (MAP.get(key) == null) return null;
        return MAP.get(key).getData();
    }

    public void put(A key, DynamicMapParameter<B> data){
        MAP.put(key, data);
        if (MAP.size() > max_entries) {
            MAP.remove(MAP.keySet().iterator().next());
        }
    }

    public static class DynamicMapParameter<C extends GenericData> {

        private final C data;
        private long lastAccessed = 0;

        public DynamicMapParameter(C data) {
            this.data = data;
        }

        public C getData() {
            return data;
        }
    }
}
