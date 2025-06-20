package com.robson.pride.api.map;

import com.robson.pride.api.utils.math.Vec3f;

import java.util.List;
import java.util.Map;

public class FastTravel {

    public static Map<String, Vec3f> places = Map.ofEntries(
            Map.entry("Samurai Sky Island", new Vec3f(-1919, 247, 391)),
            Map.entry("Pyramid", new Vec3f(-1699, 12, -1432)),
            Map.entry("Oasis", new Vec3f(-1869, 102, -1205)),
            Map.entry("Desert Village", new Vec3f(-1793, 138, -1783)),
            Map.entry("Jungle Fortress", new Vec3f(-1511, 15, 471))
    );
}
