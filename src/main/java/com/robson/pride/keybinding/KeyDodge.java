package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.KeyHandler;
import com.robson.pride.api.keybinding.LongPressKey;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.StaminaUtils;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

public  class KeyDodge extends LongPressKey {

    private byte input;

    public KeyDodge() {
        super((byte) 5);
    }

    @Override
    public void onPress(Player player) {
        this.input = KeyHandler.getInputForDodge();
        AnimUtils.rotatePlayer(player, 0);
    }

    @Override
    public void onReleaseAction(Player player) {
        if (!this.longPressTriggered) {
            StaminaUtils.consumeStamina(player, 2);
            AnimUtils.rotatePlayer(player, 0);
            AnimationManager.AnimationAccessor<? extends StaticAnimation> motion = switch (input) {
                case 1 -> Animations.BIPED_STEP_FORWARD;
                case 2 -> Animations.BIPED_STEP_RIGHT;
                case 3 -> Animations.BIPED_STEP_LEFT;
                default -> Animations.BIPED_STEP_BACKWARD;
            };
            AnimUtils.playAnim(player, motion, 0);
        }
    }

    @Override
    public void onLongPress(Player player) {
        StaminaUtils.consumeStamina(player, 3);
        float rotmodifier = 0;
        if (input == 2) {
            rotmodifier = 90;
        } else if (input == 3) {
            rotmodifier = -90;
        }
        AnimUtils.rotatePlayer(player, rotmodifier);
        AnimationManager.AnimationAccessor<? extends StaticAnimation> motion = switch (input) {
            case 1, 3, 2 -> Animations.BIPED_ROLL_FORWARD;
            default -> Animations.BIPED_ROLL_BACKWARD;
        };
        AnimUtils.playAnim(player, motion, 0);
    }
}
