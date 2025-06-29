package com.robson.pride.keybinding;

import com.github.leawind.thirdperson.config.AbstractConfig;
import com.robson.pride.api.cam.DynamicCam;
import com.robson.pride.api.keybinding.KeyHandler;
import com.robson.pride.api.keybinding.LongPressKey;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.StaminaUtils;
import com.robson.pride.api.utils.TimerUtil;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.DodgeAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.concurrent.TimeUnit;

public  class KeyDodge extends LongPressKey {


    public KeyDodge() {
        super((byte) 5);
    }


        @Override
        public void onPress(Player player) {
        }

        @Override
        public void onReleaseAction(Player player) {
        if (!this.longPressTriggered){
            StaminaUtils.consumeStamina(player, 2);
            AnimationManager.AnimationAccessor<? extends StaticAnimation> motion = switch (KeyHandler.getInputForDodge()) {
                case 1 -> Animations.BIPED_STEP_FORWARD;
                case 2 -> Animations.BIPED_STEP_RIGHT;
                case 3 -> Animations.BIPED_STEP_LEFT;
                default -> Animations.BIPED_STEP_BACKWARD;
            };
            CameraUtils.changeRotateMode(AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA);
            DynamicCam.modifier.put(player, AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA);
            int duration = (int) motion.get().getTotalTime() * 600;
            TimerUtil.schedule(()->
                    DynamicCam.modifier.remove(player), duration, TimeUnit.MILLISECONDS);
            AnimUtils.playAnim(player, motion, 0);
            }
        }


        @Override
        public void onLongPress(Player player) {
                    StaminaUtils.consumeStamina(player, 3);
                    AnimationManager.AnimationAccessor<? extends StaticAnimation> motion = switch (KeyHandler.getInputForDodge()) {
                        case 1, 3, 2 -> Animations.BIPED_ROLL_FORWARD;
                        default -> Animations.BIPED_STEP_BACKWARD;
                    };
            CameraUtils.changeRotateMode(AbstractConfig.PlayerRotateMode.INTEREST_POINT);
            int duration = (int) motion.get().getTotalTime() * 600;
            TimerUtil.schedule(()-> CameraUtils.changeRotateMode(AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA), duration, TimeUnit.MILLISECONDS);
            AnimUtils.playAnim(player, motion, 0);
        }
    };
