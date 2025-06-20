package com.robson.pride.registries;

import java.util.Arrays;
import java.util.List;

public interface WeaponSkillRegister {

    List<String> rarities = Arrays.asList("Mythical", "Legendary", "Epic", "Rare", "Uncommon", "Common");

    static boolean isValid(byte id){
        return id >= 1 && id <= 11;
    }
}
