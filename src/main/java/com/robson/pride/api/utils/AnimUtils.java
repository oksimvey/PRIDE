package com.robson.pride.api.utils;

import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.skill.DurationSkillData;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.events.OnAttackStartEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.mechanics.PerilousAttack.perilousParticle;
import static com.robson.pride.api.mechanics.PerilousAttack.playPerilous;

public interface AnimUtils {

    static void changeDelta(Entity ent, Vec3 delta) {
        if (ent != null && delta != null) {
            ent.hurtMarked = true;
            ent.hasImpulse = true;
            ent.setDeltaMovement(delta);
        }
    }

    static void rotateToEntity(Entity ent, Entity target) {
        if (ent != null && target != null) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.rotateTo(target, 1000, false);
                livingEntityPatch.updateEntityState();
            }
        }
    }


    static void rotateTo(Entity ent, float degrees) {
        if (ent != null) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.rotateTo(degrees, 1000, false);
                livingEntityPatch.updateEntityState();
            }
        }
    }

    static void rotatePlayer(Player player, float degrees) {
        if (player != null) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                playerPatch.setModelYRot(Minecraft.getInstance().gameRenderer.getMainCamera().getYRot() + degrees, true);
            }
        }
    }

    static void playAnim(Entity ent, AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, float convert) {
        TimerUtil.schedule(() -> {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null && animation != null) {
                livingEntityPatch.playAnimationSynchronized(animation, convert);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    static boolean allowShoot(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {

            }
            return true;
        }
        return false;
    }


    static void addPerilousToAnim(Entity ent, StaticAnimation animation, String perilous) {
        if (ent != null && animation != null) {
            int animationduration = getAnimationDurationInMilliseconds(ent, animation);
            ent.getPersistentData().putString("Perilous", perilous);
            Entity target = TargetUtil.getTarget(ent);
            if (target instanceof Player) {
                playPerilous(target);
                perilousParticle(target);

            }
            TimerUtil.schedule(() -> {
                if (ent != null) {
                    ent.getPersistentData().remove("Perilous");
                }
            }, animationduration, TimeUnit.MILLISECONDS);
        }
    }

    static int getAnimationDurationInMilliseconds(Entity ent, StaticAnimation animation) {
        if (ent != null && animation != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                float duration = animation.getTotalTime() / animation.getPlaySpeed(livingEntityPatch, animation);
                return (int) (duration * 500);
            }
        }
        return 0;
    }


    static StaticAnimation getCurrentAnimation(Entity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                DynamicAnimation var5 = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getAnimation().orElse(null);
                if (var5 instanceof StaticAnimation animation) {
                    return animation;
                }
            }
        }
        return null;
    }

    static InteractionHand getAttackingHand(Entity ent) {
        if (ent != null) {
            DynamicAnimation animation = getCurrentAnimation(ent);
            if (animation instanceof AttackAnimation attackAnimation) {
                return attackAnimation.getPhaseByTime(animation.getAnimationClip().getClipTime()).hand;
            }
        }
        return null;
    }


    static void resizeBoundingBox(Entity ent, float width, float height) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.resetSize(new EntityDimensions(width, height, true));
            }
        }
    }

    static void preventAttack(Entity ent, int duration) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, false);
            TimerUtil.schedule(() -> livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, true), duration, TimeUnit.MILLISECONDS);
        }
    }

    static void applyStun(Entity ent, StunType stuntype, float duration) {
        if (ent != null) {
            LivingEntityPatch<?> livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingent != null) {
                livingent.applyStun(stuntype, duration);
            }
        }
    }

    static boolean checkAttack(Entity ent) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            return livingent.getEntityState().attacking();
        }
        return false;
    }

    static void cancelMotion(Entity ent) {
        if (ent instanceof LivingEntity living) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {

                livingEntityPatch.cancelItemUse();
            }
        }
    }
}