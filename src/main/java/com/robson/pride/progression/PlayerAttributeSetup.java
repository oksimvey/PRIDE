package com.robson.pride.progression;

import com.robson.pride.api.utils.ManaUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.registries.AttributeRegister;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class PlayerAttributeSetup {

    public static void setupPlayerAttributes(Player player){
        if (player != null){
           setupVigor(player, player.getPersistentData().getInt("VigorLvl"));
           setupDexterity(player, player.getPersistentData().getInt("DexterityLvl"));
           setupEndurance(player, player.getPersistentData().getInt("EnduranceLvl"));
           setupMind(player,  player.getPersistentData().getInt("MindLvl"));
        }
    }

    public static void setupDexterity(Player player, int dexteritylvl){
        if (player != null){
            AttributeInstance attributeInstance = player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED);
            attributeInstance.setBaseValue(attributeInstance.getBaseValue() + 0.0001 * dexteritylvl);
        }
    }

    public static void setupVigor(Player player, int vigorlvl){
        if (player != null){
            player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(20 + (vigorlvl * 1.8));
            player.setHealth(player.getMaxHealth());
        }
    }

    public static void setupEndurance(Player player, int endurancelvl){
        if (player != null){
            AttributeInstance attributeInstance = player.getAttributes().getInstance(EpicFightAttributes.MAX_STAMINA.get());
            attributeInstance.setBaseValue(attributeInstance.getBaseValue() + 0.35 * endurancelvl);
            player.getAttributes().getInstance(AttributeRegister.MAX_WEIGHT.get()).setBaseValue(50 + endurancelvl);
            StaminaUtils.resetStamina(player);
        }
    }

    public static void setupMind(Player player, int mindlvl){
        if (player != null){
            AttributeInstance attributeInstance = player.getAttributes().getInstance(AttributeRegistry.MAX_MANA.get());
            attributeInstance.setBaseValue(attributeInstance.getBaseValue() + 1.5 * mindlvl);
            ManaUtils.addMana(player, 100000);
        }
    }
}
