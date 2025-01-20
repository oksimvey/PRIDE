package com.robson.pride.api.ai.conditions;

import io.netty.util.internal.StringUtil;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.api.utils.ParseUtil;
import yesman.epicfight.client.gui.datapack.widgets.ComboBox;
import yesman.epicfight.client.gui.datapack.widgets.ResizableComponent;
import yesman.epicfight.client.gui.datapack.widgets.ResizableEditBox;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.data.conditions.entity.HealthPoint;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

public class AngleDifference extends Condition.EntityPatchCondition {
    private double min;
    private double max;
    private boolean invert;

    public AngleDifference() {
    }

    public AngleDifference(double min, double max, boolean invert) {
        this.min = min;
        this.max = max;
        this.invert = invert;
    }

    @Override
    public AngleDifference read(CompoundTag tag) {
        if (!tag.contains("min")) {
            throw new IllegalArgumentException("AngleDifference condition error: min distance not specified!");
        }

        if (!tag.contains("max")) {
            throw new IllegalArgumentException("AngleDifference condition error: max distance not specified!");
        }
        if (!tag.contains("invert")) {
            throw new IllegalArgumentException("AngleDifference condition error: invert not specified!");
        }

        this.min = tag.getDouble("min");
        this.max = tag.getDouble("max");
        this.invert = tag.getBoolean("invert");

        return this;
    }

    @Override
    public CompoundTag serializePredicate() {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("min", this.min);
        tag.putDouble("max", this.max);
        tag.putBoolean("invert", this.invert);

        return tag;
    }

    @Override
    public boolean predicate(LivingEntityPatch<?> target) {
        double angleDifference = target.getOriginal().getYRot() - target.getTarget().getYRot();
        if (!invert) {
            return !(this.min > angleDifference && angleDifference < this.max);
        }
        return this.min > angleDifference && angleDifference < this.max;
    }

    @OnlyIn(Dist.CLIENT)
    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        ResizableEditBox editbox = new ResizableEditBox(screen.getMinecraft().font, 0, 0, 0, 0, Component.literal("Don't use this"), (ResizableComponent.HorizontalSizing) null, (ResizableComponent.VerticalSizing) null);
        AbstractWidget comboBox = new ComboBox(screen, screen.getMinecraft().font, 0, 0, 0, 0, (ResizableComponent.HorizontalSizing) null, (ResizableComponent.VerticalSizing) null, 4, Component.literal("in here"), List.of(HealthPoint.Comparator.values()), ParseUtil::snakeToSpacedCamel, (Consumer) null);
        editbox.setFilter((context) -> {
            return StringUtil.isNullOrEmpty(context) || ParseUtil.isParsable(context, Float::parseFloat);
        });
        return List.of(ParameterEditor.of((value) -> {
            return FloatTag.valueOf(Float.parseFloat(value.toString()));
        }, (tag) -> {
            return ParseUtil.valueOfOmittingType(ParseUtil.nullOrToString(tag, Tag::getAsString));
        }, editbox), ParameterEditor.of((value) -> {
            return StringTag.valueOf(value.toString().toLowerCase(Locale.ROOT));
        }, (tag) -> {
            return ParseUtil.enumValueOfOrNull(HealthPoint.Comparator.class, ParseUtil.nullOrToString(tag, Tag::getAsString));
        }, comboBox));
    }
}
