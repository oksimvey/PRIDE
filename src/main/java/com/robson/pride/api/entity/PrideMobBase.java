package com.robson.pride.api.entity;

import com.robson.pride.api.ai.dialogues.JsonInteractionsReader;
import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.*;
import de.markusbordihn.easynpc.data.attribute.*;
import de.markusbordihn.easynpc.data.objective.ObjectiveDataEntry;
import de.markusbordihn.easynpc.data.objective.ObjectiveDataSet;
import de.markusbordihn.easynpc.data.objective.ObjectiveType;
import de.markusbordihn.easynpc.data.synched.SynchedDataIndex;
import de.markusbordihn.easynpc.entity.EasyNPCBaseEntity;
import de.markusbordihn.easynpc.entity.easynpc.data.AttackData;
import de.markusbordihn.easynpc.entity.easynpc.data.ObjectiveData;
import de.markusbordihn.easynpc.network.NetworkMessageHandlerManager;
import de.markusbordihn.easynpc.server.player.FakePlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationProvider;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.isSpeaking;

public abstract class PrideMobBase extends EasyNPCBaseEntity implements Enemy {

    private static final EntityDataAccessor<Byte> VARIANT =
            SynchedEntityData.defineId(PrideMobBase.class, EntityDataSerializers.BYTE);

    private ConcurrentHashMap<EquipmentSlot, ItemStack> equipmentMap = new ConcurrentHashMap<>();

    public List<String> targets = new ArrayList<>();

    public List<String> textures = new ArrayList<>();

    public List<String> allies = new ArrayList<>();

    private CompoundTag skillmotions = new CompoundTag();

    public LivingEntity target;

    private Music mobMusic;

    private byte level;

    private byte music_priority = 0;

