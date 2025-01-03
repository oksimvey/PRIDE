package com.robson.pride.mechanics;

import com.robson.pride.api.utils.*;
import com.robson.pride.registries.ParticleRegister;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ParticleTracking {

    public static void auraImbuementTrackingCore(){
        if (Minecraft.getInstance().player != null) {
            Entity ent = Minecraft.getInstance().player;
            if (Minecraft.getInstance().player.getMainHandItem().getTag()!= null) {
                if (ParticleTracking.shouldRenderParticle(Minecraft.getInstance().player.getMainHandItem(), Minecraft.getInstance().player)) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR, ParticleTracking.getParticle(Minecraft.getInstance().player.getMainHandItem()),getAABBForImbuement(Minecraft.getInstance().player.getMainHandItem(), Minecraft.getInstance().player));
                }
                if (ParticleTracking.shouldRenderParticle(Minecraft.getInstance().player.getOffhandItem(), Minecraft.getInstance().player)) {
                    ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, ent, Armatures.BIPED.toolL, ParticleTracking.getParticle(Minecraft.getInstance().player.getOffhandItem()),getAABBForImbuement(Minecraft.getInstance().player.getOffhandItem(), Minecraft.getInstance().player));
                }
            }
            AABB minMax = MathUtils.createAABBForCulling(10);
            if (ent.level() != null) {
                List<Entity> listent = ent.level().getEntities(ent, minMax);
                for (Entity entko : listent) {
                    if (entko instanceof LivingEntity living){
                        if (ParticleTracking.shouldRenderParticle(living.getMainHandItem(), living)){
                            ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, living, Armatures.BIPED.toolR, ParticleTracking.getParticle(living.getMainHandItem()), getAABBForImbuement(living.getMainHandItem(), living));
                        }
                        if (ParticleTracking.shouldRenderParticle(living.getOffhandItem(), living)){
                            ParticleUtils.spawnParticleTracked(Minecraft.getInstance().player, living, Armatures.BIPED.toolL, ParticleTracking.getParticle(living.getOffhandItem()), getAABBForImbuement(living.getOffhandItem(), living));
                        }
                    }
               }
            }
        }
    }

    public static boolean shouldRenderParticle(ItemStack item, Entity ent){
        if (item != null && ent instanceof LivingEntity living) {
            if (item.getTag() != null) {
                String element = item.getTag().getString("passive_element");
                if (Objects.equals(element, "Sun") || TagCheckUtils.itemsTagCheck(item, "passives/sun")){
                    Vec3 vec3 = ArmatureUtils.getJoinPosition(Minecraft.getInstance().player, ent, Armatures.BIPED.toolR);
                    if (vec3 != null) {
                        return ElementalUtils.isNotInWater(ent, vec3);
                    }
                }
                else return
                        Objects.equals(element, "Darkness") ||
                        Objects.equals(element, "Light") ||
                        Objects.equals(element, "Thunder") ||
                        Objects.equals(element, "Moon") ||
                        Objects.equals(element, "Blood") ||
                        Objects.equals(element, "Wind") ||
                        Objects.equals(element, "Nature") ||
                        Objects.equals(element, "Ice") ||
                        Objects.equals(element, "Water") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/darkness") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/light") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/thunder") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/moon") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/blood") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/wind") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/nature") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/ice") ||
                        TagCheckUtils.itemsTagCheck(item, "passives/water");
            }
        }
        return false;
    }

    public static ParticleOptions getParticle(ItemStack item) {
        if (item != null && Minecraft.getInstance().player != null) {
            String element = item.getTag().getString("passive_element");
            if (Objects.equals(element, "Darkness") || TagCheckUtils.itemsTagCheck(item, "passives/darkness")) {
                Random random = new Random();
                if (random.nextInt(20) == 1){
                    return ParticleRegister.RED_LIGHTNING.get();
                }
                else return ParticleTypes.SMOKE;
            }
            if (Objects.equals(element, "Light") || TagCheckUtils.itemsTagCheck(item, "passives/light")) {
                return ParticleRegistry.WISP_PARTICLE.get();
            }
            if (Objects.equals(element, "Thunder") || TagCheckUtils.itemsTagCheck(item, "passives/thunder")) {
                return ParticleRegistry.ELECTRICITY_PARTICLE.get();
            }
            if (Objects.equals(element, "Sun") || TagCheckUtils.itemsTagCheck(item, "passives/sun")) {
                return ParticleRegistry.FIRE_PARTICLE.get();
            }
            if (Objects.equals(element, "Moon") || TagCheckUtils.itemsTagCheck(item, "passives/moon")) {
                return ParticleTypes.DRAGON_BREATH;
            }
            if (Objects.equals(element, "Blood") || TagCheckUtils.itemsTagCheck(item, "passives/blood")) {
                return ParticleRegistry.BLOOD_PARTICLE.get();
            }
            if (Objects.equals(element, "Wind") || TagCheckUtils.itemsTagCheck(item, "passives/wind")) {
                return ParticleTypes.CLOUD;
            }
            if (Objects.equals(element, "Nature") || TagCheckUtils.itemsTagCheck(item, "passives/nature")) {
                return ParticleTypes.COMPOSTER;
            }
            if (Objects.equals(element, "Ice") || TagCheckUtils.itemsTagCheck(item, "passives/ice")) {
                return ParticleRegistry.SNOWFLAKE_PARTICLE.get();
            }
            if (Objects.equals(element, "Water") || TagCheckUtils.itemsTagCheck(item, "passives/water")) {
                return new DustParticleOptions(new Vec3(0.3f, 0.5f, 1).normalize().toVector3f(), 1f);
            }
        }
        return null;
    }

    public static Vec3f getAABBForImbuement(ItemStack item, Entity ent){
        if (item != null){
            if (TagCheckUtils.itemsTagCheck(item, "tracking_aabb/longsword")) {
                return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.5F ) * 0.8F + 0.08F, -((new Random()).nextFloat() * (1.5F * ent.getBbHeight() / 1.8F)) - 0.15F);
            }
        }
        return new Vec3f(((new Random()).nextFloat() - 0.5F) * 0.2F, ((new Random()).nextFloat() - 0.3F) * 0.8F, ((new Random()).nextFloat() - 0.5F) * 0.2F);
    }
}
