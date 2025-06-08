package com.robson.pride.api.utils;

import java.util.ArrayList;
import java.util.List;

public class ServerTask {

    private static List<Runnable> tasks = new ArrayList<>();


    public static void sendTask(Runnable task){
       if (task != null) {
           tasks.add(task);
       }
    }

    public static void runTasks(){
        if (!tasks.isEmpty()){
            for (Runnable task : tasks) {
                task.run();
                tasks.remove(task);
            }
        }
    }
}
