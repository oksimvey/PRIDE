package com.robson.pride.api.utils;

import com.robson.pride.registries.ItemsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.network.server.SPMoveAndPlayAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.HumanoidMobPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.api.mechanics.PerilousAttack.perilousParticle;
import static com.robson.pride.api.mechanics.PerilousAttack.playPerilous;

public class AnimUtils {

    public static void playAnim(Entity ent, AnimationManager.AnimationAccessor animation, float convert) {
        TimerUtil.schedule(() -> {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                if (animation != null) {
                    if (livingEntityPatch instanceof HumanoidMobPatch AHPatch) {
                        AHPatch.playAnimationSynchronized(animation, convert);
                    } else livingEntityPatch.playAnimationSynchronized(animation, convert);
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    public static void playAnim(Entity ent, StaticAnimation animation, float convert) {
        TimerUtil.schedule(() -> {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                if (animation != null) {
                    if (livingEntityPatch instanceof HumanoidMobPatch AHPatch) {
                        AHPatch.playAnimationSynchronized(animation.getAccessor(), convert);
                    } else livingEntityPatch.playAnimationSynchronized(animation.getAccessor(), convert);
                }
            }
        }, 10, TimeUnit.MILLISECONDS);
    }

    public static boolean allowShoot(Entity ent) {
        if (ent != null) {
            if (ent instanceof Player player) {

            }
            return true;
        }
        return false;
    }

    public static void playAnimWithPerilous(Entity ent, StaticAnimation animation, String perilous, float convert) {
        AnimUtils.playAnim(ent, animation, convert);
        addPerilousToAnim(ent, animation, perilous);
    }

    public static void addPerilousToAnim(Entity ent, StaticAnimation animation, String perilous) {
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

    public static int getAnimationDurationInMilliseconds(Entity ent, StaticAnimation animation) {
        if (ent != null && animation != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                float duration = animation.getTotalTime() / animation.getPlaySpeed(livingEntityPatch, animation);
                return (int) (duration * 500);
            }
        }
        return 0;
    }

    public static StaticAnimation getCurrentAnimation(Entity ent) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                DynamicAnimation var5 = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor( null)).getAnimation().orElse(null);
                if (var5 instanceof StaticAnimation animation) {
                    return animation;
                }
            }
        }
        return null;
    }

    public static InteractionHand getAttackingHand(Entity ent) {
        if (ent != null) {
        }
        return null;
    }

    public static void playAnimByString(Entity ent, String anim, float convert) {
        StaticAnimation animation = AnimationManager.byKey(anim).orElse(null) ;
        playAnim(ent, animation, convert);
    }


    public static void resizeBoundingBox(Entity ent, float width, float height) {
        if (ent != null) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.resetSize(new EntityDimensions(width, height, true));
            }
        }
    }

    public static byte getDodgeType(Player player) {
        if (Minecraft.getInstance().options.keyUp.isDown()) {
            return 1;
        }
        if (Minecraft.getInstance().options.keyLeft.isDown()) {
            return 2;
        }
        if (Minecraft.getInstance().options.keyRight.isDown()) {
            return 3;
        }
        return 4;
    }

    public static void preventAttack(Entity ent, int duration) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, false);
            TimerUtil.schedule(() -> livingent.getEntityState().setState(EntityState.CAN_SKILL_EXECUTION, true), duration, TimeUnit.MILLISECONDS);
        }
    }

    public static void applyStun(Entity ent, StunType stuntype, float duration) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {

            livingent.applyStun(stuntype, duration);
        }
    }

    public static boolean checkAttack(Entity ent) {
        LivingEntityPatch livingent = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (livingent != null) {
            return livingent.getEntityState().attacking();
        }
        return false;
    }

    public static void rotateToEntity(Entity ent1, Entity ent2) {
        LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent1, LivingEntityPatch.class);
        if (livingEntityPatch != null && ent2 != null) {
            livingEntityPatch.rotateTo(ent2, 1000, false);
            livingEntityPatch.updateEntityState();
        }
    }

    public static void cancelMotion(Entity ent) {
        if (ent instanceof LivingEntity living) {
            LivingEntityPatch livingEntityPatch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
            if (livingEntityPatch != null) {

                livingEntityPatch.cancelItemUse();
            }
        }
    }
}