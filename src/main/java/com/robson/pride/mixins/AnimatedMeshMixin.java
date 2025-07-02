package com.robson.pride.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.api.client.model.*;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.EpicFightRenderTypes;
import yesman.epicfight.client.renderer.shader.AnimationShaderInstance;
import yesman.epicfight.client.renderer.shader.ShaderParser;
import yesman.epicfight.config.ClientConfig;

import java.util.List;
import java.util.Map;

import static com.robson.pride.events.onClientTick.playerBuffer;
import static com.robson.pride.events.onClientTick.playerStack;

@Mixin(SkinnedMesh.class)
@OnlyIn(Dist.CLIENT)
public abstract class AnimatedMeshMixin extends StaticMesh<SkinnedMesh.SkinnedMeshPart> {


    public AnimatedMeshMixin(@Nullable Map<String, Number[]> arrayMap, @Nullable Map<MeshPartDefinition, List<VertexBuilder>> partBuilders, @Nullable StaticMesh<SkinnedMesh.SkinnedMeshPart> parent, RenderProperties renderProperties) {
        super(arrayMap, partBuilders, parent, renderProperties);
    }

    @Shadow(remap = false)
    public abstract void drawWithShader(PoseStack poseStack, AnimationShaderInstance animationShaderInstance, int packedLight, float r, float g, float b, float a, int overlay, Armature armature, OpenMatrix4f[] poses);

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void draw(PoseStack poseStack, MultiBufferSource bufferSources, RenderType renderType, int packedLight, float r, float g, float b, float a, int overlay, Armature armature, OpenMatrix4f[] poses) {
        if (ClientConfig.activateAnimationShader && playerStack == null || playerBuffer == null || playerBuffer != bufferSources || playerStack != poseStack && armature.getJointNumber() <= ShaderParser.SHADER_ARRAY_LIMIT) {
            renderType.setupRenderState();
            AnimationShaderInstance animationShader = EpicFightRenderTypes.getAnimationShader(renderType);
            this.drawWithShader(poseStack, animationShader, packedLight, 1F, 1F, 1.0F, 1.0F, overlay, armature, poses);
            renderType.clearRenderState();
            return;
        }
        this.drawPosed(poseStack, bufferSources.getBuffer(EpicFightRenderTypes.getTriangulated(renderType)), Mesh.DrawingFunction.NEW_ENTITY, packedLight, r, g, b, a, overlay, armature, poses);
    }
}
