package com.robson.pride.api.npc;

import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.*;
import com.robson.pride.particles.StringParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
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
                for (int i = 0; i < tag.size(); ++i) {
                    CompoundTag behaviors = tag.getCompound(i);
                    if (behaviors.contains("conditions")) {
                        ListTag conditions = behaviors.getList("conditions", 10);
                        byte trueconditions = 0;
                        for (int j = 0; j < conditions.size(); ++j) {
                            CompoundTag condition = conditions.getCompound(j);
                            if (DialogueConditions.deserealizeConditions(target, sourceent, condition)) {
                                trueconditions++;
                            }
                        }
                        if (trueconditions == conditions.size()) {
                            if (behaviors.contains("dialogues")) {
                                deserializeDialogues(target, behaviors.getList("dialogues", 10), (byte) 0);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void deserializeDialogues(Entity ent, ListTag tag, byte i) {
        if (ent != null && tag != null) {
            if (i < tag.size()) {
                CompoundTag dialogue = tag.getCompound(i);
                {
                    if (dialogue.contains("subtitle") && dialogue.contains("duration")) {
                        isSpeaking.put(ent, true);
                        int duration = dialogue.getInt("duration");
                        int volumemultiplier = 5;
                        if (TargetUtil.getTarget(ent) == null) {

                        }
                        if (dialogue.contains("sound")) {
                            float volume = 1;
                            if (dialogue.contains("volume")) {
                                volume = (float) dialogue.getDouble("volume");
                                volumemultiplier = (int) (volumemultiplier * dialogue.getDouble("volume"));
                            }
                            Holder<net.minecraft.sounds.SoundEvent> holder = Holder.direct(SoundEvent.createVariableRangeEvent(new ResourceLocation(dialogue.getString("sound"))));
                            Minecraft.getInstance().level.playSound(Minecraft.getInstance().player, ent, holder.get(), SoundSource.NEUTRAL, volume, 1);
                        }
                        if (ent.distanceTo(Minecraft.getInstance().player) < ent.getBbHeight() * volumemultiplier) {
                            Particle text = ParticleUtils.spawnNumberParticle(ent, dialogue.getString("subtitle"), StringParticle.StringParticleTypes.WHITE);
                            TimerUtil.schedule(() -> {
                                if (text != null) {
                                    text.remove();
                                }
                            }, duration - 1, TimeUnit.MILLISECONDS);
                        }
                        TimerUtil.schedule(() -> {
                            if (ent != null) {
                                isSpeaking.remove(ent);
                            }
                        }, duration - 1, TimeUnit.MILLISECONDS);
                        TimerUtil.schedule(() -> deserializeDialogues(ent, tag, (byte) (i + 1)), duration, TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }
}

