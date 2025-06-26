package com.robson.pride.registries;
import com.robson.pride.api.entity.PrideMob;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.main.Pride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.entitypatch.mob.ZombiePatch;

@Mod.EventBusSubscriber(modid = Pride.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pride.MOD_ID);

    public static final DeferredRegister<EntityType<?>> SPECIAL_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Pride.MOD_ID);

    public static final RegistryObject<EntityType<PrideMob>> PRIDE_MOB = make(false, new ResourceLocation("pride_mob"), PrideMob::new, MobCategory.MONSTER, 0.8f, 1.8f);



    private static <E extends Entity> RegistryObject<EntityType<E>> make(boolean special, ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height) {
        return make(special, id, factory, classification, width, height, false);
    }

    private static <E extends Entity> RegistryObject<EntityType<E>> make(boolean special, ResourceLocation id, EntityType.EntityFactory<E> factory, MobCategory classification, float width, float height, boolean fireproof) {
        return special ? buildSpecial(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof) : build(id, makeBuilder(factory, classification, width, height, 80, 3), fireproof);
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
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(EntityRegister::registerArmatures);

    }

    public static void registerArmatures(){
        Armatures.registerEntityTypeArmature(PRIDE_MOB.get(), Armatures.BIPED);


    }


    @SubscribeEvent
    public static void setPatch(EntityPatchRegistryEvent event) {
        event.getTypeEntry().put(PRIDE_MOB.get(),(entity -> PrideMobPatch::new));
    }

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(PRIDE_MOB.get(), PrideMob.registerAttributes().build());
    }
}