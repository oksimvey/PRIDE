package com.robson.pride.api.utils;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.enums.ElementsEnum;
import com.robson.pride.api.enums.SkillsEnum;
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

    public static void setElement(Entity ent, String element) {
        if (ent instanceof Player player) {
            player.getPersistentData().putString("Element", element);
        }
    }

    public static ParticleOptions getParticleByElement(String element) {
        if (ElementsEnum.get(element) != null) {
            return  ElementsEnum.get(element).getNormalParticleType();
        }
        return null;
    }

    public static ChatFormatting getColorByElement(String element) {
        if (ElementsEnum.get(element) != null) {
            return ElementsEnum.get(element).getChatColor();
        }
        return ChatFormatting.GRAY;
    }

    public static void playSoundByElement(String element, Entity ent, float volume) {
        if (ElementsEnum.get(element) != null) {
            ElementsEnum.get(element).playSound(ent, volume);
        }
    }

    public static boolean canPutElementalPassive(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            String leftelement = "";
            if (leftitem.getTag().getBoolean("hasweaponart")) {
                leftelement = SkillsEnum.get(leftitem.getTag().getString("weapon_art")).getSkillElement();
            } else {
                WeaponData data = WeaponData.getWeaponData(leftitem);
                if (data != null) {
                        WeaponSkillBase skill = data.getSkill();
                        if (skill != null) {
                            leftelement = skill.getSkillElement();
                        }
                }
            }
            return leftelement.equals("Neutral") || leftelement.equals(rightitem.getTag().getString("passive_element"));
        }
        return false;
    }

    public static boolean canPutWeaponArt(ItemStack leftitem, ItemStack rightitem) {
        if (leftitem != null && rightitem != null) {
            String rightelement = SkillsEnum.get(rightitem.getTag().getString("weapon_art")).getSkillElement();
            String leftelement = getItemElement(leftitem);
            return rightelement.equals("Neutral") || leftelement.isEmpty() || leftelement.equals(rightelement);
        }
        return false;
    }

    public static String getItemElement(ItemStack item) {
        String element = "";
        if (item != null) {
            if (item.getTag() != null) {
                element = item.getTag().getString("passive_element");
                if (ElementsEnum.get(element) == null) {
                    WeaponData data = WeaponData.getWeaponData(item);
                    if (data != null) {
                        if (data.getElement() != null) {
                            if (ElementsEnum.get((data.getElement())) != null) {
                                element = data.getElement();
                            }
                        }
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
                setElement(ent, "Darkness");
            } else if (chance >= 1 && chance <= 10) {
                setElement(ent, "Light");
            } else if (chance >= 11 && chance <= 40) {
                setElement(ent, "Thunder");
            } else if (chance >= 41 && chance <= 90) {
                setElement(ent, "Sun");
            } else if (chance >= 91 && chance <= 140) {
                setElement(ent, "Moon");
            } else if (chance >= 141 && chance <= 240) {
                setElement(ent, "Blood");
            } else if (chance >= 241 && chance <= 340) {
                setElement(ent, "Wind");
            } else if (chance >= 341 && chance <= 560) {
                setElement(ent, "Nature");
            } else if (chance >= 561 && chance <= 780) {
                setElement(ent, "Ice");
            } else setElement(ent, "Water");
        }
    }

    public static String getElement(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {
                CompoundTag tag = TagsUtils.playerTags.get(player);
                if (tag != null) {
                    return tag.getString("Element");
                }
            }
        }
        return "";
    }

    public static boolean isNotInWater(Entity ent, Vec3 vec3) {
        if (ent instanceof LivingEntity living && vec3 != null) {
            BlockPos pos = new BlockPos((int) vec3.x, (int) vec3.y, (int) vec3.z);
            return !ent.level().getBlockState(pos).is(Blocks.WATER) && !ent.level().isRainingAt(pos) && !living.hasEffect(EffectRegister.WET.get());
        }
        return false;
    }
}
