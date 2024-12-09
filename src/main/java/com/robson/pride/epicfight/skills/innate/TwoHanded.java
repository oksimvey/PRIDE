package com.robson.pride.epicfight.skills.innate;

import java.util.List;

import com.google.common.collect.Lists;

import jdk.jfr.EventType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class TwoHanded extends WeaponInnateSkill {
    private long returnDuration;
    private final int maxDuration = 999999999;

    public TwoHanded(Builder<? extends Skill> builder) {
        super(builder);
    }


    @Override
    public void setParams(CompoundTag parameters) {
        super.setParams(parameters);
        this.returnDuration = parameters.getInt("return_duration");
    }

    @Override
    public void onInitiate(SkillContainer container) {
            if (container.isActivated() && !container.isDisabled()) {
                    this.setDurationSynchronize((ServerPlayerPatch) container.getExecuter(), (int) Math.min(this.maxDuration, container.getRemainDuration() + this.returnDuration));
            }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.playSound(SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F);

        if (executer.getSkill(this).isActivated()) {
            this.cancelOnServer(executer, args);
        } else {
            super.executeOnServer(executer, args);
            executer.getSkill(this).activate();
            executer.modifyLivingMotionByCurrentItem(false);
        }
    }

    @Override
    public void cancelOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        executer.getSkill(this).deactivate();
        super.cancelOnServer(executer, args);
        executer.modifyLivingMotionByCurrentItem(false);
    }

    @Override
    public void executeOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.executeOnClient(executer, args);
        executer.getSkill(this).activate();
    }

    @Override
    public void cancelOnClient(LocalPlayerPatch executer, FriendlyByteBuf args) {
        super.cancelOnClient(executer, args);
        executer.getSkill(this).deactivate();
    }

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return true;
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public List<Component> getTooltipOnItem(ItemStack itemstack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        List<Object> tooltipArgs = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();

        tooltipArgs.add(this.maxDuration / 20);
        tooltipArgs.add(this.returnDuration / 20);

        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip", tooltipArgs.toArray(new Object[0])).withStyle(ChatFormatting.DARK_GRAY));

        return list;
    }
}