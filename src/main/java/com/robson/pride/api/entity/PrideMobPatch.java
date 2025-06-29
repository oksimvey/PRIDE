package com.robson.pride.api.entity;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.LodTick;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPEntityPacket;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PrideMobPatch <PrideMob extends PathfinderMob> extends MobPatch<PrideMob> {

    public PrideMobPatch() {
        super(Factions.NEUTRAL);
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getHitAnimation(StunType stunType) {
        if (((PathfinderMob)this.original).getVehicle() != null) {
            return Animations.BIPED_HIT_ON_MOUNT;
        } else {
            switch (stunType) {
                case LONG -> {
                    return Animations.BIPED_HIT_LONG;
                }
                case SHORT, HOLD -> {
                    return Animations.BIPED_HIT_SHORT;
                }
                case KNOCKDOWN -> {
                    return Animations.BIPED_KNOCKDOWN;
                }
                case NEUTRALIZE -> {
                    return Animations.BIPED_COMMON_NEUTRALIZED;
                }
                case FALL -> {
                    return Animations.BIPED_LANDING;
                }
                default -> {
                    return null;
                }
            }
        }
    }

    public void onStartTracking(ServerPlayer trackingPlayer) {
        if (!this.getHoldingItemCapability(InteractionHand.MAIN_HAND).isEmpty()) {
            SPEntityPacket packet = new SPEntityPacket(((PathfinderMob)this.original).getId());
            EpicFightNetworkManager.sendToPlayer(packet, trackingPlayer);
        }
        super.onStartTracking(trackingPlayer);
    }

        public void processEntityPacket(FriendlyByteBuf buf) {
        ClientAnimator animator = this.getClientAnimator();
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_WALK);
        animator.setCurrentMotionsAsDefault();
    }

    @Override
    public OpenMatrix4f getModelMatrix(float partialTicks) {
        return super.getModelMatrix(partialTicks).scale(1, 1, 1);
    }

    @Override
    public void serverTick(LivingEvent.LivingTickEvent event) {
        super.serverTick(event);
        if (LodTick.canTick(this.getOriginal(), 2)) {
            if (this.getTarget() == null || !this.getTarget().isAlive()){
                this.setAttakTargetSync(null);
                return;
            }
            if (AnimUtils.checkAttack(this.getTarget())){
                SkillDataManager.addSkill(this.getOriginal(), SkillDataManager.GUARD);
                TimerUtil.schedule(()-> {
                    SkillDataManager.removeSkill(this.getOriginal(), SkillDataManager.GUARD);
                }, 2000, TimeUnit.MILLISECONDS);
                return;
            }
           if (!this.getEntityState().attacking() && this.getEntityState().canBasicAttack() && !SkillDataManager.isSkillActive(this.getOriginal(), SkillDataManager.GUARD)){
               AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = switch (new Random().nextInt(2)) {
                   case 0 -> Animations.GREATSWORD_AUTO1;
                   case 1 -> Animations.GREATSWORD_AUTO2;
                   default -> Animations.GREATSWORD_DASH;
               };
               AnimUtils.playAnim(this.getOriginal(), animation, 0);
           }
        }
    }

        public void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.FALL, Animations.BIPED_FALL);
        animator.addLivingAnimation(LivingMotions.MOUNT, Animations.BIPED_MOUNT);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
        animator.addLivingAnimation(LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
    }

    public void updateMotion(boolean considerInaction) {
        super.commonMobUpdateMotion(considerInaction);
        if (this.original.isUsingItem()){
            this.currentLivingMotion = LivingMotions.BLOCK;
        }
    }
}
