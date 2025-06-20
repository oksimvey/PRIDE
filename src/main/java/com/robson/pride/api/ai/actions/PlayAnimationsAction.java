package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.ai.combat.Condition;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlayAnimationsAction extends ActionBase {

    private final List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> animations;

    private final float convert;

    private byte currentAnimation = 0;

    public PlayAnimationsAction(List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> animations, float convert) {
        this.animations = animations;
        this.convert = convert;
    }

    protected void start(PrideMobPatch<?> ent) {
        if (ent != null && currentAnimation <= animations.size()) {
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = animations.get(currentAnimation);
            if (animation != null) {
                ent.playAnimation(animation, convert);
                currentAnimation++;
                TimerUtil.schedule(()-> start(ent), AnimUtils.getAnimationDurationInMilliseconds(ent.getOriginal(), animation.get()), TimeUnit.MILLISECONDS);
            }
        }
    }
}
