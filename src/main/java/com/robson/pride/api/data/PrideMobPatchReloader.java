package com.robson.pride.api.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.nameless.indestructible.api.animation.types.LivingEntityPatchEvent;
import com.nameless.indestructible.data.AdvancedMobpatchReloader;
import com.nameless.indestructible.gameasset.GuardAnimations;
import com.nameless.indestructible.main.Indestructible;

import java.util.*;
import java.util.stream.Stream;

import com.nameless.indestructible.world.ai.CombatBehaviors.*;
import com.nameless.indestructible.world.capability.AdvancedCustomMobPatch;
import com.robson.pride.api.entity.PrideMobPatch;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.data.reloader.MobPatchReloadListener;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.entitypatch.*;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.capabilities.provider.EntityPatchProvider;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors;
import yesman.epicfight.world.entity.ai.goal.CombatBehaviors.BehaviorSeries;

import static com.nameless.indestructible.data.AdvancedMobpatchReloader.*;

public class PrideMobPatchReloader extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).create();
    public static final Map<EntityType<?>, CompoundTag> MOB_TAGS = Maps.newHashMap();
    public static final Map<EntityType<?>, MobPatchReloadListener.AbstractMobPatchProvider> ADVANCED_MOB_PATCH_PROVIDERS = Maps.newHashMap();

    public PrideMobPatchReloader() {
        super(GSON, "pride_mobpatch");
    }

    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager resourceManager, ProfilerFiller profileIn) {
        ADVANCED_MOB_PATCH_PROVIDERS.clear();
        MOB_TAGS.clear();
        return super.prepare(resourceManager, profileIn);
    }

    public static MobPatchReloadListener.AbstractMobPatchProvider deserializePatchProvider(EntityType<?> entityType, CompoundTag tag, boolean clientSide, ResourceManager resourceManager) {
        boolean humanoid = tag.getBoolean("isHumanoid");
        return (MobPatchReloadListener.AbstractMobPatchProvider) (humanoid ? deserializeHumaniodMobPatchProvider(entityType, tag, clientSide, resourceManager) : deserializeMobPatchProvider(entityType, tag, clientSide, resourceManager));
    }

    protected void apply(Map<ResourceLocation, JsonElement> objectIn, ResourceManager resourceManagerIn, ProfilerFiller profilerIn) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : objectIn.entrySet()) {
            ResourceLocation rl = (ResourceLocation) entry.getKey();
            String pathString = rl.getPath();
            ResourceLocation registryName = new ResourceLocation(rl.getNamespace(), pathString);
            if (!ForgeRegistries.ENTITY_TYPES.containsKey(registryName)) {
                Indestructible.LOGGER.warn("[Custom Entity] Entity named " + registryName + " does not exist");
            } else {
                EntityType<?> entityType = (EntityType) ForgeRegistries.ENTITY_TYPES.getValue(registryName);
                CompoundTag tag = null;

                try {
                    tag = TagParser.parseTag(((JsonElement) entry.getValue()).toString());
                } catch (CommandSyntaxException e) {
                    e.printStackTrace();
                }
                ADVANCED_MOB_PATCH_PROVIDERS.put(entityType, deserializePatchProvider(entityType, tag, false, resourceManagerIn));
                EntityPatchProvider.putCustomEntityPatch(entityType, (entity) -> () -> ((MobPatchReloadListener.AbstractMobPatchProvider) ADVANCED_MOB_PATCH_PROVIDERS.get(entity.getType())).get(entity));
                MOB_TAGS.put(entityType, filterClientData(tag));
                if (EpicFightMod.isPhysicalClient()) {
                    ClientEngine.getInstance().renderEngine.registerCustomEntityRenderer(entityType, tag.contains("preset") ? tag.getString("preset") : tag.getString("renderer"), tag);
                }
            }
        }
    }

    public static PrideMobPatchReloader.PrideCustomMobPatchProvider deserializeMobPatchProvider(EntityType<?> entityType, CompoundTag tag, boolean clientSide, ResourceManager resourceManager) {
        PrideCustomMobPatchProvider provider = new PrideCustomMobPatchProvider();
        provider.attributeValues = deserializeAdvancedAttributes(tag.getCompound("attributes"));
        ResourceLocation modelLocation = new ResourceLocation(tag.getString("model"));
        ResourceLocation armatureLocation = new ResourceLocation(tag.getString("armature"));
        if (EpicFightMod.isPhysicalClient()) {
            Meshes.getOrCreateAnimatedMesh(Minecraft.getInstance().getResourceManager(), modelLocation, HumanoidMesh::new);
            provider.name = tag.contains("boss_bar") && tag.contains("custom_name") ? tag.getString("custom_name") : null;
            provider.bossBar = tag.contains("boss_bar") && tag.contains("custom_texture") ? ResourceLocation.tryParse(tag.getString("custom_texture")) : null;
        }

        Armature armature = Armatures.getOrCreateArmature(resourceManager, armatureLocation, HumanoidArmature::new);
        Armatures.registerEntityTypeArmature(entityType, armature);
        provider.hasBossBar = tag.contains("boss_bar") && tag.getBoolean("boss_bar");
        provider.defaultAnimations = MobPatchReloadListener.deserializeDefaultAnimations(tag.getCompound("default_livingmotions"));
        provider.faction = Faction.valueOf(tag.getString("faction").toUpperCase(Locale.ROOT));
        provider.scale = tag.getCompound("attributes").contains("scale") ? (float) tag.getCompound("attributes").getDouble("scale") : 1.0F;
        provider.maxStunShield = tag.getCompound("attributes").contains("max_stun_shield") ? (float) tag.getCompound("attributes").getDouble("max_stun_shield") : 0.0F;
        if (tag.contains("swing_sound")) {
            provider.swingSound = (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(tag.getString("swing_sound")));
        }

        if (tag.contains("hit_sound")) {
            provider.hitSound = (SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(tag.getString("hit_sound")));
        }

        if (tag.contains("hit_particle")) {
            provider.hitParticle = (HitParticleType) ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(tag.getString("hit_particle")));
        }

        if (!clientSide) {
            provider.stunAnimations = MobPatchReloadListener.deserializeStunAnimations(tag.getCompound("stun_animations"));
            provider.chasingSpeed = tag.getCompound("attributes").getDouble("chasing_speed");
            provider.defaultGuardMotion = deserializeGuardMotions(tag.getCompound("custom_guard_motion"));
            provider.combatBehaviorsBuilder = deserializeAdvancedBehaviorsBuilder(tag.getList("combat_behavior", 10));
            provider.regenStaminaStandbyTime = tag.getCompound("attributes").contains("stamina_regan_delay") ? tag.getCompound("attributes").getInt("stamina_regan_delay") : 30;
            provider.hasStunReduction = !tag.getCompound("attributes").contains("has_stun_reduction") || tag.getCompound("attributes").getBoolean("has_stun_reduction");
            provider.reganShieldStandbyTime = tag.getCompound("attributes").contains("stun_shield_regan_delay") ? tag.getCompound("attributes").getInt("stun_shield_regan_delay") : 30;
            provider.reganShieldMultiply = tag.getCompound("attributes").contains("stun_shield_regan_multiply") ? (float) tag.getCompound("attributes").getDouble("stun_shield_multiply") : 1.0F;
            provider.staminaLoseMultiply = tag.getCompound("attributes").contains("stamina_lose_multiply") ? (float) tag.getCompound("attributes").getDouble("stamina_lose_multiply") : 0.0F;
            provider.attackRadius = tag.getCompound("attributes").contains("attack_radius") ? (float) tag.getCompound("attributes").getDouble("attack_radius") : 1.5F;
            provider.guardRadius = tag.getCompound("attributes").contains("guard_radius") ? (float) tag.getCompound("attributes").getDouble("guard_radius") : 3.0F;
            provider.stunEvent = deserializeStunCommandList(tag.getList("stun_command_list", 10));
        }

        return provider;
    }

    public static PrideCustomHumanoidMobPatchProvider deserializeHumaniodMobPatchProvider(EntityType<?> entityType, CompoundTag tag, boolean clientSide, ResourceManager resourceManager) {
        PrideCustomHumanoidMobPatchProvider provider = new PrideCustomHumanoidMobPatchProvider();
        provider.attributeValues = deserializeAdvancedAttributes(tag.getCompound("attributes"));
        ResourceLocation modelLocation = new ResourceLocation(tag.getString("model"));
        ResourceLocation armatureLocation = new ResourceLocation(tag.getString("armature"));
        if (EpicFightMod.isPhysicalClient()) {
            Meshes.getOrCreateAnimatedMesh(Minecraft.getInstance().getResourceManager(), modelLocation, HumanoidMesh::new);
            provider.name = tag.contains("boss_bar") && tag.contains("custom_name") ? tag.getString("custom_name") : null;
            provider.bossBar = tag.contains("boss_bar") && tag.contains("custom_texture") ? ResourceLocation.tryParse(tag.getString("custom_texture")) : null;
        }

        Armature armature = Armatures.getOrCreateArmature(resourceManager, armatureLocation, HumanoidArmature::new);
        Armatures.registerEntityTypeArmature(entityType, armature);
        provider.hasBossBar = tag.contains("boss_bar") && tag.getBoolean("boss_bar");
        provider.defaultAnimations = MobPatchReloadListener.deserializeDefaultAnimations(tag.getCompound("default_livingmotions"));
        provider.faction = Faction.valueOf(tag.getString("faction").toUpperCase(Locale.ROOT));
        provider.scale = tag.getCompound("attributes").contains("scale") ? (float)tag.getCompound("attributes").getDouble("scale") : 1.0F;
        if (tag.contains("swing_sound")) {
            provider.swingSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(tag.getString("swing_sound")));
        }

        if (tag.contains("hit_sound")) {
            provider.hitSound = (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(tag.getString("hit_sound")));
        }

        if (tag.contains("hit_particle")) {
            provider.hitParticle = (HitParticleType)ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(tag.getString("hit_particle")));
        }

        if (!clientSide) {
            provider.stunAnimations = MobPatchReloadListener.deserializeStunAnimations(tag.getCompound("stun_animations"));
            provider.chasingSpeed = tag.getCompound("attributes").getDouble("chasing_speed");
            provider.AHCombatBehaviors = deserializeAdvancedHumanoidCombatBehaviors(tag.getList("combat_behavior", 10));
            provider.AHWeaponMotions = MobPatchReloadListener.deserializeHumanoidWeaponMotions(tag.getList("humanoid_weapon_motions", 10));
            provider.guardMotions = deserializeHumanoidGuardMotions(tag.getList("custom_guard_motion", 10));
            provider.regenStaminaStandbyTime = tag.getCompound("attributes").contains("stamina_regan_delay") ? tag.getCompound("attributes").getInt("stamina_regan_delay") : 30;
            provider.hasStunReduction = !tag.getCompound("attributes").contains("has_stun_reduction") || tag.getCompound("attributes").getBoolean("has_stun_reduction");
            provider.maxStunShield = tag.getCompound("attributes").contains("max_stun_shield") ? (float)tag.getCompound("attributes").getDouble("max_stun_shield") : 0.0F;
            provider.reganShieldStandbyTime = tag.getCompound("attributes").contains("stun_shield_regan_delay") ? tag.getCompound("attributes").getInt("stun_shield_regan_delay") : 30;
            provider.reganShieldMultiply = tag.getCompound("attributes").contains("stun_shield_regan_multiply") ? (float)tag.getCompound("attributes").getDouble("stun_shield_multiply") : 1.0F;
            provider.staminaLoseMultiply = tag.getCompound("attributes").contains("stamina_lose_multiply") ? (float)tag.getCompound("attributes").getDouble("stamina_lose_multiply") : 0.0F;
            provider.attackRadius = tag.getCompound("attributes").contains("attack_radius") ? (float)tag.getCompound("attributes").getDouble("attack_radius") : 1.5F;
            provider.guardRadius = tag.getCompound("attributes").contains("guard_radius") ? (float)tag.getCompound("attributes").getDouble("guard_radius") : 3.0F;
            provider.stunEvent = deserializeStunCommandList(tag.getList("stun_command_list", 10));
        }

        return provider;
    }

    private static <T extends MobPatch<?>> CombatBehaviors.Builder<T> deserializeAdvancedBehaviorsBuilder(ListTag tag) {
        CombatBehaviors.Builder<T> builder = CombatBehaviors.builder();

        for(int i = 0; i < tag.size(); ++i) {
            CompoundTag behaviorSeries = tag.getCompound(i);
            float weight = (float)behaviorSeries.getDouble("weight");
            int cooldown = behaviorSeries.contains("cooldown") ? behaviorSeries.getInt("cooldown") : 0;
            boolean canBeInterrupted = behaviorSeries.contains("canBeInterrupted") && behaviorSeries.getBoolean("canBeInterrupted");
            boolean looping = behaviorSeries.contains("looping") && behaviorSeries.getBoolean("looping");
            ListTag behaviorList = behaviorSeries.getList("behaviors", 10);
            CombatBehaviors.BehaviorSeries.Builder<T> behaviorSeriesBuilder = BehaviorSeries.builder();
            behaviorSeriesBuilder.weight(weight).cooldown(cooldown).canBeInterrupted(canBeInterrupted).looping(looping);

            for(int j = 0; j < behaviorList.size(); ++j) {
                AdvancedBehaviorBuilder<T> behaviorBuilder = new AdvancedBehaviorBuilder();
                CompoundTag behavior = behaviorList.getCompound(j);
                ListTag conditionList = behavior.getList("conditions", 10);
                if (behavior.contains("set_phase")) {
                    behaviorBuilder.tryProcessSetPhase(behavior.getInt("set_phase"));
                }

                if (behavior.contains("end_by_hurt_level")) {
                    behaviorBuilder.tryProcessSetHurtResistLevel(behavior.getInt("end_by_hurt_level"));
                }

                if (behavior.contains("animation")) {
                    StaticAnimation animation = AnimationManager.getInstance().byKeyOrThrow(behavior.getString("animation"));
                    AnimationMotionSet motionSet = new AnimationMotionSet(animation, 0.0F, 1.0F, 0.0F);
                    motionSet = behavior.contains("play_speed") ? motionSet.setSpeed((float)behavior.getDouble("play_speed")) : motionSet;
                    motionSet = behavior.contains("stamina") ? motionSet.setStaminaCost((float)behavior.getDouble("stamina")) : motionSet;
                    motionSet = behavior.contains("convert_time") ? motionSet.setConvertTime((float)behavior.getDouble("convert_time")) : motionSet;
                    motionSet = behavior.contains("damage_modifier") && !behavior.getCompound("damage_modifier").isEmpty() ? motionSet.setDamageSourceModifier(deserializeDamageModifier(behavior.getCompound("damage_modifier"))) : motionSet;
                    motionSet = behavior.contains("command_list") && !behavior.getList("command_list", 10).isEmpty() ? motionSet.addTimeStampedEvents(deserializeTimeCommandList(behavior.getList("command_list", 10))) : motionSet;
                    motionSet = behavior.contains("hit_command_list") && !behavior.getList("hit_command_list", 10).isEmpty() ? motionSet.addHitEvents(deserializeHitCommandList(behavior.getList("hit_command_list", 10))) : motionSet;
                    motionSet = behavior.contains("blocked_command_list") && !behavior.getList("blocked_command_list", 10).isEmpty() ? motionSet.addBlockedEvents(deserializeBlockedCommandList(behavior.getList("blocked_command_list", 10))) : motionSet;
                    behaviorBuilder.tryProcessAnimationSet(motionSet);
                } else if (behavior.contains("guard")) {
                    int guard_time = behavior.getInt("guard");
                    GuardMotionSet motionSet = new GuardMotionSet(guard_time, 0, 0);
                    CounterMotion counterMotion = new CounterMotion(GuardAnimations.MOB_COUNTER_ATTACK, 3.0F, 0.3F, 1.0F, 0.0F, true);
                    motionSet = behavior.contains("parry_times") ? motionSet.setParryTimes(behavior.getInt("parry_times")) : motionSet;
                    motionSet = behavior.contains("stun_immunity_time") ? motionSet.setStunImmunityTime(behavior.getInt("stun_immunity_time")) : motionSet;
                    counterMotion = behavior.contains("counter") ? counterMotion.setCounterAnimation(behavior.getString("counter")) : counterMotion;
                    counterMotion = behavior.contains("counter_cost") ? counterMotion.setCost((float)behavior.getDouble("counter_cost")) : counterMotion;
                    counterMotion = behavior.contains("counter_chance") ? counterMotion.setChance((float)behavior.getDouble("counter_chance")) : counterMotion;
                    counterMotion = behavior.contains("counter_speed") ? counterMotion.setSpeed((float)behavior.getDouble("counter_speed")) : counterMotion;
                    counterMotion = behavior.contains("counter_convert_time") ? counterMotion.setConvertTime((float)behavior.getDouble("counter_convert_time")) : counterMotion;
                    counterMotion = behavior.contains("cancel_after_counter") ? counterMotion.cancelBlock(behavior.getBoolean("cancel_after_counter")) : counterMotion;
                    motionSet = motionSet.setCounterMotion(counterMotion);
                    motionSet = behavior.contains("specific_guard_motion") ? motionSet.setSpecificGuardMotion(deserializeGuardMotions(behavior.getCompound("specific_guard_motion"))) : motionSet;
                    behaviorBuilder.tryProcessGuardMotion(motionSet);
                } else if (behavior.contains("wander")) {
                    int strafingTime = behavior.getInt("wander");
                    WanderMotionSet motionSet = new WanderMotionSet(strafingTime, strafingTime, 0.0F, 0.0F);
                    motionSet = behavior.contains("inaction_time") ? motionSet.setInactionTime(behavior.getInt("inaction_time")) : motionSet;
                    motionSet = behavior.contains("z_axis") ? motionSet.setForwardDirection((float)behavior.getDouble("z_axis")) : motionSet;
                    motionSet = behavior.contains("x_axis") ? motionSet.setClockwise((float)behavior.getDouble("x_axis")) : motionSet;
                    behaviorBuilder.tryProcessWanderSet(motionSet);
                }

                behaviorBuilder.process();

                for(int k = 0; k < conditionList.size(); ++k) {
                    CompoundTag condition = conditionList.getCompound(k);
                    Condition<T> predicate = MobPatchReloadListener.deserializeBehaviorPredicate(condition.getString("predicate"), condition);
                    behaviorBuilder.predicate(predicate);
                }

                behaviorSeriesBuilder.nextBehavior(behaviorBuilder);
            }

            builder.newBehaviorSeries(behaviorSeriesBuilder);
        }
        return builder;
    }

    public static CompoundTag filterClientData(CompoundTag tag) {
        CompoundTag clientTag = new CompoundTag();
        extractBranch(clientTag, tag);
        return clientTag;
    }

    public static CompoundTag extractBranch(CompoundTag extract, CompoundTag original) {
        extract.put("model", original.get("model"));
        extract.put("armature", original.get("armature"));
        extract.putBoolean("isHumanoid", original.contains("isHumanoid") ? original.getBoolean("isHumanoid") : false);
        extract.put("renderer", original.get("renderer"));
        extract.put("faction", original.get("faction"));
        extract.put("default_livingmotions", original.get("default_livingmotions"));
        extract.put("attributes", original.get("attributes"));
        if (original.contains("interaction_behaviors")) {
            extract.put("interaction_behaviors", original.getList("interaction_behaviors", 10));
        }
        if (original.contains("level")) {
            extract.putByte("level", original.getByte("level"));
        }
        if (original.contains("custom_music")) {
            extract.putString("custom_music", original.getString("custom_music"));
        }
        if (original.contains("music_priority")){
            extract.putByte("music_priority", original.getByte("music_priority"));
        }
        if (original.contains("textures")) {
            extract.put("textures", original.getList("textures", 8));
        }
        if (original.contains("targets")) {
            extract.put("targets", original.getList("targets", 8));
        }
        if (original.contains("allies")) {
            extract.put("allies", original.getList("allies", 8));
        }
        if (original.contains("goals")) {
            extract.put("goals", original.getList("goals", 10));
        }
        if (original.contains("skills")) {
            extract.put("skills", original.getCompound("skills"));
        }
        if (original.contains("equipment")) {
            extract.put("equipment", original.getList("equipment", 10));
        }
        extract.putByte("variations", original.contains("variations") ? original.getByte("variations") : 1);
        if (original.contains("boss_bar")) {
            extract.put("boss_bar", original.get("boss_bar"));
            if (original.contains("custom_name")) {
                extract.put("custom_name", original.get("custom_name"));
            }
            if (original.contains("custom_texture")) {
                extract.put("custom_texture", original.get("custom_texture"));
            }
        }

        return extract;
    }

    public static Stream<CompoundTag> getDataStream() {
        Stream<CompoundTag> tagStream = MOB_TAGS.entrySet().stream().map((entry) -> {
            ((CompoundTag) entry.getValue()).putString("id", ForgeRegistries.ENTITY_TYPES.getKey((EntityType) entry.getKey()).toString());
            return (CompoundTag) entry.getValue();
        });
        return tagStream;
    }

    public static int getTagCount() {
        return MOB_TAGS.size();
    }

    public static class PrideCustomMobPatchProvider extends AdvancedMobpatchReloader.AdvancedCustomMobPatchProvider {
        protected Faction faction;
        protected CombatBehaviors.Builder<MobPatch<?>> combatBehaviorsBuilder;
        protected int regenStaminaStandbyTime;
        protected boolean hasStunReduction;
        protected float maxStunShield;
        protected int reganShieldStandbyTime;
        protected float reganShieldMultiply;
        protected float staminaLoseMultiply;
        protected float guardRadius;
        protected float attackRadius;
        protected List<Pair<LivingMotion, StaticAnimation>> defaultAnimations;
        protected Map<StunType, StaticAnimation> stunAnimations;
        public Map<Attribute, Double> attributeValues;
        protected double chasingSpeed;
        protected float scale;
        protected boolean hasBossBar;
        protected ResourceLocation bossBar;
        protected String name;
        protected GuardMotion defaultGuardMotion;
        protected List<LivingEntityPatchEvent.StunEvent> stunEvent;
        protected SoundEvent swingSound;
        protected SoundEvent hitSound;
        protected HitParticleType hitParticle;

        public PrideCustomMobPatchProvider() {
            this.faction = Faction.NEUTRAL;
            this.regenStaminaStandbyTime = 30;
            this.hasStunReduction = true;
            this.maxStunShield = 0.0F;
            this.reganShieldStandbyTime = 30;
            this.reganShieldMultiply = 1.0F;
            this.staminaLoseMultiply = 0.0F;
            this.guardRadius = 3.0F;
            this.attackRadius = 1.5F;
            this.defaultAnimations = new ArrayList();
            this.stunAnimations = Maps.newHashMap();
            this.attributeValues = Maps.newHashMap();
            this.chasingSpeed = (double) 1.0F;
            this.scale = 1.0F;
            this.hasBossBar = false;
            this.stunEvent = new ArrayList();
            this.swingSound = (SoundEvent) EpicFightSounds.WHOOSH.get();
            this.hitSound = (SoundEvent) EpicFightSounds.BLUNT_HIT.get();
            this.hitParticle = (HitParticleType) EpicFightParticles.HIT_BLUNT.get();
        }

        public EntityPatch<?> get(Entity entity) {
            return new AdvancedCustomMobPatch(this.faction, this);
        }

        public List<Pair<LivingMotion, StaticAnimation>> getDefaultAnimations() {
            return this.defaultAnimations;
        }

        public Map<StunType, StaticAnimation> getStunAnimations() {
            return this.stunAnimations;
        }

        public Map<Attribute, Double> getAttributeValues() {
            return this.attributeValues;
        }

        public double getChasingSpeed() {
            return this.chasingSpeed;
        }

        public float getScale() {
            return this.scale;
        }

        public int getRegenStaminaStandbyTime() {
            return this.regenStaminaStandbyTime;
        }

        public boolean hasStunReduction() {
            return this.hasStunReduction;
        }

        public float getMaxStunShield() {
            return this.maxStunShield;
        }

        public int getReganShieldStandbyTime() {
            return this.reganShieldStandbyTime;
        }

        public float getReganShieldMultiply() {
            return this.reganShieldMultiply;
        }

        public float getStaminaLoseMultiply() {
            return this.staminaLoseMultiply;
        }

        public float getGuardRadius() {
            return this.guardRadius;
        }

        public float getAttackRadius() {
            return this.attackRadius;
        }

        public List<LivingEntityPatchEvent.StunEvent> getStunEvent() {
            return this.stunEvent;
        }

        public boolean hasBossBar() {
            return this.hasBossBar;
        }

        public String getName() {
            return this.name;
        }

        public ResourceLocation getBossBar() {
            return this.bossBar;
        }

        public CombatBehaviors.Builder<MobPatch<?>> getCombatBehaviorsBuilder() {
            return this.combatBehaviorsBuilder;
        }

        public GuardMotion getGuardMotion() {
            return this.defaultGuardMotion;
        }

        public SoundEvent getSwingSound() {
            return this.swingSound;
        }

        public SoundEvent getHitSound() {
            return this.hitSound;
        }

        public HitParticleType getHitParticle() {
            return this.hitParticle;
        }
    }

    public static class PrideCustomHumanoidMobPatchProvider extends AdvancedMobpatchReloader.AdvancedCustomHumanoidMobPatchProvider {
        protected Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> AHCombatBehaviors = Maps.newHashMap();
        protected Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, StaticAnimation>>>> AHWeaponMotions = Maps.newHashMap();
        protected Map<WeaponCategory, Map<Style, GuardMotion>> guardMotions = Maps.newHashMap();
        protected Faction faction;
        protected CombatBehaviors.Builder<MobPatch<?>> combatBehaviorsBuilder;
        protected int regenStaminaStandbyTime;
        protected boolean hasStunReduction;
        protected float maxStunShield;
        protected int reganShieldStandbyTime;
        protected float reganShieldMultiply;
        protected float staminaLoseMultiply;
        protected float guardRadius;
        protected float attackRadius;
        protected List<Pair<LivingMotion, StaticAnimation>> defaultAnimations;
        protected Map<StunType, StaticAnimation> stunAnimations;
        public Map<Attribute, Double> attributeValues;
        protected double chasingSpeed;
        protected float scale;
        protected boolean hasBossBar;
        protected ResourceLocation bossBar;
        protected String name;
        protected GuardMotion defaultGuardMotion;
        protected List<LivingEntityPatchEvent.StunEvent> stunEvent;
        protected SoundEvent swingSound;
        protected SoundEvent hitSound;
        protected HitParticleType hitParticle;

        public EntityPatch<?> get(Entity entity) {
            return new PrideMobPatch(this.faction, this);
        }

        public Map<WeaponCategory, Map<Style, Set<Pair<LivingMotion, StaticAnimation>>>> getHumanoidWeaponMotions() {
            return this.AHWeaponMotions;
        }

        public PrideCustomHumanoidMobPatchProvider() {
            this.faction = Faction.NEUTRAL;
            this.regenStaminaStandbyTime = 30;
            this.hasStunReduction = true;
            this.maxStunShield = 0.0F;
            this.reganShieldStandbyTime = 30;
            this.reganShieldMultiply = 1.0F;
            this.staminaLoseMultiply = 0.0F;
            this.guardRadius = 3.0F;
            this.attackRadius = 1.5F;
            this.defaultAnimations = new ArrayList();
            this.stunAnimations = Maps.newHashMap();
            this.attributeValues = Maps.newHashMap();
            this.chasingSpeed = (double) 1.0F;
            this.scale = 1.0F;
            this.hasBossBar = false;
            this.stunEvent = new ArrayList();
            this.swingSound = (SoundEvent) EpicFightSounds.WHOOSH.get();
            this.hitSound = (SoundEvent) EpicFightSounds.BLUNT_HIT.get();
            this.hitParticle = (HitParticleType) EpicFightParticles.HIT_BLUNT.get();
        }

        public List<Pair<LivingMotion, StaticAnimation>> getDefaultAnimations() {
            return this.defaultAnimations;
        }

        public Map<StunType, StaticAnimation> getStunAnimations() {
            return this.stunAnimations;
        }

        public Map<Attribute, Double> getAttributeValues() {
            return this.attributeValues;
        }

        public double getChasingSpeed() {
            return this.chasingSpeed;
        }

        public float getScale() {
            return this.scale;
        }

        public int getRegenStaminaStandbyTime() {
            return this.regenStaminaStandbyTime;
        }

        public boolean hasStunReduction() {
            return this.hasStunReduction;
        }

        public float getMaxStunShield() {
            return this.maxStunShield;
        }

        public int getReganShieldStandbyTime() {
            return this.reganShieldStandbyTime;
        }

        public float getReganShieldMultiply() {
            return this.reganShieldMultiply;
        }

        public float getStaminaLoseMultiply() {
            return this.staminaLoseMultiply;
        }

        public float getGuardRadius() {
            return this.guardRadius;
        }

        public float getAttackRadius() {
            return this.attackRadius;
        }

        public List<LivingEntityPatchEvent.StunEvent> getStunEvent() {
            return this.stunEvent;
        }

        public boolean hasBossBar() {
            return this.hasBossBar;
        }

        public String getName() {
            return this.name;
        }

        public ResourceLocation getBossBar() {
            return this.bossBar;
        }

        public CombatBehaviors.Builder<MobPatch<?>> getCombatBehaviorsBuilder() {
            return this.combatBehaviorsBuilder;
        }

        public GuardMotion getGuardMotion() {
            return this.defaultGuardMotion;
        }

        public SoundEvent getSwingSound() {
            return this.swingSound;
        }

        public SoundEvent getHitSound() {
            return this.hitSound;
        }

        public HitParticleType getHitParticle() {
            return this.hitParticle;
        }

        public Map<WeaponCategory, Map<Style, CombatBehaviors.Builder<HumanoidMobPatch<?>>>> getHumanoidCombatBehaviors() {
            return this.AHCombatBehaviors;
        }

        public Map<WeaponCategory, Map<Style, GuardMotion>> getGuardMotions() {
            return this.guardMotions;
        }
    }
}
