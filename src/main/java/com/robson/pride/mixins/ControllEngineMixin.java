package com.robson.pride.mixins;

import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.ServerTask;
import com.robson.pride.keybinding.KeyActionPacket;
import com.robson.pride.registries.PacketRegister;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import yesman.epicfight.client.events.engine.ControllEngine;
import yesman.epicfight.client.gui.screen.SkillEditScreen;
import yesman.epicfight.client.gui.screen.config.IngameConfigurationScreen;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.entity.eventlistener.SkillExecuteEvent;

import static yesman.epicfight.client.events.engine.ControllEngine.disableKey;

@Mixin(ControllEngine.class)
@OnlyIn(Dist.CLIENT)
public abstract class ControllEngineMixin {

    @Shadow
    private static boolean keyPressed(KeyMapping key, boolean eventCheck) {
        return false;
    }

    @Shadow private LocalPlayerPatch playerpatch;

    @Shadow private LocalPlayer player;

    @Shadow @Final private Minecraft minecraft;

    @Shadow private KeyMapping currentChargingKey;

    @Shadow public Options options;

    @Shadow private boolean attackLightPressToggle;

    @Shadow protected abstract void releaseAllServedKeys();

    @Shadow protected abstract void reserveKey(SkillSlot slot, KeyMapping keyMapping);

    @Shadow public abstract void lockHotkeys();

    @Shadow private boolean weaponInnatePressToggle;

    @Shadow private int weaponInnatePressCounter;

    @Shadow private boolean sneakPressToggle;

    @Shadow private boolean moverPressToggle;

    @Shadow protected abstract void tick();

    /**
     * @author
     * @reason
     */
    @Overwrite(remap = false)
    public void handleEpicFightKeyMappings() {
        if (keyPressed(EpicFightKeyMappings.SKILL_EDIT, false) && this.playerpatch.getSkillCapability() != null) {
            Minecraft.getInstance().setScreen(new SkillEditScreen(this.player, this.playerpatch.getSkillCapability()));
        }

        if (keyPressed(EpicFightKeyMappings.CONFIG, false)) {
            Minecraft.getInstance().setScreen(new IngameConfigurationScreen(this.minecraft, (Screen)null));
        }

        while(keyPressed(EpicFightKeyMappings.ATTACK, true)) {
            if (this.playerpatch.isBattleMode() && this.currentChargingKey != EpicFightKeyMappings.ATTACK) {
                if (!EpicFightKeyMappings.ATTACK.getKey().equals(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey())) {
                    SkillSlot slot = !this.player.onGround() && !this.player.isInWater() && this.player.getDeltaMovement().y > 0.05 ? SkillSlots.AIR_ATTACK : SkillSlots.BASIC_ATTACK;
                    if (this.playerpatch.getSkill(slot).sendExecuteRequest(this.playerpatch, (ControllEngine) (Object)this).isExecutable()) {
                        this.player.resetAttackStrengthTicker();
                        this.attackLightPressToggle = false;
                        this.releaseAllServedKeys();
                    } else if (!this.player.isSpectator() && slot == SkillSlots.BASIC_ATTACK) {
                        this.reserveKey(slot, EpicFightKeyMappings.ATTACK);
                    }

                    this.lockHotkeys();
                    this.attackLightPressToggle = false;
                    this.weaponInnatePressToggle = false;
                    this.weaponInnatePressCounter = 0;
                } else if (!this.weaponInnatePressToggle) {
                    this.weaponInnatePressToggle = true;
                }

                if (this.options.keyAttack.getKey() == EpicFightKeyMappings.ATTACK.getKey()) {
                    disableKey(this.options.keyAttack);
                }
            }
        }

        while(keyPressed(EpicFightKeyMappings.DODGE, true)) {
            if (this.playerpatch.isBattleMode() && this.currentChargingKey != EpicFightKeyMappings.DODGE) {
                if (EpicFightKeyMappings.DODGE.getKey().getValue() == this.options.keyShift.getKey().getValue()) {
                    if (this.player.getVehicle() == null && !this.sneakPressToggle) {
                        this.sneakPressToggle = true;
                    }
                }
                else {
                    SkillSlot skillCategory = this.playerpatch.getEntityState().knockDown() ? SkillSlots.KNOCKDOWN_WAKEUP : SkillSlots.DODGE;
                    SkillContainer skill = this.playerpatch.getSkill(skillCategory);
                    skill.setSkill(EpicFightSkills.STEP, true);
                    if (skill.sendExecuteRequest(this.playerpatch, (ControllEngine) (Object) this).shouldReserverKey()) {
                        this.reserveKey(SkillSlots.DODGE, EpicFightKeyMappings.DODGE);
                    }
                }
            }
        }
        while(keyPressed(EpicFightKeyMappings.WEAPON_INNATE_SKILL, true)) {
            if (this.currentChargingKey != EpicFightKeyMappings.WEAPON_INNATE_SKILL && !EpicFightKeyMappings.ATTACK.getKey().equals(EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey())) {
                ServerTask.sendTask(()-> SkillCore.onSkillExecute(this.player));
            }
        }

        while(keyPressed(EpicFightKeyMappings.MOVER_SKILL, true)) {
            if (this.playerpatch.isBattleMode() && !this.playerpatch.isChargingSkill()) {
                if (EpicFightKeyMappings.MOVER_SKILL.getKey().getValue() == this.options.keyJump.getKey().getValue()) {
                    SkillContainer skillContainer = this.playerpatch.getSkill(SkillSlots.MOVER);
                    SkillExecuteEvent event = new SkillExecuteEvent(this.playerpatch, skillContainer);
                    if (skillContainer.canExecute(this.playerpatch, event) && this.player.getVehicle() == null && !this.moverPressToggle) {
                        this.moverPressToggle = true;
                    }
                } else {
                    SkillContainer skill = this.playerpatch.getSkill(SkillSlots.MOVER);
                    skill.sendExecuteRequest(this.playerpatch, (ControllEngine) (Object)this);
                }
            }
        }
        if (this.playerpatch.getEntityState().inaction() || !this.playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).canBePlacedOffhand()) {
            disableKey(this.minecraft.options.keySwapOffhand);
        }
        this.tick();
    }
}
