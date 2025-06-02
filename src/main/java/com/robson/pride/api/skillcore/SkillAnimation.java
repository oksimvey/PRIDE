package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.types.StaticAnimation;

public class SkillAnimation {

    private StaticAnimation animation;

    private Runnable function;

    public SkillAnimation(StaticAnimation animation, Runnable function){
        this.animation = animation;
        this.function = function;
    }

    public void play(Entity ent){
        if (ent != null){
            if (this.function != null) {
                function.run();
            }
            AnimUtils.playAnim(ent, this.animation, 0);
        }
    }

    public int getDuration(Entity ent){
        return  AnimUtils.getAnimationDurationInMilliseconds(ent, this.animation);
    }
}
