package com.robson.pride.registries;

import com.robson.pride.api.skillcore.WeaponArtItem;
import com.robson.pride.item.materials.Bullet;
import com.robson.pride.item.materials.ElementalGem;
import com.robson.pride.item.spawnegg.SpawnEggBase;
import com.robson.pride.item.weapons.*;
import com.robson.pride.main.Pride;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ItemsRegister {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, Pride.MODID);

    public static final RegistryObject<Item> EuropeanLongsword = REGISTRY.register("european_longsword", com.robson.pride.item.weapons.EuropeanLongsword::new);
    public static final RegistryObject<Item> BATTLE_AXE = REGISTRY.register("battle_axe", BattleAxe::new);
    public static final RegistryObject<Item> COLOSSAL_BLADE = REGISTRY.register("colossal_blade", ColossalBlade::new);
    public static final RegistryObject<Item> DAGGER = REGISTRY.register("dagger", Dagger::new);
    public static final RegistryObject<Item> ESTOC = REGISTRY.register("estoc", Estoc::new);
    public static final RegistryObject<Item> HALBERD = REGISTRY.register("halberd", Halberd::new);
    public static final RegistryObject<Item> KATANA = REGISTRY.register("katana", Katana::new);
    public static final RegistryObject<Item> SABER = REGISTRY.register("saber", Saber::new);
    public static final RegistryObject<Item> SCYTHE = REGISTRY.register("scythe", Scythe::new);
    public static final RegistryObject<Item> SPEAR = REGISTRY.register("spear", Spear::new);
    public static final RegistryObject<Item> HOSHIGIRI = REGISTRY.register("hoshigiri", Hoshigiri::new);
    public static final RegistryObject<Item> PYROSCOURGE = REGISTRY.register("pyroscourge", Pyroscourge::new);
    public static final RegistryObject<Item> NEPTARION = REGISTRY.register("neptarion", Neptarion::new);
    public static final RegistryObject<Item> SCARVIELLE = REGISTRY.register("scarvielle", Scarvielle::new);
    public static final RegistryObject<Item> KURONAMI = REGISTRY.register("kuronami", Kuronami::new);
    public static final RegistryObject<Item> SERAPHIEL = REGISTRY.register("seraphiel", Seraphiel::new);
    public static final RegistryObject<Item> COWBOY_REVOLVER = REGISTRY.register("cowboy_revolver", CowboyRevolver::new);
    public static final RegistryObject<Item> KICK_BOX = REGISTRY.register("kickbox", com.robson.pride.item.fightstyles.KickBoxItem::new);
    public static final RegistryObject<Item> BULLET = REGISTRY.register("bullet",
            () -> new Bullet(new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> ELEMENTAL_GEM = REGISTRY.register("elemental_gem",
            () -> new ElementalGem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WEAPON_ART = REGISTRY.register("weapon_art",
            () -> new WeaponArtItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SPAWN_EGG = REGISTRY.register("spawn_egg",
            () -> new SpawnEggBase(new Item.Properties().stacksTo(64)));
}
