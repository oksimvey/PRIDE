package com.robson.pride.api.data.manager;

import com.robson.pride.api.data.types.*;
import com.robson.pride.api.elements.*;
import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.entities.Ronin;
import com.robson.pride.api.item.CustomItem;
import com.robson.pride.item.weapons.EuropeanLongsword;
import com.robson.pride.item.weapons.Kuronami;
import com.robson.pride.item.weapons.Pyroscourge;
import com.robson.pride.skills.weaponarts.*;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.item.ItemStack;

public interface ServerDataManager extends ServerDataManagerImpl<GenericData> {

   //ELEMENTS ID's

   byte DARKNESS = 1;

   byte LIGHT = 2;

   byte THUNDER = 3;

   byte SUN = 4;

   byte MOON = 5;

   byte BLOOD = 6;

   byte WIND = 7;

   byte NATURE = 8;

   byte ICE = 9;

   byte WATER = 10;

   byte NEUTRAL = 11;

   //MATERIALS

   short EXAMPLE_MATERIAL = 1000;

   //WEAPONS ID's

   short KURONAMI = 2000;

   short EUROPEAN_LONGSWORD = 2001;

   short PYROSCOURGE = 2002;

   //OTHER EQUIPMENTS ID's

   short SHIELD = 3000;

   short RING = 3001;

   //WEAPON ARTS ID's

   short DARKNESS_CUT = 4000;

   short HEAVENS_STRIKE = 4001;

   short FLAME_SLASH = 4002;

   short TORNADO = 4003;

   short GROUND_STOMP = 4004;

   short KICK = 4005;

   short LONGSWORD_PIERCE = 4006;

   //ENTITIES ID's

   short RONIN = 5000;

   ServerDataManager INSTANCE = id -> switch (id) {

      //ELEMENTS DATA

      case DARKNESS -> DarknessElement.DATA;

      case LIGHT -> LightElement.DATA;

      case THUNDER -> ThunderElement.DATA;

      case SUN -> SunElement.DATA;

      case MOON -> MoonElement.DATA;

      case BLOOD -> BloodElement.DATA;

      case WIND -> WindElement.DATA;

      case NATURE -> NatureElement.DATA;

      case ICE -> IceElement.DATA;

      case WATER -> WaterElement.DATA;

      //WEAPONS DATA

      case KURONAMI -> Kuronami.WEAPON_DATA;

      case EUROPEAN_LONGSWORD -> EuropeanLongsword.WEAPON_DATA;

      case PYROSCOURGE -> Pyroscourge.WEAPON_DATA;

      //WEAPON ARTS DATA

      case DARKNESS_CUT -> DarknessCut.DATA;

      case HEAVENS_STRIKE -> HeavensStrike.DATA;

      case FLAME_SLASH -> FlameSlashSkill.DATA;

      case TORNADO -> Tornado.DATA;

      case GROUND_STOMP -> GroundStomp.DATA;

      case KICK -> Kick.DATA;

      case LONGSWORD_PIERCE -> LongSwordWeaponSkill.DATA;

      //ENTITIES DATA

      case RONIN -> Ronin.DATA;

      default -> null;
   };;

   static GenericData getGenericData(short id){
      return INSTANCE.getByID(id);
   }

   static GenericData getGenericData(ItemStack stack){
      if (stack != null && stack.getItem() instanceof CustomItem){
         return INSTANCE.getByID(stack.getOrCreateTag().getShort("pride_id"));
      }
      return null;
   }

   static ElementData getElementData(byte id){
      if (getGenericData(id) instanceof ElementData elementData){
         return elementData;
      }
      return null;
   }

   static ElementData getElementData(ItemStack stack){
      if (getGenericData(stack) instanceof ElementData elementData){
         return elementData;
      }
      return null;
   }

   static WeaponData getWeaponData(short id){
      if (getGenericData(id) instanceof WeaponData data){
         return data;
      }
      return null;
   }

   static WeaponData getWeaponData(ItemStack stack){
      if (getGenericData(stack) instanceof WeaponData data){
         return data;
      }
      return null;
   }

   static WeaponSkillData getWeaponSkillData(short id){
      if (getGenericData(id) instanceof WeaponSkillData data){
         return data;
      }
      return null;
   }

   static WeaponSkillData getWeaponSkillData(ItemStack stack){
      if (getGenericData(stack) instanceof WeaponSkillData data){
         return data;
      }
      return null;
   }

   static MobTypeData getMobType(short id){
      if (getGenericData(id) instanceof MobTypeData data){
         return data;
      }
      return null;
   }

   static MobTypeData getMobType(PrideMob mob){
      if (mob != null){
         return getMobType(mob.getTypeID());
      }
      return null;
   }

   static MobData getMobData(PrideMob mob){
      if (mob != null && getMobType(mob) != null){
         return getMobType(mob).getDataByVariant(mob.getTypeVariant());
      }
      return null;
   }
}
