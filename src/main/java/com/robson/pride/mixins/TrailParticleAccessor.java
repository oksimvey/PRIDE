package com.robson.pride.mixins;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.particle.TrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(TrailParticle.class)
@OnlyIn(Dist.CLIENT)
public interface TrailParticleAccessor {
    @Invoker("<init>")
    static TrailParticle createTrailParticle(ClientLevel level,
                                             LivingEntityPatch<?> entitypatch,
                                             Joint joint,
                                             StaticAnimation animation,
                                             TrailInfo trailInfo,
                                             SpriteSet spriteSet) {
        throw new AssertionError();
    }
}
