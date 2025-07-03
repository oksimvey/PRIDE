package com.robson.pride.api.entity;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.MobData;
import com.robson.pride.api.utils.LodTick;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.math.PrideVec3f;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.event.entity.living.LivingEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPEntityPacket;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;

import java.util.concurrent.TimeUnit;

public class PrideMobPatch <PrideMob extends PathfinderMob> extends MobPatch<PrideMob> {

    private byte staminaregaindelay = 0;

    private float stamina = 0;

    private float maxStamina = 100;

    public PrideMobPatch() {
        super(Factions.NEUTRAL);
    }

    public void setMaxStamina(float maxStamina) {
        this.maxStamina = maxStamina;
    }

    public float getMaxStamina() {
        return this.maxStamina;
    }

    public void consumeStamina(float amount) {
        this.stamina -= amount;
        this.staminaregaindelay = 60;
    }

    public float getStamina() {
        return this.stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = stamina;
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
        if (this.stamina < 0){
            this.stamina = 0;
        }
        if (this.stamina >= maxStamina){
            this.stamina = maxStamina;
        }
        else if (this.staminaregaindelay == 0){
            this.stamina += 0.01f;
        }
        else this.staminaregaindelay--;
        for (Entity ent : this.getOriginal().level().getEntities(getOriginal(), MathUtils.createAABBAroundEnt(getOriginal(), 10))){
            if (ent instanceof Projectile arrow && arrow.getDeltaMovement().length() > 0.75) {
                PrideVec3f delta = PrideVec3f.fromVec3(arrow.getDeltaMovement());
                if (delta.willHit(PrideVec3f.fromVec3(arrow.position()), PrideVec3f.fromVec3(this.getOriginal().position()),
                        this.getOriginal().getBbWidth() + arrow.getBbWidth(), this.getOriginal().getBbHeight() + arrow.getBbHeight())) {
                    this.rotateTo(arrow, 999999, false);
                    SkillDataManager.addSkill(this.getOriginal(), SkillDataManager.GUARD);
                    TimerUtil.schedule(() -> {
                        SkillDataManager.removeSkill(this.getOriginal(), SkillDataManager.GUARD);
                    }, 1000, TimeUnit.MILLISECONDS);
                    return;
                }
            }
        }
        if (LodTick.canTick(this.getOriginal(), 2) &&
                !SkillDataManager.isSkillActive(this.getOriginal(), SkillDataManager.GUARD)){
            if (this.getTarget() == null || !this.getTarget().isAlive() || (this.getTarget() instanceof Player player && player.isCreative())){
                this.setAttakTargetSync(null);
            }
            if (EpicFightCapabilities.getEntityPatch(this.getTarget(), LivingEntityPatch.class) instanceof PlayerPatch<?> playerPatch && playerPatch.getEntityState().getLevel() > 0 ){
                SkillDataManager.addSkill(this.getOriginal(), SkillDataManager.GUARD);
                TimerUtil.schedule(()-> {
                    SkillDataManager.removeSkill(this.getOriginal(), SkillDataManager.GUARD);
                }, 3000, TimeUnit.MILLISECONDS);
                return;
            }
            if (this.getTarget() != null &&!this.getEntityState().attacking() && this.getEntityState().canBasicAttack()) {
                MobData data = ServerDataManager.getMobData((com.robson.pride.api.entity.PrideMob) this.getOriginal());
                if (data != null){
                    data.getCombatActions().trySelect(this);
                }
            }
        }
    }

    public boolean isBlocking() {
        return SkillDataManager.isSkillActive(this.getOriginal(), SkillDataManager.GUARD);
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

    public void updateMotion(boolean bol){
        if (this.original.getHealth() <= 0.0F) {
            this.currentLivingMotion = LivingMotions.DEATH;
        } else if (this.state.inaction() && bol) {
            this.currentLivingMotion = LivingMotions.IDLE;
        } else if (this.original.getVehicle() != null) {
            this.currentLivingMotion = LivingMotions.MOUNT;
        } else if (this.original.getDeltaMovement().y < -0.550000011920929) {
            this.currentLivingMotion = LivingMotions.FALL;
        } else if (this.original.walkAnimation.speed() > 0.01F) {
            if (this.getTarget() != null) {
                this.currentLivingMotion = LivingMotions.CHASE;
            } else {
                this.currentLivingMotion = LivingMotions.WALK;
            }
        } else {
            this.currentLivingMotion = LivingMotions.IDLE;
        }

        this.currentCompositeMotion = this.currentLivingMotion;

        if (this.original.isUsingItem()) {
            CapabilityItem activeItem = this.getHoldingItemCapability(this.original.getUsedItemHand());
            UseAnim useAnim = this.original.getItemInHand(this.original.getUsedItemHand()).getUseAnimation();
            UseAnim secondUseAnim = activeItem.getUseAnimation(this);

            if (useAnim == UseAnim.BLOCK || secondUseAnim == UseAnim.BLOCK)
                if (activeItem.getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD)
                    currentCompositeMotion = LivingMotions.BLOCK_SHIELD;
                else
                    currentCompositeMotion = LivingMotions.BLOCK;
            else if (useAnim == UseAnim.BOW || useAnim == UseAnim.SPEAR)
                currentCompositeMotion = LivingMotions.AIM;
            else if (useAnim == UseAnim.CROSSBOW)
                currentCompositeMotion = LivingMotions.RELOAD;
            else
                currentCompositeMotion = currentLivingMotion;
        } else {
            if(this.isBlocking()) currentCompositeMotion = LivingMotions.BLOCK;
            else if (CrossbowItem.isCharged(this.original.getMainHandItem()))
                currentCompositeMotion = LivingMotions.AIM;
            else if (this.getClientAnimator().getCompositeLayer(Layer.Priority.MIDDLE).animationPlayer.getAnimation().get().isReboundAnimation())
                currentCompositeMotion = LivingMotions.NONE;
            else if (this.original.swinging && this.original.getSleepingPos().isEmpty())
                currentCompositeMotion = LivingMotions.DIGGING;
            else
                currentCompositeMotion = currentLivingMotion;

            if (this.getClientAnimator().isAiming() && currentCompositeMotion != LivingMotions.AIM) {

            }
        }
    }
}
