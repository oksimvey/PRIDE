package com.robson.pride.mixins;

import com.robson.pride.api.elements.ElementBase;
import com.robson.pride.api.mechanics.ParticleTracking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.client.model.ItemSkin;
import yesman.epicfight.api.client.model.ItemSkins;
import yesman.epicfight.client.particle.TrailParticle;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Optional;

@Mixin(TrailParticle.Provider.class)
@OnlyIn(Dist.CLIENT)
public abstract class TrailParticleProviderMixin implements ParticleProvider<SimpleParticleType> {
    @Shadow @Final private SpriteSet spriteSet;

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
        if (entity != null) {
            LivingEntityPatch<?> entitypatch = (LivingEntityPatch) EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            StaticAnimation animation = AnimationManager.getInstance().byId(animid);
            Optional<List<TrailInfo>> trailInfo = animation.getProperty(ClientAnimationProperties.TRAIL_EFFECT);
            TrailInfo result = (TrailInfo)((List)trailInfo.get()).get(idx);
            if (result.hand != null) {
                ItemStack stack = ((LivingEntity)entitypatch.getOriginal()).getItemInHand(result.hand);
                ItemSkin itemSkin = ItemSkins.getItemSkin(stack.getItem());
                if (itemSkin != null && itemSkin.trailInfo() != null) {
                    result = itemSkin.trailInfo().overwrite(result);
                    ElementBase elementBase = ParticleTracking.getItemElementForImbuement(stack, entitypatch.getOriginal());
                    if (elementBase != null) {
                        result = elementBase.getTrailInfo(result);
                    }
                }
            }
            if (entitypatch != null && animation != null && trailInfo.isPresent()) {
                return TrailParticleAccessor.createTrailParticle(level, entitypatch, entitypatch.getArmature().searchJointById(jointId), animation, result, this.spriteSet);
            }
        }
        return null;
    }
}
