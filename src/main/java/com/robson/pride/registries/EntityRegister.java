package com.robson.pride.registries;

import com.robson.pride.entities.pre_hardmode.japanese.boss.shogun.Shogun;
import com.robson.pride.entities.pre_hardmode.japanese.mob.ronin.Ronin;
import com.robson.pride.entities.pre_hardmode.knight.mob.eliteknight.EliteKnight;
import com.robson.pride.entities.special.CloneEntity;
import com.robson.pride.main.Pride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Pride.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pride.MODID);
    public static final DeferredRegister<Item> SPAWN_EGGS = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MODID);

    public static final RegistryObject<EntityType<Ronin>> RONIN = make(new ResourceLocation("ronin"), Ronin::new, MobCategory.MONSTER, 0.8F, 2.25F, 0x0a0000, 0x00008b);
    public static final RegistryObject<EntityType<Shogun>> SHOGUN = make(new ResourceLocation("shogun"), Shogun::new, MobCategory.MONSTER, 1, 2.5F, 0xcdcdcd, 0x29486e);
    public static final RegistryObject<EntityType<EliteKnight>> ELITE_KNIGHT = make(new ResourceLocation("elite_knight"), EliteKnight::new, MobCategory.MONSTER, 0.9F, 2.25f, 0x239391, 0x9a9a9a);
    public static final RegistryObject<EntityType<CloneEntity>> CLONE_ENTITY = make(new ResourceLocation("clone_entity"),  CloneEntity::new, MobCategory.MONSTER, 0.8f, 1.8f, 0xd3e7bc, 0x1f3fff);

    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int primary, int secondary) {
        return make(id, factory, classification, width, height, false, primary, secondary);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> make(ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof, int primary, int secondary) {
        return build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof, primary, secondary);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> buildNoEgg(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        return ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> RegistryObject<EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof, int primary, int secondary) {
        if (fireproof) builder.fireImmune();
        RegistryObject<EntityType<E>> ret = ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
        if (primary != 0 && secondary != 0) {
            SPAWN_EGGS.register(id.getPath() + "_spawn_egg", () -> new ForgeSpawnEggItem(() -> (EntityType<? extends Mob>) ret.get(), primary, secondary, new Item.Properties()));
        }
        return ret;
    }

    private static <E extends Entity> EntityType.Builder<E> makeCastedBuilder(@SuppressWarnings("unused") Class<E> cast, EntityType.EntityFactory<E> factory, float width, float height, int range, int interval) {
        return makeBuilder(factory, MobCategory.MISC, width, height, range, interval);
    }

    private static <E extends Entity> EntityType.Builder<E> makeBuilder(EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, int range, int interval) {
        return EntityType.Builder.of(factory, classification).
                sized(width, height).
                setTrackingRange(range).
                setUpdateInterval(interval).
                setShouldReceiveVelocityUpdates(true);
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(RONIN.get(), Ronin.registerAttributes().build());
        event.put(SHOGUN.get(), Shogun.registerAttributes().build());
        event.put(ELITE_KNIGHT.get(), EliteKnight.registerAttributes().build());
        event.put(CLONE_ENTITY.get(), CloneEntity.registerAttributes().build());
      }
}