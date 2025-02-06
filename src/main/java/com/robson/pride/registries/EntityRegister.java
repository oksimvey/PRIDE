package com.robson.pride.registries;

import com.robson.pride.entities.forest.hunter.Hunter;
import com.robson.pride.entities.forest.magmamonster.MagmaMonster;
import com.robson.pride.entities.japanese.boss.shogun.Shogun;
import com.robson.pride.entities.japanese.mob.ronin.Ronin;
import com.robson.pride.entities.forest.eliteknight.EliteKnight;
import com.robson.pride.entities.special.CloneEntity;
import com.robson.pride.entities.special.Shooter;
import com.robson.pride.main.Pride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Pride.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pride.MODID);

    public static final DeferredRegister<EntityType<?>> SPECIAL_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pride.MODID);

    public static final RegistryObject<EntityType<Ronin>> RONIN = make(false, new ResourceLocation("ronin"), Ronin::new, MobCategory.MONSTER, 0.8F, 2.25F);
    public static final RegistryObject<EntityType<Shogun>> SHOGUN = make(false, new ResourceLocation("shogun"), Shogun::new, MobCategory.MONSTER, 1, 2.5F);
    public static final RegistryObject<EntityType<EliteKnight>> ELITE_KNIGHT = make(false, new ResourceLocation("elite_knight"), EliteKnight::new, MobCategory.MONSTER, 0.9F, 2.25f);
    public static final RegistryObject<EntityType<CloneEntity>> CLONE_ENTITY = make(true,new ResourceLocation("clone_entity"), CloneEntity::new, MobCategory.MONSTER, 0.8f, 1.8f);
    public static final RegistryObject<EntityType<Hunter>> HUNTER = make(false, new ResourceLocation("hunter"), Hunter::new, MobCategory.MONSTER, 0.8f, 1.8f);
    public static final RegistryObject<EntityType<Shooter>> SHOOTER = make(true, new ResourceLocation("shooter"), Shooter::new, MobCategory.MONSTER, 0.25f, 0.25f);
    public static final RegistryObject<EntityType<MagmaMonster>> MAGMA_MONSTER = make(false, new ResourceLocation("magma_monster"), MagmaMonster::new, MobCategory.MONSTER, 1, 2.5f);

    private static <E extends Entity> RegistryObject<EntityType<E>> make(boolean special, ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height) {
        return make(special, id, factory, classification, width, height, false);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> make(boolean special, ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof) {
        return special ?  buildSpecial(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof) : build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof);
    }

    @SuppressWarnings("unchecked")
    private static <E extends Entity> RegistryObject<EntityType<E>> build(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        RegistryObject<EntityType<E>> ret = ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
        return ret;
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> buildSpecial(ResourceLocation id, EntityType.Builder<E> builder, boolean fireproof) {
        if (fireproof) builder.fireImmune();
        RegistryObject<EntityType<E>> ret = SPECIAL_ENTITIES.register(id.getPath(), () -> builder.build(id.toString()));
        return ret;
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
        event.put(HUNTER.get(), Hunter.registerAttributes().build());
        event.put(MAGMA_MONSTER.get(), MagmaMonster.registerAttributes().build());
        event.put(SHOOTER.get(), Shooter.registerAttributes().build());
    }
}