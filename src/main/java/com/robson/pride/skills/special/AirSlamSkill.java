package com.robson.pride.skills.special;

import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import reascer.wom.particle.WOMParticles;
import yesman.epicfight.api.utils.LevelUtil;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AirSlamSkill extends WeaponSkillBase {

    public AirSlamSkill() {
        super("Rare", 10, 3);
    }

    @Override
    public void onehandExecute(LivingEntity ent){
        if (ent != null){
            ent.getPersistentData().putBoolean("Airslam", true);
            TimerUtil.schedule(()-> ent.getPersistentData().putBoolean("Airslam", false), 1000, TimeUnit.MILLISECONDS);
            AnimUtils.playAnim(ent, Animations.VINDICATOR_SWING_AXE3, 0);
        }
    }

    public static void onAirSlamDMG(Entity dmgent, Entity target){
        if (dmgent != null && target != null){
            AnimUtils.playAnim(target, Animations.BIPED_KNOCKDOWN, 0);
            TimerUtil.schedule(()->breakGround(dmgent), 300, TimeUnit.MILLISECONDS);
        }
    }

    public static void breakGround(Entity ent){
        LivingEntityPatch entitypatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (entitypatch != null) {
            Vec3 position = ((LivingEntity) entitypatch.getOriginal()).position();
            OpenMatrix4f modelTransform = entitypatch.getArmature().getBindedTransformFor(entitypatch.getAnimator().getPose(0.0F), Armatures.BIPED.torso).mulFront(OpenMatrix4f.createTranslation((float) position.x, (float) position.y, (float) position.z).mulBack(OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS).mulBack(entitypatch.getModelMatrix(1.0F))));
            Vec3 weaponEdge = OpenMatrix4f.transform(modelTransform, (new Vec3f(0.0F, -0.0F, -1.4F)).toDoubleVector());
            Level level = ((LivingEntity) entitypatch.getOriginal()).level();
            Vec3 floorPos = ent.position();
            BlockState blockState = ((LivingEntity) entitypatch.getOriginal()).level().getBlockState(new BlockPos.MutableBlockPos(floorPos.x, floorPos.y, floorPos.z));
            if (entitypatch instanceof PlayerPatch) {
                ((LivingEntity) entitypatch.getOriginal()).level().playSound((Player) entitypatch.getOriginal(), entitypatch.getOriginal(), blockState.is(Blocks.WATER) ? SoundEvents.GENERIC_SPLASH : (SoundEvent) EpicFightSounds.GROUND_SLAM.get(), SoundSource.PLAYERS, 1.5F, 1.1F - ((new Random()).nextFloat() - 0.5F) * 0.2F);
            }
            weaponEdge = new Vec3(weaponEdge.x, floorPos.y, weaponEdge.z);
            ((LivingEntity) entitypatch.getOriginal()).level().addParticle((ParticleOptions) WOMParticles.WOM_GROUND_SLAM.get(), floorPos.x, (double) ((int) floorPos.y + 1), floorPos.z, 1.0, 50.0, 1.0);
            LevelUtil.circleSlamFracture((LivingEntity) entitypatch.getOriginal(), level, weaponEdge, 3.5, true, true);
        }
    }
}
