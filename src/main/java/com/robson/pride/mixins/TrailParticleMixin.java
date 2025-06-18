package com.robson.pride.mixins;

import com.robson.pride.particles.PrideTrailParticle;
import com.robson.pride.registries.ParticleRegister;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.*;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.particle.AnimationTrailParticle;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;

@Mixin(value = AnimationTrailParticle.Provider.class, remap = false)
@OnlyIn(Dist.CLIENT)
public abstract class TrailParticleMixin implements ParticleProvider<SimpleParticleType> {
    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        int eid = (int)Double.doubleToRawLongBits(x);
        int animid = (int)Double.doubleToRawLongBits(z);
        int jointId = (int)Double.doubleToRawLongBits(xSpeed);
        int idx = (int)Double.doubleToRawLongBits(ySpeed);
        Entity entity = level.getEntity(eid);
        if (entity == null) {
            return null;
        } else {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (entitypatch == null) {
                return null;
            } else {
                AnimationManager.AnimationAccessor<? extends StaticAnimation> animation = AnimationManager.byId(animid);
                if (animation == null) {
                    return null;
                } else {
                    Optional<List<TrailInfo>> trailInfo = ((StaticAnimation)animation.get()).getProperty(ClientAnimationProperties.TRAIL_EFFECT);
                    if (trailInfo.isEmpty()) {
                        return null;
                    } else {
                        TrailInfo result = (TrailInfo)((List)trailInfo.get()).get(idx);
                        if (result.hand() != null) {
                            ItemStack stack = ((LivingEntity)entitypatch.getOriginal()).getItemInHand(result.hand());
                            RenderItemBase renderItemBase = ClientEngine.getInstance().renderEngine.getItemRenderer(stack);
                            if (renderItemBase != null && renderItemBase.trailInfo() != null) {
                                result = renderItemBase.trailInfo().overwrite(result);
                            }
                        }

                        return result.playable() ? new PrideTrailParticle(level, entitypatch, entitypatch.getArmature().searchJointById(jointId), animation, result) : null;
                    }
                }
            }
        }
    }
}
