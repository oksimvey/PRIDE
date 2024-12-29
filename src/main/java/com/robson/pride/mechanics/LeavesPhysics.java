package com.robson.pride.mechanics;

import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.MathUtils;
import com.robson.pride.api.utils.TimerUtil;
import de.cheaterpaul.fallingleaves.config.LeafSettingsEntry;
import de.cheaterpaul.fallingleaves.init.ClientMod;
import de.cheaterpaul.fallingleaves.particle.FallingLeafParticle;
import de.cheaterpaul.fallingleaves.util.Wind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Vector3f;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static de.cheaterpaul.fallingleaves.util.LeafUtil.getBlockTextureColor;

public class LeavesPhysics {

    public static void spawnLeaves(LocalPlayer renderer) {
        if (renderer != null) {
            int radius = Minecraft.getInstance().options.renderDistance().get();
                Vector3f lookAngle = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector();
                double length = 24.0;
                double minX1 = renderer.getX() + lookAngle.x * radius - (radius);
                double minY1 = renderer.getY() - (radius * 0.5);
                double minZ1 = renderer.getZ() + lookAngle.z * radius - (radius);

                double maxX1 = renderer.getX() + lookAngle.x * length + (radius);
                double maxY1 = renderer.getY() + (radius * 1.5);
                double maxZ1 = renderer.getZ() + lookAngle.z * length + (radius);

                AABB minMax = new AABB(minX1, minY1, minZ1, maxX1, maxY1, maxZ1);
                int minX = Mth.floor(minMax.minX);
                int minY = Mth.floor(minMax.minY);
                int minZ = Mth.floor(minMax.minZ);
                int maxX = Mth.floor(minMax.maxX);
                int maxY = Mth.floor(minMax.maxY);
                int maxZ = Mth.floor(minMax.maxZ);

                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            BlockPos blockPos = new BlockPos(x, y, z);
                            BlockState state = renderer.level().getBlockState(blockPos);
                            Block block = renderer.level().getBlockState(blockPos).getBlock();
                            Block block1 = renderer.level().getBlockState(new BlockPos(x, y - 1, z)).getBlock();
                            if (block instanceof LeavesBlock && block1 instanceof AirBlock) {
                                Random random = new Random();
                                if (random.nextInt(50) == 1) {
                                    FallingLeafParticle.LeavesParticleFactory factory = new FallingLeafParticle.LeavesParticleFactory();
                                    double px = (double) blockPos.getX() + random.nextDouble();
                                    double py = (double) blockPos.getY() - random.nextDouble() / 3.0;
                                    double pz = (double) blockPos.getZ() + random.nextDouble();
                                    Minecraft client = Minecraft.getInstance();
                                    BakedModel model = client.getBlockRenderer().getBlockModel(state);
                                    ModelData modelData = model.getModelData(renderer.level(), blockPos, state, ModelData.EMPTY);
                                    LeafSettingsEntry leafSettings = ClientMod.getLeafSetting(BuiltInRegistries.BLOCK.getKey(state.getBlock()));
                                    double[] color = getBlockTextureColor(state, renderer.level(), blockPos, modelData);
                                    double r = color[0];
                                    double g = color[1];
                                    double b = color[2];
                                    Particle particle = factory.createParticle(null, (ClientLevel) renderer.level(), px, py, pz, r, g, b, getSpriteSetForSettings(state, leafSettings));
                                    if (particle != null) {
                                        Minecraft.getInstance().particleEngine.add(particle);
                                    }
                                    moveParticleOnEntity(particle, renderer);
                                    List<Entity> listent = renderer.level().getEntities(renderer, minMax);
                                    for (Entity entko : listent) {
                                        if (entko != null) {
                                            moveParticleOnEntity(particle, entko);
                                        }
                                    }
                                }
                            }
                    }
                }
            }
        }
    }

    private static SpriteSet getSpriteSetForSettings(BlockState blockState, @Nullable LeafSettingsEntry entry) {
        SpriteSet set = ClientMod.getSpriteForLeafType(entry == null ? ForgeRegistries.BLOCKS.getKey(blockState.getBlock()) : entry.leafType());
        if (set == null) {
            set = ClientMod.getSpriteForLeafType(entry != null && entry.considerAsConifer() ? ClientMod.CONIFER : ClientMod.DEFAULT);
        }

        return set;
    }

    public static void moveParticleOnEntity(Particle particle, Entity ent){
        if (ent != null && particle.isAlive()){
            Vec3 pos1 = new Vec3(ent.getX(), ent.getY(), ent.getZ());
            loopParticleMoveOnEntity(particle, ent);
            TimerUtil.schedule(()-> {
                if (ent != null && particle.isAlive()) {
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
                        if (entityarea.contains(particle.getPos())) {
                            if (ent.level().getBlockState(new BlockPos((int) particle.getPos().x, (int) (particle.getPos().y - 0.1), (int) particle.getPos().z)).getBlock() != Blocks.AIR){
                                changeLeaveDelta(particle, finalpos.x, 0.25, finalpos.z);
                            }
                            else changeLeaveDelta(particle, finalpos.x, finalpos.y, finalpos.z);
                        }
                    }
                }
            }, 5, TimeUnit.MILLISECONDS);
        }
    }

    public static void loopParticleMoveOnEntity(Particle particle, Entity ent){
        TimerUtil.schedule(()->{
            if (ent != null && particle.isAlive()){
                moveParticleOnEntity(particle, ent);
            }
        }, 50, TimeUnit.MILLISECONDS);
    }

    public static void changeLeaveDelta(Particle particle, double deltax, double deltay, double deltaz){
        if (particle.isAlive()){
            Random random = new Random();
            double windCoefficient = 2.6f + random.nextFloat() * 0.4f;
            double xd = 0;
            double zd = 0;
            double yd = -0.1;
            xd += (Wind.windX - xd) * windCoefficient / 60.0f;
            zd += (Wind.windZ - zd) * windCoefficient / 60.0f;

            particle.setParticleSpeed(xd + deltax / 2, yd + deltay / 2, zd + deltaz / 2);
        }
    }

    public static void moveParticlesOnAttack(LocalPlayer renderer, Entity ent, Joint joint, Particle particle) {
        if (renderer != null && ent != null && particle != null) {
            Vec3 pos1 = ArmatureUtils.getJoinPosition(renderer, ent, joint);
            TimerUtil.schedule(() -> {
                if (renderer != null && ent != null && particle != null) {
                    Vec3 pos2 = ArmatureUtils.getJoinPosition(renderer, ent, joint);
                    if (pos1 != null && pos2 != null) {
                        Vec3 finalpos = new Vec3(pos2.x - pos1.x, pos2.y - pos1.y, pos2.z - pos1.z);
                        if (finalpos != null) {
                           double entityareamultiplier = MathUtils.getTotalSpeed(finalpos.x, finalpos.y, finalpos.z) * 10;
                            double wradius = entityareamultiplier * ent.getBbWidth();
                            double yradius = entityareamultiplier * ent.getBbHeight();
                            AABB entityarea = ArmatureUtils.getJointCollider(renderer, ent, joint);
                            if (entityarea != null) {
                                if (entityarea.contains(particle.getPos())) {
                                    particle.setParticleSpeed(finalpos.x, finalpos.y, finalpos.z);
                                }
                            }
                         }
                    }
                }
            }, 5, TimeUnit.MILLISECONDS);
        }
    }
    public static void loopMoveParticleOnAttack(LocalPlayer renderer, Entity ent, Joint joint, Particle particle){
        TimerUtil.schedule(()->{
            if (ent != null && renderer != null && particle.isAlive()){
                moveParticlesOnAttack(renderer, ent, joint, particle);
            }
        }, 5, TimeUnit.MILLISECONDS);
    }
}
