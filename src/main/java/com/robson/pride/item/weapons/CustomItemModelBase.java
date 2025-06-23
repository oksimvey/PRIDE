package com.robson.pride.item.weapons;


import com.robson.pride.api.data.types.GenericData;
import com.robson.pride.api.data.manager.DataManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class CustomItemModelBase implements BakedModel {
    private final BakedModel original;
    private final ItemOverrides itemOverrides;

    public CustomItemModelBase(BakedModel original, ModelBakery loader) {
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
                    GenericData data = DataManager.getGenericData(itemStack);
                    if (data != null) {
                            ModelManager manager = Minecraft.getInstance().getModelManager();
                            BakedModel missing = manager.getModel(ModelBakery.MISSING_MODEL_LOCATION);
                            BakedModel model = manager.getModel(data.getModel());
                            return model == missing ? original : model;
                    }

                return original;
            }
        };
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
