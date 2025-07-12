package com.robson.pride.api.utils;

import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.data.types.ElementData;
import com.robson.pride.api.data.types.item.WeaponData;
import com.robson.pride.api.data.types.skill.WeaponSkillData;
import com.robson.pride.api.utils.math.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
public class ElementalUtils {

    public static void setElement(Entity ent,String element) {
        if (ent instanceof Player player) {
            player.getPersistentData().putString
                    ("Element", element);
        }
    }

    public static ParticleOptions getParticleByElement(byte element) {

        return null;
    }

    public static ChatFormatting getColorByElement(String element) {
        ElementData data = ElementDataManager.MANAGER.getByKey(element);
        if (data != null) {
            return data.getChatColor();
        }
        return ChatFormatting.GRAY;
    }

    public static void playSoundByElement(byte element, Entity ent, float volume) {

    }

    public static boolean canPutElementalPassive(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            byte leftelement = 0;
            if (leftitem.getTag().getBoolean("hasweaponart")) {
            }
            else {
                WeaponData data = WeaponDataManager.MANAGER.getByItem(leftitem);
                if (data != null) {
                        WeaponSkillData skill = data.getSkill();
                        if (skill != null) {
                            leftelement = Byte.parseByte(skill.getElement());
                        }
                }
            }
            return leftelement == rightitem.getTag().getByte("passive_element");
        }
        return false;
    }

    public static boolean canPutWeaponArt(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            String rightelement = rightitem.getTag().getString("weapon_art");
            String leftelement = getItemElement(leftitem);
            return  false;
        }
        return false;
    }

    public static String getItemElement(ItemStack item) {
        if (item != null) {
                WeaponData data = WeaponDataManager.MANAGER.getByItem(item);
               if (data != null){
                   return data.getElement();
               }
        }
        return "";
    }

    public static void rollElement(Entity ent) {
        if (ent != null) {
            short chance = (short) MathUtils.getRandomInt(1000);
            if (chance == 0) {
                setElement(ent, ElementDataManager.DARKNESS);
            } else if (chance >= 1 && chance <= 10) {
                setElement(ent, ElementDataManager.LIGHT);
            } else if (chance >= 11 && chance <= 40) {
                setElement(ent, ElementDataManager.THUNDER);
            } else if (chance >= 41 && chance <= 90) {
                setElement(ent, ElementDataManager.SUN);
            } else if (chance >= 91 && chance <= 140) {
                setElement(ent, ElementDataManager.MOON);
            } else if (chance >= 141 && chance <= 240) {
                setElement(ent, ElementDataManager.BLOOD);
            } else if (chance >= 241 && chance <= 340) {
                setElement(ent, ElementDataManager.WIND);
            } else if (chance >= 341 && chance <= 560) {
                setElement(ent, ElementDataManager.NATURE);
            } else if (chance >= 561 && chance <= 780) {
                setElement(ent, ElementDataManager.ICE);
            } else setElement(ent, ElementDataManager.WATER);
        }
    }

    public static String getElement(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                return ClientDataManager.CLIENT_DATA_MANAGER.get(player).getProgressionData().getElement() ;
            }
        }
        return "";
    }

    public static boolean isNotInWater(Entity ent, Vec3 vec3) {
        if (ent != null && vec3 != null) {
            BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            return !ent.level().getBlockState(pos).is(Blocks.WATER) && !ent.level().isRainingAt(pos);
        }
        return false;
    }
}
