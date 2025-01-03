package com.robson.pride.mechanics;

import com.mojang.blaze3d.platform.NativeImage;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.entities.special.LeafEntity;
import com.robson.pride.registries.EntityRegister;
import de.cheaterpaul.fallingleaves.mixin.NativeImageAccessor;
import de.cheaterpaul.fallingleaves.util.TextureCache;
import de.cheaterpaul.fallingleaves.util.Wind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.system.MemoryUtil;
import yesman.epicfight.api.animation.Joint;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LeavesPhysics {

    private static final RandomSource renderRandom = RandomSource.create();

    public static void trySpawnLeafParticle(BlockState state, Level world, BlockPos pos, RandomSource random) {
        double x = (double)pos.getX() + random.nextDouble();
        double y = (double)pos.getY() - random.nextDouble() / 3.0;
        double z = (double)pos.getZ() + random.nextDouble();
        if (shouldSpawnParticle(world, pos, x, y, z)) {
            Minecraft client = Minecraft.getInstance();
            BakedModel model = client.getBlockRenderer().getBlockModel(state);
            ModelData modelData = model.getModelData(world, pos, state, ModelData.EMPTY);
            LeafEntity leafEntity = EntityRegister.LEAF_ENTITY.get().create(world);
            double[] color = getBlockTextureColor(state, world, pos, modelData);
            leafEntity.setColor((int) color[0], (int) color[1], (int) color[2]);
            leafEntity.setPos(x, y, z);
            world.addFreshEntity(leafEntity);
        }
    }

    private static boolean shouldSpawnParticle(Level world, BlockPos pos, double x, double y, double z) {
        return world.getBlockState(pos.below()).getBlock() instanceof AirBlock;
    }

    public static double[] averageColor(NativeImage image) {
        if (image.format() != NativeImage.Format.RGBA) {
            return new double[]{1.0, 1.0, 1.0};
        } else {
            long pixels = ((NativeImageAccessor) (Object)image).getPixels();
            if (pixels == 0L) {
                return new double[]{1.0, 1.0, 1.0};
            } else {
                double r = 0.0;
                double g = 0.0;
                double b = 0.0;
                int n = 0;
                int width = image.getWidth();
                int height = image.getHeight();

                for(int i = 0; i < width * height; ++i) {
                    int c = MemoryUtil.memGetInt(pixels + 4L * (long)i);
                    int cr = c & 255;
                    int cg = c >> 8 & 255;
                    int cb = c >> 16 & 255;
                    int ca = c >> 24 & 255;
                    if (ca != 0) {
                        r += cr;
                        g += cg;
                        b += cb;
                        ++n;
                    }
                }

                return new double[]{r / (double)n / 255.0, g / (double)n / 255.0, b / (double)n / 255.0};
            }
        }
    }

    public static double[] getBlockTextureColor(BlockState state, Level world, BlockPos pos, ModelData modelData) {
        Minecraft client = Minecraft.getInstance();
        BakedModel model = client.getBlockRenderer().getBlockModel(state);
        renderRandom.setSeed(state.getSeed(pos));
        List<BakedQuad> quads = model.getQuads(state, Direction.DOWN, renderRandom, modelData, RenderType.cutout());
        TextureAtlasSprite sprite;
        boolean shouldColor;
        if (!quads.isEmpty()) {
            BakedQuad quad = quads.get(0);
            sprite = quad.getSprite();
            shouldColor = quad.isTinted();
        } else {
            sprite = model.getParticleIcon(modelData);
            shouldColor = true;
        }

        SpriteContents contents = sprite.contents();
        ResourceLocation spriteId = contents.name();
        NativeImage texture = contents.byMipLevel[0];
        int blockColor = shouldColor ? client.getBlockColors().getColor(state, world, pos, 0) : -1;
        return calculateLeafColor(spriteId, texture, blockColor);
    }

    private static double[] calculateLeafColor(ResourceLocation spriteId, NativeImage texture, int blockColor) {
        double[] textureColor = TextureCache.INST.computeIfAbsent(spriteId, (loc) -> {
            double[] doubles = averageColor(texture);
            LogManager.getLogger().debug("{}: Calculated texture color {} ", spriteId, doubles);
            return new TextureCache.Data(doubles);
        }).getColor();
        if (blockColor != -1) {
            textureColor[0] *= (double)(blockColor >> 16 & 255) / 255.0;
            textureColor[1] *= (double)(blockColor >> 8 & 255) / 255.0;
            textureColor[2] *= (double)(blockColor & 255) / 255.0;
        }

        return textureColor;
    }


    public static void moveParticleOnEntity(LeafEntity leafEntity, Entity ent){
        if (ent != null && leafEntity.isAlive()){
            Vec3 pos1 = new Vec3(ent.getX(), ent.getY(), ent.getZ());
            loopParticleMoveOnEntity(leafEntity, ent);
            TimerUtil.schedule(()-> {
                if (ent != null && leafEntity.isAlive()) {
                    Vec3 pos2 = new Vec3(ent.getX(), ent.getY(), ent.getZ());
                    Vec3 finalpos = new Vec3(pos2.x - pos1.x, pos2.y - pos1.y, pos2.z - pos1.z);
                    if (finalpos != null) {
                        double entityareamultiplier = MathUtils.getTotalSpeed(finalpos.x, finalpos.y, finalpos.z) * 10;
                        double wradius = entityareamultiplier * ent.getBbWidth() / 2;
                        double yradius = entityareamultiplier * ent.getBbHeight() / 2;
                        AABB entityarea = new AABB(ent.getX() - wradius,
                                ent.getY() - yradius,
                                ent.getZ() - wradius,
                                ent.getX() + wradius,
                                ent.getY() + yradius,
                                ent.getZ() + wradius);
                        List<Entity> listent = ent.level().getEntities(ent, entityarea);
                        for (Entity entko : listent) {
                            if (entko == null || !(entko instanceof LeafEntity)) {
                                return;
                            }
                        }
                    }
                }
            }, 5, TimeUnit.MILLISECONDS);
        }
    }

    public static void loopParticleMoveOnEntity(LeafEntity leafEntity, Entity ent){
        TimerUtil.schedule(()->{
            if (ent != null && leafEntity.isAlive()){
                moveParticleOnEntity(leafEntity, ent);
            }
        }, 50, TimeUnit.MILLISECONDS);
    }

    public static void changeLeaveDelta(LeafEntity leafEntity, double deltax, double deltay, double deltaz){
        if (leafEntity.isAlive()){
            Random random = new Random();
            double windCoefficient = 2.6f + random.nextFloat() * 0.4f;
            double xd = 0;
            double zd = 0;
            double yd = -0.1;
            xd += (Wind.windX - xd) * windCoefficient / 60.0f;
            zd += (Wind.windZ - zd) * windCoefficient / 60.0f;
        }
    }

    public static void moveParticlesOnAttack(LocalPlayer renderer, Entity ent, Joint joint, LeafEntity leafEntity) {
        if (renderer != null && ent != null && leafEntity != null) {
            Vec3 pos1 = ArmatureUtils.getRawJoinPosition(renderer, ent, joint);
            TimerUtil.schedule(() -> {
                if (renderer != null && ent != null && leafEntity != null) {
                    Vec3 pos2 = ArmatureUtils.getRawJoinPosition(renderer, ent, joint);
                    if (pos1 != null && pos2 != null) {
                        Vec3 finalpos = new Vec3(pos2.x - (pos1.x), pos2.y - (pos1.y), pos2.z - (pos1.z));
                            leafEntity.setDeltaMovement(finalpos.x / 2, finalpos.y / 2, finalpos.z / 2);
                            if (leafEntity.distanceToSqr(pos2) < leafEntity.getBbWidth());
                    }
                }
            }, 5, TimeUnit.MILLISECONDS);
        }
    }
    public static void loopMoveParticleOnAttack(LocalPlayer renderer, Entity ent, Joint joint, LeafEntity leafEntity){
        TimerUtil.schedule(()->{
            if (ent != null && renderer != null && leafEntity != null){
                moveParticlesOnAttack(renderer, ent, joint, leafEntity);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
