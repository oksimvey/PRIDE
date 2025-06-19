package com.robson.pride.api.skillcore;

import com.robson.pride.api.utils.AnimUtils;
import net.minecraft.world.entity.Entity;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

public class SkillAnimation {

    private AnimationManager.AnimationAccessor<? extends StaticAnimation> animation;

    private Runnable function;

    public SkillAnimation(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, Runnable function) {
        this.animation = animation;
        this.function = function;
    }

    public void play(Entity ent) {
        if (ent != null) {
            if (this.function != null) {
                function.run();
            }
            AnimUtils.playAnim(ent, this.animation, 0);
        }
    }

    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getAnimation() {
        return this.animation;
    }

    public int getDuration(Entity ent) {
        return AnimUtils.getAnimationDurationInMilliseconds(ent, this.animation.get());
    }
}
