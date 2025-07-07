package com.robson.pride.particles;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.types.item.ElementData;
import com.robson.pride.api.mechanics.ParticleTracking;
import com.robson.pride.api.utils.math.BezierCurvef;
import com.robson.pride.api.utils.math.PrideVec3f;
import com.robson.pride.api.item.CustomItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.*;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.particle.AbstractTrailParticle;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;

public class PrideTrailParticle extends AbstractTrailParticle<LivingEntityPatch<?>> {

    protected final Joint joint;
    protected final AssetAccessor<? extends StaticAnimation> animation;
    protected final List<AbstractTrailParticle.TrailEdge> invisibleTrailEdges;
    protected Pose lastPose;
    protected JointTransform lastTransform;
    protected TrailInfo trailInfo;

    public PrideTrailParticle(ClientLevel level, LivingEntityPatch<?> owner, Joint joint, AssetAccessor<? extends StaticAnimation> animation, TrailInfo trailInfo) {
        super(level, owner, trailInfo);
        this.joint = joint;
        this.animation = animation;
        this.invisibleTrailEdges = Lists.newLinkedList();
        ItemStack item = owner.getValidItemInHand(trailInfo.hand());
        if (item.getTag() == null) {
            return;
        }
        if (item.getItem() instanceof CustomItem && ServerDataManager.getWeaponData(item) != null){
            trailInfo = ServerDataManager.getWeaponData(item).getTrailInfo(trailInfo);
            this.trailInfo = trailInfo;
        }
        int r = (int) trailInfo.rCol();
        int g = (int) trailInfo.gCol();
        int b = (int) trailInfo.bCol();
        if (ParticleTracking.shouldRenderParticle(item)) {
            ElementData element = ParticleTracking.getItemElementForImbuement(item);
            if (element != null) {
                ItemRenderingParams params = element.getItemRenderingParams();
                if (params != null) {
                    r = params.getColor().r();
                    g = params.getColor().g();
                    b = params.getColor().b();
                }
            }
        }
        Pose prevPose = ((LivingEntityPatch)this.owner).getAnimator().getPose(0.0F);
        Pose middlePose = ((LivingEntityPatch)this.owner).getAnimator().getPose(0.5F);
        Pose currentPose = ((LivingEntityPatch)this.owner).getAnimator().getPose(1.0F);
        PrideVec3f posOld = PrideVec3f.fromVec3(((LivingEntity)((LivingEntityPatch)this.owner).getOriginal()).getPosition(0.0F));
        PrideVec3f posMid = PrideVec3f.fromVec3(((LivingEntity)((LivingEntityPatch)this.owner).getOriginal()).getPosition(0.5F));
        PrideVec3f posCur = PrideVec3f.fromVec3(((LivingEntity)((LivingEntityPatch)this.owner).getOriginal()).getPosition(1.0F));
        this.lastPose = currentPose;
        this.lastPos = posCur.toVec3();
        this.lastTransform = JointTransform.fromMatrix(((LivingEntityPatch)this.owner).getModelMatrix(1.0F));
        OpenMatrix4f prvmodelTf = OpenMatrix4f.createTranslation((float) posOld.x(), (float) posOld.y(), (float) posOld.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(((LivingEntityPatch)this.owner).getModelMatrix(0.0F));
        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float) posMid.x(), (float) posMid.y(), (float) posMid.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(((LivingEntityPatch)this.owner).getModelMatrix(0.5F));
        OpenMatrix4f curModelTf = OpenMatrix4f.createTranslation((float) posCur.x(), (float) posCur.y(), (float) posCur.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(((LivingEntityPatch)this.owner).getModelMatrix(1.0F));
        OpenMatrix4f prevJointTf = ((LivingEntityPatch)this.owner).getArmature().getBindedTransformFor(prevPose, this.joint).mulFront(prvmodelTf);
        OpenMatrix4f middleJointTf = ((LivingEntityPatch)this.owner).getArmature().getBindedTransformFor(middlePose, this.joint).mulFront(middleModelTf);
        OpenMatrix4f currentJointTf = ((LivingEntityPatch)this.owner).getArmature().getBindedTransformFor(currentPose, this.joint).mulFront(curModelTf);
        Vec3 prevStartPos = OpenMatrix4f.transform(prevJointTf, trailInfo.start());
        Vec3 prevEndPos = OpenMatrix4f.transform(prevJointTf, trailInfo.end());
        Vec3 middleStartPos = OpenMatrix4f.transform(middleJointTf, trailInfo.start());
        Vec3 middleEndPos = OpenMatrix4f.transform(middleJointTf, trailInfo.end());
        Vec3 currentStartPos = OpenMatrix4f.transform(currentJointTf, trailInfo.start());
        Vec3 currentEndPos = OpenMatrix4f.transform(currentJointTf, trailInfo.end());
        this.invisibleTrailEdges.add(new AbstractTrailParticle.TrailEdge(prevStartPos, prevEndPos, trailInfo.trailLifetime()));
        this.invisibleTrailEdges.add(new AbstractTrailParticle.TrailEdge(middleStartPos, middleEndPos, trailInfo.trailLifetime()));
        this.invisibleTrailEdges.add(new AbstractTrailParticle.TrailEdge(currentStartPos, currentEndPos, trailInfo.trailLifetime()));
        this.rCol = Math.max(r, 0.0F);
        this.gCol = Math.max(g, 0.0F);
        this.bCol = Math.max(b, 0.0F);
        this.setColor(r, g, b);
        if (this.trailInfo.texturePath() != null) {
            TextureManager texturemanager = Minecraft.getInstance().getTextureManager();
            AbstractTexture abstracttexture = texturemanager.getTexture(this.trailInfo.texturePath());
            RenderSystem.bindTexture(abstracttexture.getId());
            RenderSystem.texParameter(3553, 10242, 33071);
            RenderSystem.texParameter(3553, 10243, 33071);
        }

    }

    protected boolean canContinue() {
        AnimationPlayer animPlayer = ((LivingEntityPatch)this.owner).getAnimator().getPlayerFor(this.animation);
        return ((LivingEntity)((LivingEntityPatch)this.owner).getOriginal()).isAlive() && this.animation == animPlayer.getRealAnimation() && animPlayer.getElapsedTime() <= this.trailInfo.endTime();
    }

    protected boolean canCreateNextCurve() {
        AnimationPlayer animPlayer = ((LivingEntityPatch)this.owner).getAnimator().getPlayerFor(this.animation);
        return TrailInfo.isValidTime(this.trailInfo.fadeTime()) && this.trailInfo.endTime() < animPlayer.getElapsedTime() ? false : super.canCreateNextCurve();
    }
    protected void createNextCurve() {
        AnimationPlayer animPlayer = ((LivingEntityPatch) this.owner).getAnimator().getPlayerFor(this.animation);
        boolean isTrailInvisible = ((DynamicAnimation) animPlayer.getAnimation().get()).isLinkAnimation() || animPlayer.getElapsedTime() <= this.trailInfo.startTime();
        boolean isFirstTrail = this.trailEdges.isEmpty();
        boolean needCorrection = !isTrailInvisible && isFirstTrail;
        if (needCorrection) {
            float startCorrection = Math.max((this.trailInfo.startTime() - animPlayer.getPrevElapsedTime()) / (animPlayer.getElapsedTime() - animPlayer.getPrevElapsedTime()), 0.0F);
            this.startEdgeCorrection = (float) (this.trailInfo.interpolateCount() * 2) * startCorrection;
        }
        TrailInfo trailInfo = this.trailInfo;
        Pose prevPose = this.lastPose;
        Pose currentPose = ((LivingEntityPatch) this.owner).getAnimator().getPose(1.0F);
        Pose middlePose = Pose.interpolatePose(prevPose, currentPose, 0.5F);
        PrideVec3f posOld = PrideVec3f.fromVec3(this.lastPos);
        PrideVec3f posCur = PrideVec3f.fromVec3(((LivingEntityPatch) this.owner).getOriginal().getPosition(1.0F));
        PrideVec3f posMid = PrideVec3f.fromVec3(MathUtils.lerpVector(posOld.toVec3(), posCur.toVec3(), 0.5F));
        OpenMatrix4f prevModelMatrix = this.lastTransform.toMatrix();
        OpenMatrix4f curModelMatrix = ((LivingEntityPatch) this.owner).getModelMatrix(1.0F);
        JointTransform currentTransform = JointTransform.fromMatrix(curModelMatrix);
        OpenMatrix4f prvmodelTf = OpenMatrix4f.createTranslation((float) posOld.x(), (float) posOld.y(), (float) posOld.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(prevModelMatrix);
        OpenMatrix4f middleModelTf = OpenMatrix4f.createTranslation((float) posMid.x(), (float) posMid.y(), (float) posMid.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(JointTransform.interpolate(this.lastTransform, currentTransform, 0.5F).toMatrix());
        OpenMatrix4f curModelTf = OpenMatrix4f.createTranslation((float) posCur.x(), (float) posCur.y(), (float) posCur.z()).rotateDeg(180.0F, yesman.epicfight.api.utils.math.Vec3f.Y_AXIS).mulBack(curModelMatrix);
        OpenMatrix4f prevJointTf = ((LivingEntityPatch) this.owner).getArmature().getBindedTransformFor(prevPose, this.joint).mulFront(prvmodelTf);
        OpenMatrix4f middleJointTf = ((LivingEntityPatch) this.owner).getArmature().getBindedTransformFor(middlePose, this.joint).mulFront(middleModelTf);
        OpenMatrix4f currentJointTf = ((LivingEntityPatch) this.owner).getArmature().getBindedTransformFor(currentPose, this.joint).mulFront(curModelTf);
        PrideVec3f prevStartPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(prevJointTf, trailInfo.start()));
        PrideVec3f prevEndPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(prevJointTf, trailInfo.end()));
        PrideVec3f middleStartPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(middleJointTf, trailInfo.start()));
        PrideVec3f middleEndPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(middleJointTf, trailInfo.end()));
        PrideVec3f currentStartPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(currentJointTf, trailInfo.start()));
        PrideVec3f currentEndPos = PrideVec3f.fromVec3(OpenMatrix4f.transform(currentJointTf, trailInfo.end()));
        List<PrideVec3f> finalStartPositions;
        List<PrideVec3f> finalEndPositions;
        boolean visibleTrail;
        if (isTrailInvisible) {
            finalStartPositions = Lists.newArrayList();
            finalEndPositions = Lists.newArrayList();
            finalStartPositions.add(prevStartPos);
            finalStartPositions.add(middleStartPos);
            finalEndPositions.add(prevEndPos);
            finalEndPositions.add(middleEndPos);
            this.invisibleTrailEdges.clear();
            visibleTrail = false;
        } else {
            List<PrideVec3f> startPosList = Lists.newArrayList();
            List<PrideVec3f> endPosList = Lists.newArrayList();
            AbstractTrailParticle.TrailEdge edge1;
            AbstractTrailParticle.TrailEdge edge2;
            if (isFirstTrail) {
                int lastIdx = this.invisibleTrailEdges.size() - 1;
                edge1 = (AbstractTrailParticle.TrailEdge) this.invisibleTrailEdges.get(lastIdx);
                edge2 = new AbstractTrailParticle.TrailEdge(prevStartPos.toVec3(), prevEndPos.toVec3(), -1);
            } else {
                edge1 = (AbstractTrailParticle.TrailEdge) this.trailEdges.get(this.trailEdges.size() - (this.trailInfo.interpolateCount() / 2 + 1));
                edge2 = (AbstractTrailParticle.TrailEdge) this.trailEdges.get(this.trailEdges.size() - 1);
                ++edge2.lifetime;
            }

            startPosList.add(PrideVec3f.fromVec3(edge1.start));
            endPosList.add(PrideVec3f.fromVec3(edge1.end));
            startPosList.add(PrideVec3f.fromVec3(edge2.start));
            endPosList.add(PrideVec3f.fromVec3(edge2.end));
            startPosList.add(middleStartPos);
            endPosList.add(middleEndPos);
            startPosList.add(currentStartPos);
            endPosList.add(currentEndPos);
            finalStartPositions = BezierCurvef.getBezierInterpolatedPoints(startPosList, 1, 3, this.trailInfo.interpolateCount());
            finalEndPositions = BezierCurvef.getBezierInterpolatedPoints(endPosList, 1, 3, this.trailInfo.interpolateCount());
            if (!isFirstTrail) {
                finalStartPositions.remove(0);
                finalEndPositions.remove(0);
            }

            visibleTrail = true;
        }
        this.makeTrailEdge(finalStartPositions, finalEndPositions, visibleTrail ? this.trailEdges : this.invisibleTrailEdges);
        this.lastPos = posCur.toVec3();
        this.lastPose = currentPose;
        this.lastTransform = currentTransform;
    }

    protected void makeTrailEdge(List<PrideVec3f> startPositions, List<PrideVec3f> endPositions, List<TrailEdge> dest) {
        for (int i = 0; i < startPositions.size(); ++i) {
            dest.add(new TrailEdge(startPositions.get(i).toVec3(), endPositions.get(i).toVec3(), this.trailInfo.trailLifetime()));
        }
    }
}
