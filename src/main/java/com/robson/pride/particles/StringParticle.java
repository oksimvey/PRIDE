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
import org.joml.Quaternionf;

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
    public void render(VertexConsumer consumer, Camera camera, float tickDelta) {
        if (this.particletext != null) {

            Vec3 cameraPos = camera.getPosition();

            float particleX = (float) (this.xo + (x - this.xo) * (double) tickDelta - cameraPos.x);
            float particleY = (float) (this.yo + (y - this.yo) * (double) tickDelta - cameraPos.y);
            float particleZ = (float) (this.zo + (z - this.zo) * (double) tickDelta - cameraPos.z);


            Font textRenderer = Minecraft.getInstance().font;

            String text = this.particletext;

            PoseStack matrixStack = new PoseStack();
            float textX = textRenderer.width(text) / -2.0F;
            matrixStack.pushPose();
            matrixStack.translate(particleX, particleY, particleZ);
            float size = 0.001F;
            matrixStack.scale(-size, -size, size);
            Quaternionf rot = null;
            try {
                rot = (Quaternionf) camera.rotation().clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
            if (rot != null) {
                rot.mul(-1);
                rot.conjugate();
                matrixStack.mulPose(rot);
                matrixStack.pushPose();
                matrixStack.translate(0.4f, 0.4f, 0.4f);
                textRenderer.drawInBatch(Component.literal(text), textX, 0.0F, this.type.color, false,
                        matrixStack.last().pose(), Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.NORMAL, 0, this.getLightColor(tickDelta));

                matrixStack.popPose();
            }
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
