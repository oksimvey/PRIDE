package com.robson.pride.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.particles.SimpleParticleType;

import net.minecraft.client.particle.*;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.concurrent.ConcurrentHashMap;

public class StringParticle extends TextureSheetParticle {

    private final Font fontRenderer = Minecraft.getInstance().font;
    private float visualDY = 0;
    private float prevVisualDY = 0;
    private float visualDX = 0;
    private float prevVisualDX = 0;
    private float fadeout = -1;
    private float prevFadeout = -1;
    private final int darkColor;
    private String particletext;
    private int lifetime;

    public void setparams(int lifetime, String text){
        this.lifetime = lifetime;
        this.particletext = text;
    }

    public enum StringParticleTypes {
        WHITE( 0xFFFFFF),
        RED( 0xFF0000),
        LIGHT_BLUE(0x80ffff);


        private final int color;

        StringParticleTypes(int color) {
            this.color = color;
        }

        public int getColor() {
            return color;
        }
    }

    StringParticleTypes type;

    public StringParticle(ClientLevel level, double x, double y, double z, double velX, double number, double type) {
        super(level, x, y, z, velX, 0, 0);
        this.type = StringParticleTypes.values()[(int) type];
        this.darkColor = FastColor.ARGB32.color(255, (int) (this.rCol * 0.25f), (int) (this.rCol * 0.25f), (int) (this.rCol * 0.25));
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float length = 6;
            this.prevFadeout = this.fadeout;
            this.fadeout = this.age > (lifetime - length) ? ((float) lifetime - this.age) / length : 1;

            this.prevVisualDY = this.visualDY;
            this.visualDY += (float) this.yd;
            this.prevVisualDX = this.visualDX;
            this.visualDX += (float) this.xd;

            if (Math.sqrt(Mth.square(this.visualDX * 1.5) + Mth.square(this.visualDY - 1)) < 1.9 - 1) {
                this.yd = this.yd / 2;
            } else {
                this.yd = 0;
                this.xd = 0;
            }
        }
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        if (this.particletext != null) {
            Component text1 = Component.literal(this.particletext);
            Vec3 cameraPos = camera.getPosition();
            float particleX = (float) (Mth.lerp(partialTicks, this.xo, this.x) - cameraPos.x());
            float particleY = (float) (Mth.lerp(partialTicks, this.yo, this.y) - cameraPos.y());
            float particleZ = (float) (Mth.lerp(partialTicks, this.zo, this.z) - cameraPos.z());
            int light = this.getLightColor(partialTicks);

            PoseStack poseStack = new PoseStack();
            poseStack.pushPose();

            // Position setup
            poseStack.translate(particleX, particleY, particleZ);
            double distanceFromCam = (new Vec3(particleX, particleY, particleZ)).length();
            double inc = Mth.clamp(distanceFromCam / 32.0F, 0.0F, 5.0F);

            // Movement and rotation
            poseStack.translate(0.0F, (1.0F + inc / 4.0F) * Mth.lerp(partialTicks, this.prevVisualDY, this.visualDY), 0.0F);
            float fadeout = Mth.lerp(partialTicks, this.prevFadeout, this.fadeout);
            float defScale = 0.006F;
            float scale = (float) (defScale * distanceFromCam);
            poseStack.mulPose(camera.rotation());
            poseStack.translate((1.0F + inc) * Mth.lerp(partialTicks, this.prevVisualDX, this.visualDX), 0.0F, 0.0F);

            // Scale and fade
            poseStack.scale(-scale, -scale, scale);
            poseStack.translate(0.0F, 4.0F * (1.0F - fadeout), 0.0F);
            poseStack.scale(fadeout, fadeout, fadeout);
            poseStack.translate(0.0F, -distanceFromCam / 10.0F, 0.0F);

            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();

            // Set up render states
            RenderSystem.enableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(false);

            // Render text
            float x1 = 0.5F - (float) this.fontRenderer.width(text1) / 2.0F;
            this.fontRenderer.drawInBatch(text1, x1, 0.0F, this.type.color, false,
                    poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, light);

            // Render shadow
            poseStack.translate(1.0F, 1.0F, 0.03);
            this.fontRenderer.drawInBatch(text1, x1, 0.0F, this.darkColor, false,
                    poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, light);

            // Clean up
            buffer.endBatch();
            RenderSystem.depthMask(true);
            RenderSystem.disableBlend();
            poseStack.popPose();
        }

    }


    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

            public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            StringParticle particle = new StringParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
