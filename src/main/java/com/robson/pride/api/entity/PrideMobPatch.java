package com.robson.pride.api.entity;

import com.robson.pride.api.ai.combat.HumanoidCombatActions;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.event.entity.living.LivingEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPEntityPacket;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;

import java.util.concurrent.TimeUnit;

public class PrideMobPatch <PrideMob extends PathfinderMob> extends HumanoidMobPatch<PrideMob> {

    private boolean isHumanoid;

    private HumanoidCombatActions humanoidCombatActions;


    private HumanoidCombatActions hurtHumanoidActions;

    private float stamina;

    private boolean isBlocking;

    private boolean canParry;

    public PrideMobPatch() {
        super(Factions.NEUTRAL);
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
        if (this.getTarget() != null && AnimUtils.checkAttack(this.getTarget())) {
            this.startBlocking(1000, false);
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
    }

    public void startBlocking(int duration, boolean canParry){
        this.isBlocking = true;
        this.canParry = canParry;
        TimerUtil.schedule(()-> {
            this.isBlocking = false;
            this.canParry = false;
        }, duration, TimeUnit.MILLISECONDS);
    }
}
