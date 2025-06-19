package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.ai.combat.Condition;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.skillcore.SkillAnimation;
import com.robson.pride.api.utils.AnimUtils;
import com.robson.pride.api.utils.TimerUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PlaySkillAnimation extends ActionBase {

    private final List<SkillAnimation> animations;

    private byte currentAnimation = 0;

    public PlaySkillAnimation(List<Condition> conditions, List<SkillAnimation> animations) {
        super(conditions);
        this.animations = animations;
    }

    protected void start(PrideMobPatch<?> ent) {
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
