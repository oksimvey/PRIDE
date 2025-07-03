package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.AnimUtils;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.List;

public class PlayAnimationsAction extends ActionBase {

    private final List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> animations;

    private final float convert;

    private byte currentAnimation = 0;

    public PlayAnimationsAction(List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> animations, float convert) {
        this.animations = animations;
        this.convert = convert;
    }

    protected void start(PrideMobPatch<?> ent) {
        if (ent != null) {
            if (currentAnimation >= animations.size()){
                currentAnimation = 0;
            }
            AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = animations.get(currentAnimation);
            if (animation != null) {
                AnimUtils.playAnim(ent.getOriginal(), animation, convert);
                currentAnimation++;
              }
        }
    }
}