    protected PrideMobBase(EntityType<? extends PrideMobBase> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_, Variant.STEVE);
        this.xpReward = 5;
        this.setPersistenceRequired();
        deserializeConstructorJsons();
    }

    private void deserializeConstructorJsons() {
        CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(this.getType());
        if (tagmap != null) {
            deserializeTargetGoals(tagmap);
            deserializeTextures(tagmap);
            deserializeMusics(tagmap);
            deserializeLevel(tagmap);
            deserializeSkillMotions(tagmap);
            deserializeEquipmentMap(tagmap);
            deserializeAllies(tagmap);
        }
    }

    public void deserializeAlliesTargeting(Entity ent, Entity dmgent){
        if (ent != null && dmgent != null && (ent.getType().equals(this.getType()) || this.allies.contains(EntityType.getKey(ent.getType()).toString()))){
            TargetUtil.setTarget(this, dmgent);
        }
    }

    public void deserializeAllies(CompoundTag tagmap) {
        ListTag allies = tagmap.getList("allies", 8);
        for (int i = 0; i < allies.size(); ++i) {
            this.allies.add(allies.getString(i));
        }
    }

    public void deserializeTargetGoals(CompoundTag tagmap) {
        ListTag targets = tagmap.getList("targets", 8);
        for (int i = 0; i < targets.size(); ++i) {
            this.targets.add(targets.getString(i));
        }
    }

    public void deserializeTextures(CompoundTag tagmap){
        ListTag targets = tagmap.getList("textures", 8);
        for (int i = 0; i < targets.size(); ++i) {
            this.textures.add(targets.getString(i));
        }
    }

    public void deserializeMusics(CompoundTag tags){
        if (tags.contains("custom_music")) {
            Holder<SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(tags.getString("custom_music"))));
            this.mobMusic = new Music(holder, 1, 1, true);
        }
        if (tags.contains("music_priority")){
            this.music_priority = tags.getByte("music_priority");
        }
    }

    private void deserializeLevel(CompoundTag tagmap) {
        this.level = tagmap.contains("level") ? tagmap.getByte("level") :  1;
    }

    private void deserializeSkillMotions(CompoundTag tagmap){
        if (tagmap.contains("skills")){
            this.skillmotions = tagmap.getCompound("skills");
        }
    }

    public boolean hasSkill(String skill){
        return this.skillmotions.contains(skill);
    }

    public StaticAnimation getSkillMotion(String skill){
        if (skill.equals("divine_reflexes")){
            ListTag motions = this.skillmotions.getList(skill, 8);
            return AnimationManager.getInstance().byKeyOrThrow(motions.getString(new Random().nextInt(motions.size()) + 1));
        }
        return AnimationManager.getInstance().byKeyOrThrow(this.skillmotions.getString(skill));
    }

    public boolean isEquiped(){
        if (!this.equipmentMap.isEmpty()) {
            for (EquipmentSlot slot : this.equipmentMap.keySet()) {
                if (this.equipmentMap.get(slot) != this.getItemBySlot(slot)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(VARIANT, tag.getByte("variant"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("variant", this.getTypeVariant());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, (byte) 0);
    }

    public byte getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public byte getPrideLevel(){
        return this.level;
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(
                    this,
                    LivingEntity.class,
                    true,
                    entity -> this.targets.contains(EntityType.getKey(entity.getType()).toString())));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(accessor, difficulty, reason, spawnDataIn, dataTag);
        this.entityData.set(VARIANT, (byte) new Random().nextInt(getMaxVariations()));
        equipAllSlotsToDefault();
        setupDefaultNPC();
        return data;
    }

    public byte getMaxVariations(){
        if (PrideMobPatchReloader.MOB_TAGS.get(this.getType()) != null){
            byte variations = PrideMobPatchReloader.MOB_TAGS.get(this.getType()).getByte("variations");
            if (variations > 0){
                return variations;
            }
        }
        return 1;
    }

    @Override
    public void setTarget(@Nullable LivingEntity p_21544_) {
        super.setTarget(p_21544_);
        if (!this.isEquiped()) {
            equipAllSlotsToDefault();
        }
        this.target = p_21544_;
        if (p_21544_ != null && EpicFightCapabilities.getEntityPatch(p_21544_, LivingEntityPatch.class) == null){
           deserializeFight(p_21544_, (byte) 0);
        }
    }

    public void deserializeFight(LivingEntity target, byte animation){
        if (target != null){
            if (this.target == target && target.isAlive()){
                if (this.distanceTo(target) < this.getBbHeight() * 3){
                    List<AnimationProvider<?>> motions = ItemStackUtils.getWeaponMotions(this.getMainHandItem());
                    if (motions != null){
                        if (animation > motions.size()){
                            animation = 0;
                        }
                        StaticAnimation animationtoplay = motions.get(animation).get();
                        AnimUtils.playAnim(this, animationtoplay, 0);
                        animation += 1;
                        byte finalAnimation = animation;
                        int delay = AnimUtils.getAnimationDurationInMilliseconds(this, animationtoplay);
                        TimerUtil.schedule(()-> deserializeFight(target, finalAnimation), delay, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }

    public Music getMobMusic(){
        return this.mobMusic;
    }

    public byte getMusicPriority(){
        return (byte) (this.music_priority + 1);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand){
        if (!this.level().isClientSide) {
            if (this.target != null || isSpeaking.get(this) != null) {
                return InteractionResult.FAIL;
            }
            super.mobInteract(player, hand);
            JsonInteractionsReader.onInteraction(this, player);
        }
        return InteractionResult.FAIL;
    }

    public void deserializeEquipmentMap(CompoundTag tagmap) {
          ListTag equipments = tagmap.getList("equipment", 10);
            if (equipments != null) {
                for (int i = 0; i < equipments.size(); ++i) {
                    CompoundTag equipment = equipments.getCompound(i);
                    if (equipment.contains("variations")) {
                        ListTag variations = equipment.getList("variations", 3);
                        for (int j = 0; j < variations.size(); ++j) {
                            if (getTypeVariant() == variations.getInt(j)) {
                                if (equipment.contains("slot") && equipment.contains("item")) {
                                    ItemStack item = EquipUtils.locateItem(equipment.getString("item"));
                                    if (equipment.contains("element")) {
                                        item.getOrCreateTag().putString("passive_element", equipment.getString("element"));
                                    }
                                    switch (equipment.getString("slot")) {
                                        case "mainhand" -> this.equipmentMap.put(EquipmentSlot.MAINHAND, item);
                                        case "offhand" -> this.equipmentMap.put(EquipmentSlot.OFFHAND, item);
                                        case "head" -> this.equipmentMap.put(EquipmentSlot.HEAD, item);
                                        case "chest" -> this.equipmentMap.put(EquipmentSlot.CHEST, item);
                                        case "leg" -> this.equipmentMap.put(EquipmentSlot.LEGS, item);
                                        case "feet" -> this.equipmentMap.put(EquipmentSlot.FEET, item);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        equipAllSlotsToDefault();
    }

    public void setupDefaultNPC(){
        EntityAttributes attributes = this.getEasyNPCAttributeData().getEntityAttributes();
        attributes.setCombatAttributes(new CombatAttributes(true, 0));
        attributes.setInteractionAttributes( new InteractionAttributes(true, false, true));
        attributes.setMovementAttributes(new MovementAttributes(true, true, true, false));
        attributes.setEnvironmentalAttributes(new EnvironmentalAttributes(true, false, false));
        NetworkMessageHandlerManager.getServerHandler().combatAttributeChange(this.getUUID(), CombatAttributeType.IS_ATTACKABLE, true);
        ObjectiveData objectiveData = this.getEasyNPCObjectiveData();
        objectiveData.removeObjective(ObjectiveType.LOOK_AT_RESET);
        objectiveData.removeObjective(ObjectiveType.LOOK_AT_PLAYER);
        objectiveData.removeObjective(ObjectiveType.LOOK_AT_MOB);
        objectiveData.addObjective(new ObjectiveDataEntry(ObjectiveType.MOVE_BACK_TO_HOME, 5));
        objectiveData.addObjective(new ObjectiveDataEntry(ObjectiveType.RANDOM_STROLL_AROUND_HOME, 5));
    }

    public void equipAllSlotsToDefault(){
       if (!this.equipmentMap.isEmpty()){
           for (EquipmentSlot slot : this.equipmentMap.keySet()){
               if (this.getItemBySlot(slot) != this.equipmentMap.get(slot)) {
                   this.setItemSlot(slot, this.equipmentMap.get(slot));
               }
           }
       }
    }

    @Override
    public void die(DamageSource damageSource){
        this.refreshDimensions();
        AnimUtils.cancelMotion(this);
        super.die(damageSource);
    }

    public boolean tickLod(Minecraft client, float multiplier) {
        if (client.player != null) {
            short distance = (short) (1 + Math.pow(1.025, client.player.distanceTo(this)) * multiplier);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

    @Override
    public void travel(Vec3 travel) {
        if (this.target == null && isSpeaking.get(this) != null) {
            travel = new Vec3(0, 0, 0);
        }
        super.travel(travel);
    }

    public void deserializePassiveSkills() {
        if (this.hasSkill("open_door") && shouldOpenDoor() != null) {
            shouldOpenDoor().setOpen(this, this.level(), this.level().getBlockState(getBlockPosAhead()), getBlockPosAhead(), true);
        }
        else if (this.hasSkill("path_sneak") && shouldPathSneak()) {
            AnimUtils.cancelMotion(this);
            AnimUtils.playAnim(this, getSkillMotion("path_sneak"), 0.1f);
            AnimUtils.resizeBoundingBox(this, 0.5f, 1.8f);
            TimerUtil.schedule(this::refreshDimensions, 1, TimeUnit.SECONDS);
        }
    }

    public DoorBlock shouldOpenDoor(){
        if (this.level().getBlockState(getBlockPosAhead()).getBlock() instanceof DoorBlock door) {
            if(!door.isOpen(this.level().getBlockState(getBlockPosAhead()))){
                return door;
            }
        }
        return null;
    }

    public boolean shouldPathSneak() {
        return !(this.level().getBlockState(getBlockPosAhead().offset(0, (int) this.getBbHeight(), 0)).getBlock() instanceof AirBlock) && this.level().getBlockState(getBlockPosAhead().offset(0, (int) this.getBbHeight(), 0).below()).getBlock() instanceof AirBlock;
    }

    public boolean shouldPathRoll() {
        BlockPos posAhead = getBlockPosAhead();
        BlockPos checkPos = posAhead.above((int) this.getEyeHeight());
        return !(level().getBlockState(checkPos).getBlock() instanceof AirBlock) && level().getBlockState(checkPos.below()).getBlock() instanceof AirBlock;
    }

    public BlockPos getBlockPosAhead(){
        Vec3 lookVec = this.getLookAngle().scale(1.5).add(this.position());
        return BlockPos.containing(lookVec);
    }

    protected boolean shouldDespawnInPeaceful() {
        return true;
    }

    protected SoundEvent getSwimSound() {
        return SoundEvents.HOSTILE_SWIM;
    }

    protected SoundEvent getSwimSplashSound() {
        return SoundEvents.HOSTILE_SPLASH;
    }

    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return SoundEvents.HOSTILE_HURT;
    }

    public SoundEvent getDeathSound() {
        return SoundEvents.HOSTILE_DEATH;
    }

    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.HOSTILE_SMALL_FALL, SoundEvents.HOSTILE_BIG_FALL);
    }

    public float getWalkTargetValue(BlockPos p_33013_, LevelReader p_33014_) {
        return -p_33014_.getPathfindingCostFromLightLevels(p_33013_);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor p_219010_, BlockPos p_219011_, RandomSource p_219012_) {
        if (p_219010_.getBrightness(LightLayer.SKY, p_219011_) > p_219012_.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensiontype = p_219010_.dimensionType();
            int i = dimensiontype.monsterSpawnBlockLightLimit();
            if (i < 15 && p_219010_.getBrightness(LightLayer.BLOCK, p_219011_) > i) {
                return false;
            } else {
                int j = p_219010_.getLevel().isThundering() ? p_219010_.getMaxLocalRawBrightness(p_219011_, 10) : p_219010_.getMaxLocalRawBrightness(p_219011_);
                return j <= dimensiontype.monsterSpawnLightTest().sample(p_219012_);
            }
        }
    }

    public static boolean checkMonsterSpawnRules(EntityType<? extends PrideMobBase> p_219014_, ServerLevelAccessor p_219015_, MobSpawnType p_219016_, BlockPos p_219017_, RandomSource p_219018_) {
        return p_219015_.getDifficulty() != Difficulty.PEACEFUL && isDarkEnoughToSpawn(p_219015_, p_219017_, p_219018_) && checkMobSpawnRules(p_219014_, p_219015_, p_219016_, p_219017_, p_219018_);
    }

    public static boolean checkAnyLightMonsterSpawnRules(EntityType<? extends PrideMobBase> p_219020_, LevelAccessor p_219021_, MobSpawnType p_219022_, BlockPos p_219023_, RandomSource p_219024_) {
        return p_219021_.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(p_219020_, p_219021_, p_219022_, p_219023_, p_219024_);
    }

    public static AttributeSupplier.Builder createMonsterAttributes() {
        return Mob.createMobAttributes().add(Attributes.ATTACK_DAMAGE);
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    public boolean isPreventingPlayerRest(Player p_33036_) {
        return true;
    }

    public ItemStack getProjectile(ItemStack p_33038_) {
        if (p_33038_.getItem() instanceof ProjectileWeaponItem) {
            Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_33038_.getItem()).getSupportedHeldProjectiles();
            ItemStack itemstack = ProjectileWeaponItem.getHeldProjectile(this, predicate);
            return ForgeHooks.getProjectile(this, p_33038_, itemstack.isEmpty() ? new ItemStack(Items.ARROW) : itemstack);
        } else {
            return ForgeHooks.getProjectile(this, p_33038_, ItemStack.EMPTY);
        }
    }
}