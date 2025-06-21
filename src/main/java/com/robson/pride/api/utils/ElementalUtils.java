package com.robson.pride.api.utils;

import com.robson.pride.api.data.item.ItemData;
import com.robson.pride.api.data.item.WeaponData;
import com.robson.pride.api.data.manager.ElementDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.registries.EffectRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
public class ElementalUtils {

    public static void setElement(Entity ent, byte element) {
        if (ent instanceof Player player) {
            player.getPersistentData().putByte
                    ("Element", element);
        }
    }

    public static ParticleOptions getParticleByElement(byte element) {
        if (ElementDataManager.INSTANCE.getByID(element) != null) {
            return  ElementDataManager.INSTANCE.getByID(element).getNormalParticleType();
        }
        return null;
    }

    public static ChatFormatting getColorByElement(byte element) {
        if (ElementDataManager.INSTANCE.getByID(element) != null) {
            return ElementDataManager.INSTANCE.getByID(element).getChatColor();
        }
        return ChatFormatting.GRAY;
    }

    public static void playSoundByElement(byte element, Entity ent, float volume) {
        if (ElementDataManager.INSTANCE.getByID(element) != null) {
            ElementDataManager.INSTANCE.getByID(element).playSound(ent, volume);
        }
    }

    public static boolean canPutElementalPassive(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            byte leftelement = 0;
            if (leftitem.getTag().getBoolean("hasweaponart")) {
                leftelement = WeaponSkillsDataManager.INSTANCE.getByID(leftitem.getTag().getShort("weapon_art")).getSkillElement();
            }
            else {
                WeaponData data =  WeaponData.getWeaponData(leftitem);
                if (data != null) {
                        WeaponSkillBase skill = data.getSkill();
                        if (skill != null) {
                            leftelement = skill.getSkillElement();
                        }
                }
            }
            return leftelement == ElementDataManager.NEUTRAL || leftelement == rightitem.getTag().getByte("passive_element");
        }
        return false;
    }

    public static boolean canPutWeaponArt(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            short rightelement = rightitem.getTag().getShort("weapon_art");
            byte leftelement = getItemElement(leftitem);
            return rightelement == ElementDataManager.NEUTRAL || leftelement == 0 || leftelement == rightelement;
        }
        return false;
    }

    public static byte getItemElement(ItemStack item) {
        byte element = 0;
        if (item != null) {
            if (item.getTag() != null) {
                element = item.getTag().getByte("passive_element");
                if (ElementDataManager.INSTANCE.getByID(element) == null) {
                    ItemData data = ItemData.getItemData(item);
                    if (data != null && ElementDataManager.INSTANCE.getByID((data.getElement())) != null) {
                        element = data.getElement();
                    }
                }
            }
        }
        return element;
    }

    public static void rollElement(Entity ent) {
        if (ent != null) {
            short chance = (short) MathUtils.getRandomInt(1000);
            if (chance == 0) {
                setElement(ent, (byte) 1);
            } else if (chance >= 1 && chance <= 10) {
                setElement(ent, (byte) 2);
            } else if (chance >= 11 && chance <= 40) {
                setElement(ent, (byte) 3);
            } else if (chance >= 41 && chance <= 90) {
                setElement(ent, (byte) 4);
            } else if (chance >= 91 && chance <= 140) {
                setElement(ent, (byte) 5);
            } else if (chance >= 141 && chance <= 240) {
                setElement(ent, (byte) 6);
            } else if (chance >= 241 && chance <= 340) {
                setElement(ent, (byte) 7);
            } else if (chance >= 341 && chance <= 560) {
                setElement(ent, (byte) 8);
            } else if (chance >= 561 && chance <= 780) {
                setElement(ent, (byte) 9);
            } else setElement(ent, (byte) 10);
        }
    }

    public static byte getElement(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                CompoundTag tag = TagsUtils.playerTags.get(player);
                if (tag != null) {
                    return tag.getByte("Element");
                }
            }
        }
        return 0;
    }

    public static boolean isNotInWater(Entity ent, Vec3 vec3) {
        if (ent instanceof LivingEntity living && vec3 != null) {
            BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            return !ent.level().getBlockState(pos).is(Blocks.WATER) && !ent.level().isRainingAt(pos) && !living.hasEffect(EffectRegister.WET.get());
        }
        return false;
    }
}
