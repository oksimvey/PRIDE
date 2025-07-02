package com.robson.pride.mixins;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.client.forgeevent.UpdatePlayerMotionEvent;
import yesman.epicfight.api.client.physics.cloth.ClothSimulatable;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(AbstractClientPlayerPatch.class)
@OnlyIn(Dist.CLIENT)
public abstract class AbstractClientPlayerPatchMixin<T extends AbstractClientPlayer> extends PlayerPatch<T> implements ClothSimulatable {

    @Shadow protected abstract boolean isMoving();

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void updateMotion(boolean considerInaction) {
        if (((AbstractClientPlayer)this.original).getHealth() <= 0.0F) {
            this.currentLivingMotion = LivingMotions.DEATH;
        } else if (!this.state.updateLivingMotion() && considerInaction) {
            this.currentLivingMotion = LivingMotions.INACTION;
        } else {
            ClientAnimator animator = this.getClientAnimator();
            if (!((AbstractClientPlayer)this.original).isFallFlying() && !((AbstractClientPlayer)this.original).isAutoSpinAttack()) {
                if (((AbstractClientPlayer)this.original).getVehicle() != null) {
                    if (((AbstractClientPlayer)this.original).getVehicle() instanceof PlayerRideableJumping) {
                        this.currentLivingMotion = LivingMotions.MOUNT;
                    }
                    else if (this.getOriginal().getVehicle() instanceof Animal){
                        this.currentLivingMotion = LivingMotions.DEATH;
                    }
                    else if (this.getOriginal().getDeltaMovement().length() > 0f){
                        this.currentLivingMotion = LivingMotions.WALK;
                    }
                    else this.currentLivingMotion = LivingMotions.DEATH;
                } else if (((AbstractClientPlayer)this.original).isVisuallySwimming()) {
                    this.currentLivingMotion = LivingMotions.SWIM;
                } else if (((AbstractClientPlayer)this.original).isSleeping()) {
                    this.currentLivingMotion = LivingMotions.SLEEP;
                } else if (!((AbstractClientPlayer)this.original).onGround() && ((AbstractClientPlayer)this.original).onClimbable()) {
                    this.currentLivingMotion = LivingMotions.CLIMB;
                } else if (!((AbstractClientPlayer)this.original).getAbilities().flying) {
                    if (((AbstractClientPlayer)this.original).isUnderWater() && ((AbstractClientPlayer)this.original).getY() - this.yo < -0.005) {
                        this.currentLivingMotion = LivingMotions.FLOAT;
                    } else if (!(((AbstractClientPlayer)this.original).getY() - this.yo < (double)-0.4F) && !this.isAirborneState()) {
                        if (this.isMoving()) {
                            if (((AbstractClientPlayer)this.original).isCrouching()) {
                                this.currentLivingMotion = LivingMotions.SNEAK;
                            } else if (((AbstractClientPlayer)this.original).isSprinting()) {
                                this.currentLivingMotion = LivingMotions.RUN;
                            } else {
                                this.currentLivingMotion = LivingMotions.WALK;
                            }

                            animator.baseLayer.animationPlayer.setReversed(this.dz < (double)0.0F);
                        } else {
                            animator.baseLayer.animationPlayer.setReversed(false);
                            if (((AbstractClientPlayer)this.original).isCrouching()) {
                                this.currentLivingMotion = LivingMotions.KNEEL;
                            } else {
                                this.currentLivingMotion = LivingMotions.IDLE;
                            }
                        }
                    } else {
                        this.currentLivingMotion = LivingMotions.FALL;
                    }
                } else if (this.isMoving()) {
                    this.currentLivingMotion = LivingMotions.CREATIVE_FLY;
                } else {
                    this.currentLivingMotion = LivingMotions.CREATIVE_IDLE;
                }
            } else {
                this.currentLivingMotion = LivingMotions.FLY;
            }
        }

        UpdatePlayerMotionEvent.BaseLayer baseLayerEvent = new UpdatePlayerMotionEvent.BaseLayer((AbstractClientPlayerPatch<?>) (Object)this, this.currentLivingMotion, !this.state.updateLivingMotion() && considerInaction);
        MinecraftForge.EVENT_BUS.post(baseLayerEvent);
        this.currentLivingMotion = baseLayerEvent.getMotion();
        if (!this.state.updateLivingMotion() && considerInaction) {
            this.currentCompositeMotion = LivingMotions.NONE;
        } else {
            CapabilityItem mainhandItemCap = this.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            CapabilityItem offhandItemCap = this.getHoldingItemCapability(InteractionHand.OFF_HAND);
            LivingMotion customLivingMotion = mainhandItemCap.getLivingMotion(this, InteractionHand.MAIN_HAND);
            if (customLivingMotion == null) {
                customLivingMotion = offhandItemCap.getLivingMotion(this, InteractionHand.OFF_HAND);
            }

            if (customLivingMotion != null) {
                this.currentCompositeMotion = customLivingMotion;
            } else if (((AbstractClientPlayer)this.original).isUsingItem()) {
                CapabilityItem itemUsingCap = ((AbstractClientPlayer)this.original).getUsedItemHand() == InteractionHand.MAIN_HAND ? mainhandItemCap : offhandItemCap;
                UseAnim useAnim = ((AbstractClientPlayer)this.original).getUseItem().getUseAnimation();
                UseAnim capUseAnim = itemUsingCap.getUseAnimation(this);
                if (useAnim != UseAnim.BLOCK && capUseAnim != UseAnim.BLOCK) {
                    if (useAnim == UseAnim.CROSSBOW) {
                        this.currentCompositeMotion = LivingMotions.RELOAD;
                    } else if (useAnim == UseAnim.DRINK) {
                        this.currentCompositeMotion = LivingMotions.DRINK;
                    } else if (useAnim == UseAnim.EAT) {
                        this.currentCompositeMotion = LivingMotions.EAT;
                    } else if (useAnim == UseAnim.SPYGLASS) {
                        this.currentCompositeMotion = LivingMotions.SPECTATE;
                    } else {
                        this.currentCompositeMotion = this.currentLivingMotion;
                    }
                } else if (itemUsingCap.getWeaponCategory() == CapabilityItem.WeaponCategories.SHIELD) {
                    this.currentCompositeMotion = LivingMotions.BLOCK_SHIELD;
                } else {
                    this.currentCompositeMotion = LivingMotions.BLOCK;
                }
            } else if (((StaticAnimation)this.getClientAnimator().getCompositeLayer(Layer.Priority.MIDDLE).animationPlayer.getRealAnimation().get()).isReboundAnimation()) {
                this.currentCompositeMotion = LivingMotions.SHOT;
            } else if (((AbstractClientPlayer)this.original).swinging && ((AbstractClientPlayer)this.original).getSleepingPos().isEmpty()) {
                this.currentCompositeMotion = LivingMotions.DIGGING;
            } else {
                this.currentCompositeMotion = this.currentLivingMotion;
            }

            UpdatePlayerMotionEvent.CompositeLayer compositeLayerEvent = new UpdatePlayerMotionEvent.CompositeLayer((AbstractClientPlayerPatch<?>) (Object)this, this.currentCompositeMotion);
            MinecraftForge.EVENT_BUS.post(compositeLayerEvent);
            this.currentCompositeMotion = compositeLayerEvent.getMotion();
        }

    }
}
