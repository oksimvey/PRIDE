package com.robson.pride.api.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerUtil {

    private static final ScheduledExecutorService Timer = Executors.newScheduledThreadPool(1);

    public static void schedule(Runnable task, long delay, TimeUnit unit) {
        Timer.schedule(task, delay, unit);
    }

    public static void shutdown() {
        Timer.shutdown();
    }
}
