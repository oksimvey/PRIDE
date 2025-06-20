package com.robson.pride.api.data.manager;

import com.robson.pride.api.utils.math.Vec3f;

public interface LocationsDataManager {

     Vec3f TOWN = new Vec3f(128, 128, 128);

     static Vec3f getLocationByID(int id){
         return switch (id){
             default -> TOWN;
         };
     }
}
