package com.robson.pride.api.customtick;

import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public interface ServerTickManager {

    List<Runnable> TASKS = Collections.synchronizedList(new ArrayList<>());

    static void tick(ServerLevel level) {
        if (level == null) {
            return;
        }
        level.players().forEach(PlayerCustomTick::onTick);
        synchronized (TASKS) {
            Iterator<Runnable> it = TASKS.iterator();
            while (it.hasNext()) {
                Runnable runnable = it.next();
                runnable.run();
                it.remove();
            }
        }
        TimerUtil.schedule(() -> tick(level), 50, TimeUnit.MILLISECONDS);
    }

    static void addVoidTask(Runnable runnable) {
        synchronized (TASKS) {
            TASKS.add(runnable);
        }
    }
}
