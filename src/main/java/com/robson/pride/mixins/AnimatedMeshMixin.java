package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.api.client.model.AnimatedMesh;
import yesman.epicfight.api.client.model.AnimatedVertexBuilder;
import yesman.epicfight.api.client.model.Mesh;
import yesman.epicfight.api.client.model.MeshPartDefinition;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.shader.AnimationShaderInstance;
import yesman.epicfight.main.EpicFightMod;

import java.util.List;
import java.util.Map;

import static com.robson.pride.events.onClientTick.playerBuffer;
import static com.robson.pride.events.onClientTick.playerStack;

@Mixin(AnimatedMesh.class)
@OnlyIn(Dist.CLIENT)
public abstract class AnimatedMeshMixin extends Mesh<AnimatedMesh.AnimatedModelPart, AnimatedVertexBuilder> {

    public AnimatedMeshMixin(@Nullable Map<String, float[]> arrayMap, @Nullable Map<MeshPartDefinition, List<AnimatedVertexBuilder>> partBuilders, @Nullable Mesh<AnimatedMesh.AnimatedModelPart, AnimatedVertexBuilder> parent, RenderProperties renderProperties) {
        super(arrayMap, partBuilders, parent, renderProperties);
    }

    @Shadow(remap = false)
    public abstract void drawWithShader(PoseStack poseStack, AnimationShaderInstance animationShaderInstance, int packedLight, float r, float g, float b, float a, int overlay, Armature armature, OpenMatrix4f[] poses);

    @Shadow(remap = false)
    public abstract void drawToBuffer(PoseStack poseStack, VertexConsumer builder, Mesh.DrawingFunction drawingFunction, int packedLight, float r, float g, float b, float a, int overlay, Armature armature, OpenMatrix4f[] poses);


    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
     public void draw(PoseStack poseStack, MultiBufferSource multiBufferSource, RenderType renderType, int packedLight, float r, float g, float b, float a, int overlay, Armature armature, OpenMatrix4f[] poses) {
        if (EpicFightMod.CLIENT_CONFIGS.useAnimationShader.getValue() && (playerStack == null || playerBuffer == null || playerBuffer != multiBufferSource ||  playerStack != poseStack)) {
            renderType.setupRenderState();
            AnimationShaderInstance animationShader = EpicFightRenderTypes.getAnimationShader(renderType);
            this.drawWithShader(poseStack, animationShader, packedLight, 1.0F, 1.0F, 1.0F, 1.0F, overlay, armature, poses);
            renderType.clearRenderState();
        }
        else{
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(EpicFightRenderTypes.getTriangulated(renderType));
            this.drawToBuffer(poseStack, vertexConsumer, Mesh.DrawingFunction.ENTITY_TEXTURED, packedLight, r, g, b, a, overlay, armature, poses);
        }
    }
}
