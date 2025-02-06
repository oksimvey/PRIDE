package com.robson.pride.api.entity;

import com.robson.pride.api.ai.goals.JsonGoalsReader;
import com.robson.pride.api.ai.dialogues.JsonInteractionsReader;
import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.*;
import com.robson.pride.registries.AnimationsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static com.robson.pride.api.ai.dialogues.JsonInteractionsReader.isSpeaking;

public abstract class PrideMobBase extends PathfinderMob implements Enemy {

    private static final EntityDataAccessor<Byte> VARIANT =
            SynchedEntityData.defineId(PrideMobBase.class, EntityDataSerializers.BYTE);

    public List<String> targets = new ArrayList<>();

    public List<String> skills = new ArrayList<>();

    public List<String> textures = new ArrayList<>();

    private ListTag pathpositions;

    private Vec3 targetpos;

    private float speed = 1;

    private float moveRadius = 1;

    private byte pathcounter = 0;

    protected PrideMobBase(EntityType<? extends PrideMobBase> p_33002_, Level p_33003_) {
        super(p_33002_, p_33003_);
        this.xpReward = 5;
        this.setPersistenceRequired();
        deserializeTargetGoals();
        deserializePassiveSkillsJson();
        deserializeTextures();
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

    public void deserializeTargetGoals() {
        CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(this.getType());
        if (tagmap != null) {
            ListTag targets = tagmap.getList("targets", 8);
            if (targets != null) {
                for (int i = 0; i < targets.size(); ++i) {
                    this.targets.add(targets.getString(i));
                }
            }
        }
    }


    public void deserializeTextures(){
        CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(this.getType());
        if (tagmap != null) {
            ListTag targets = tagmap.getList("textures", 8);
            for (int i = 0; i < targets.size(); ++i) {
                this.textures.add(targets.getString(i));
            }
        }
    }

    public void deserializePassiveSkillsJson() {
        CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(this.getType());
        if (tagmap != null) {
            ListTag targets = tagmap.getList("skills", 8);
            for (int i = 0; i < targets.size(); ++i) {
                this.skills.add(targets.getString(i));
            }
        }
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
        equipAllSlotsToDefault();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand){
        super.mobInteract(player, hand);
        if (TargetUtil.getTarget(this) == null && isSpeaking.get(this) == null){
            JsonInteractionsReader.onInteraction(this, player);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }


    public void equipAllSlotsToDefault() {
        CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(this.getType());
        if (tagmap != null) {
            ListTag equipments = tagmap.getList("equipment", 10);
            if (equipments != null) {
                for (int i = 0; i < equipments.size(); ++i) {
                    CompoundTag equipment = equipments.getCompound(i);
                    if (equipment.contains("variations")) {
                        ListTag variations = equipment.getList("variations", 3);
                        for (int j = 0; j < variations.size(); ++j) {
                            if (getTypeVariant() == variations.getInt(j)) {
                                deserializeEquipment(equipment);
                            }
                        }
                    }
                }
            }
        }
    }

    public void deserializeEquipment(CompoundTag tag){
        if (tag != null){
            if (tag.contains("slot") && tag.contains("item")){
                ItemStack item = EquipUtils.locateItem(tag.getString("item"));
                if (tag.contains("element")){
                    item.getOrCreateTag().putString("passive_element", tag.getString("element"));
                }
                switch (tag.getString("slot")){
                    case "mainhand" -> this.setItemSlot(EquipmentSlot.MAINHAND, item);
                    case "offhand" -> this.setItemSlot(EquipmentSlot.OFFHAND, item);
                    case "head" -> this.setItemSlot(EquipmentSlot.HEAD, item);
                    case "chest" -> this.setItemSlot(EquipmentSlot.CHEST, item);
                    case "leg" -> this.setItemSlot(EquipmentSlot.LEGS, item);
                    case "feet" -> this.setItemSlot(EquipmentSlot.FEET, item);
                }
            }
        }
    }

    public void setTargetpos(Vec3 newpos){
        this.targetpos = newpos;
    }

    public void setPathpositions(ListTag pathpositions){
        if (this.pathpositions != pathpositions) {
            this.targetpos = null;
            this.pathcounter = 0;
            this.pathpositions = pathpositions;
        }
    }

    public void setMoveRadius(float radius){
        this.moveRadius = radius;
    }

    public void setTravellingSpeed(float speed){
        this.speed = speed;
    }

    public boolean canTickLod(Minecraft client) {
        if (client.player != null) {
            short distance = (short) (1 + Math.pow(1.025, client.player.distanceTo(this)) * 2);
            return client.player.tickCount % distance == 0;
        }
        return false;
    }

    @Override
    public void travel(Vec3 travel) {
            if (isSpeaking.get(this) != null && TargetUtil.getTarget(this) == null) {
                travel = new Vec3(0, 0, 0);
            }
            else if (canTickLod(Minecraft.getInstance())) {
                JsonGoalsReader.onEntTick(this);
                if (TargetUtil.getTarget(this) == null) {
                    deserializePassiveSkills();
                    if (this.targetpos != null) {
                        if (this.distanceToSqr(targetpos) >= this.moveRadius) {
                            PathNavigation navigator = this.getNavigation();
                            Path path = navigator.createPath(this.targetpos.x, this.targetpos.y, this.targetpos.z, 0);
                            navigator.moveTo(path, speed);
                        }
                        else {
                            this.targetpos = null;
                            this.moveRadius = 1;
                            this.speed = 1;
                            if (this.pathpositions != null) {
                                this.pathcounter++;
                            }
                        }
                    }
                    else if (this.pathpositions != null) {
                        deserializePaths();
                    }
                }
        }
        super.travel(travel);
    }

    private void deserializePaths() {
        if (this.pathcounter < this.pathpositions.size()) {
            CompoundTag path = this.pathpositions.getCompound(this.pathcounter);
            if (path.contains("x") && path.contains("y") && path.contains("z")) {
                this.targetpos = new Vec3(path.getInt("x"), path.getInt("y"), path.getInt("z"));
                this.speed = path.contains("speed") ? path.getFloat("speed") : 1;
                this.moveRadius = path.contains("radius") ? path.getInt("radius") : 1;
            }
        }
        else {
            this.pathpositions = null;
            this.pathcounter = 0;
        }
    }

    public void deserializePassiveSkills() {
        if (this.skills.contains("open_door") && shouldOpenDoor() != null) {
            shouldOpenDoor().setOpen(this, this.level(), this.level().getBlockState(getBlockPosAhead()), getBlockPosAhead(), true);
        }
        else if (this.skills.contains("path_sneak") && shouldPathSneak()) {
            Entity target = TargetUtil.getTarget(this);
            AnimUtils.cancelMotion(this);
            AnimUtils.playAnim(this, AnimationsRegister.MOB_SNEAK, 0.1f);
            AnimUtils.resizeBoundingBox(this, 0.5f, 1.8f);
            TargetUtil.setTarget(this, null);
            TimerUtil.schedule(()->{
                this.refreshDimensions();
                if (target != null){
                    TargetUtil.setTarget(this, target);
                }
            }, 1, TimeUnit.SECONDS);
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

    protected SoundEvent getDeathSound() {
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

