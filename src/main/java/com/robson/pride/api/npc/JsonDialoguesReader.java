package com.robson.pride.api.npc;

import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.*;
import com.robson.pride.particles.StringParticle;
import com.robson.pride.registries.DialogueConditionsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JsonDialoguesReader {

    public static ConcurrentHashMap<Entity, Boolean> isSpeaking = new ConcurrentHashMap<>();

    public static void onInteraction(Entity target, Entity sourceent) {
        if (target != null && sourceent != null) {
            ListTag tag = PrideMobPatchReloader.DIALOGUES.get(target.getType());
            if (tag != null) {
                deserializeConditions(target, sourceent, tag);
            }
        }
    }

    public static void deserializeConditions(Entity ent, Entity sourceent, ListTag behaviors){
        if (ent != null && sourceent != null && behaviors != null){
            for (int i = 0; i < behaviors.size(); ++i) {
                CompoundTag behavior = behaviors.getCompound(i);
                if (behavior.contains("conditions")) {
                    ListTag conditions = behavior.getList("conditions", 10);
                    byte trueconditions = 0;
                    for (int j = 0; j < conditions.size(); ++j) {
                        CompoundTag condition = conditions.getCompound(j);
                        if (condition.contains("predicate")) {
                            DialogueConditionBase conditionBase = DialogueConditionsRegister.dialogueConditions.get(condition.getString("predicate"));
                            if (conditionBase != null) {
                                if (conditionBase.isTrue(sourceent, ent, condition)) {
                                    trueconditions++;
                                }
                            }
                        }
                    }
                    if (trueconditions == conditions.size()) {
                        if (behavior.contains("dialogues")) {
                            deserializeDialogues(ent, sourceent, behavior.getList("dialogues", 10), (byte) 0);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void deserializeDialogues(Entity ent, Entity sourceent, ListTag tag, byte i) {
        if (ent != null && tag != null) {
            if (i < tag.size()) {
                CompoundTag dialogue = tag.getCompound(i);
                {
                    ClientLevel level  = Minecraft.getInstance().level;
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (dialogue.contains("subtitle") && dialogue.contains("duration") && player != null && level != null) {
                        isSpeaking.put(ent, true);
                        int duration = dialogue.getInt("duration");
                        int volumemultiplier = 5;
                        if (TargetUtil.getTarget(ent) == null) {
                            if (sourceent != null){
                                AnimUtils.rotateToEntity(ent, sourceent);
                            }
                        }
                        if (dialogue.contains("sound")) {
                            float volume = 1;
                            if (dialogue.contains("volume")) {
                                volume = (float) dialogue.getDouble("volume");
                                volumemultiplier = (int) (volumemultiplier * dialogue.getDouble("volume"));
                            }
                            Holder<net.minecraft.sounds.SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(dialogue.getString("sound"))));
                            level.playSound(player, ent, holder.get(), SoundSource.NEUTRAL, volume, 1);
                        }
                        if (ent.distanceTo(player) < ent.getBbHeight() * volumemultiplier) {
                            ParticleUtils.spawnNumberParticle(ent, dialogue.getString("subtitle"), StringParticle.StringParticleTypes.WHITE, duration / 50 - 1);
                        }
                        TimerUtil.schedule(() -> {
                            if (ent != null) {
                                isSpeaking.remove(ent);
                            }
                        }, duration - 1, TimeUnit.MILLISECONDS);
                        TimerUtil.schedule(() -> {
                            deserializeDialogues(ent,sourceent, tag, (byte) (i + 1));
                            if (dialogue.contains("answers")){
                                if (sourceent != null) {
                                    ListTag answers = dialogue.getList("answers", 10);
                                    deserializeConditions(sourceent, ent, answers);
                                }
                            }
                        }, duration, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }
}

