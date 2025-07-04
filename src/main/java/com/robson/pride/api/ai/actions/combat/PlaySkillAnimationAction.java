package com.robson.pride.api.ai.actions.combat;

import com.robson.pride.api.ai.actions.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlaySkillAnimationAction extends ActionBase {

    private final List<SkillAnimation> animations;

    private byte currentAnimation = 0;

    public PlaySkillAnimationAction(List<SkillAnimation> animations) {
        this.animations = animations;
    }

    public void start(PrideMobPatch<?> ent) {
        if (ent != null && currentAnimation <= animations.size()) {
            SkillAnimation animation = animations.get(currentAnimation);
            if (animation != null) {
               animation.play(ent.getOriginal());
                currentAnimation++;
                TimerUtil.schedule(()-> start(ent), AnimUtils.getAnimationDurationInMilliseconds(ent.getOriginal(), animation.getAnimation().get()), TimeUnit.MILLISECONDS);
            }
        }
    }
}
