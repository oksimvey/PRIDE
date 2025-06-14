package com.robson.pride.item.weapons;


import com.robson.pride.api.data.WeaponData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CustomWeaponModelBase implements BakedModel {
    private final BakedModel original;
    private final ItemOverrides itemOverrides;

    public CustomWeaponModelBase(BakedModel original, ModelBakery loader) {
        this.original = original;
        BlockModel missing = (BlockModel)loader.getModel(ModelBakery.MISSING_MODEL_LOCATION);
        this.itemOverrides = new ItemOverrides(new ModelBaker() {
            public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
                return null;
            }

            public BakedModel bake(ResourceLocation location, ModelState state, Function<Material, TextureAtlasSprite> sprites) {
                return null;
            }

            public UnbakedModel getModel(ResourceLocation resourceLocation) {
                return null;
            }

            public @Nullable BakedModel bake(ResourceLocation resourceLocation, ModelState modelState) {
                return null;
            }
        }, missing, Collections.emptyList()) {

            public BakedModel resolve(@NotNull BakedModel original, @NotNull ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity livingEntity, int seed) {
                if (itemStack.hasTag()) {
                    Optional<ResourceLocation> override = this.getModelFromTag(itemStack, itemStack.getTag());
                    if (override.isPresent()) {
                        ModelManager manager = Minecraft.getInstance().getModelManager();
                        BakedModel missing = manager.getModel(ModelBakery.MISSING_MODEL_LOCATION);
                        BakedModel model = manager.getModel((ResourceLocation)override.get());
                        return model == missing ? original : model;
                    }
                }

                return original;
            }

            private Optional<ResourceLocation> getModelFromTag(@NotNull ItemStack itemStack, CompoundTag tag) {
                WeaponData data = WeaponData.getWeaponData(itemStack);
                if (data != null) {
                    return Optional.of(new ResourceLocation(data.getModel()));
                }
                return Optional.empty();
            }
        };
    }

    public static ResourceLocation getWeaponModelLocation(String resourcelocation) {
        return new ResourceLocation(resourcelocation);
    }


    public @NotNull ItemOverrides getOverrides() {
        return this.itemOverrides;
    }

    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand) {
        return this.original.getQuads(state, side, rand);
    }

    public boolean useAmbientOcclusion() {
        return this.original.useAmbientOcclusion();
    }

    public boolean isGui3d() {
        return this.original.isGui3d();
    }

    public boolean usesBlockLight() {
        return this.original.usesBlockLight();
    }

    public boolean isCustomRenderer() {
        return this.original.isCustomRenderer();
    }

    public @NotNull TextureAtlasSprite getParticleIcon() {
        return this.original.getParticleIcon();
    }

    public ItemTransforms getTransforms() {
        return this.original.getTransforms();
    }
}
