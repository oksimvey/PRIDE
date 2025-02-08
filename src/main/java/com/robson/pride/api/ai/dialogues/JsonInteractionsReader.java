package com.robson.pride.api.ai.dialogues;

import com.robson.pride.api.ai.goals.JsonGoalsReader;
import com.robson.pride.api.data.PrideMobPatchReloader;
import com.robson.pride.api.utils.*;
import com.robson.pride.particles.StringParticle;
import com.robson.pride.registries.DialogueConditionsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JsonInteractionsReader {

    public static ConcurrentHashMap<Entity, Boolean> isSpeaking = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Player, AnswerGUI> isAnswering = new ConcurrentHashMap<>();

    public static void onInteraction(Entity target, Entity sourceent) {
        if (target != null && sourceent != null) {
            if (TargetUtil.getTarget(target) == null) {
                CompoundTag tagmap = PrideMobPatchReloader.MOB_TAGS.get(target.getType());
                if (tagmap != null) {
                    ListTag tag = tagmap.getList("interaction_behaviors", 10);
                    if (tag != null) {
                        deserializeInteractions(target, sourceent, tag);
                    }
                }
            }
        }
    }

    public static void deserializeInteractions(Entity ent, Entity sourceent, ListTag behaviors) {
        if (ent != null && sourceent != null && behaviors != null) {
            for (int i = 0; i < behaviors.size(); ++i) {
                CompoundTag behavior = behaviors.getCompound(i);
                if (behavior.contains("conditions")) {
                    ListTag conditions = behavior.getList("conditions", 10);
                    if (deserializeConditions(sourceent, ent, conditions)) {
                        if (behavior.contains("dialogues")) {
                            deserializeDialogues(ent, sourceent, behavior.getList("dialogues", 10), (byte) 0);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static boolean deserializeConditions(Entity sourceent, Entity ent, ListTag conditions){
        if (ent != null && conditions != null) {
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
            return trueconditions == conditions.size();
        }
        return false;
    }

    public static void deserializeDialogues(Entity ent, Entity sourceent, ListTag tag, byte i) {
        if (ent != null && tag != null) {
            if (i < tag.size()) {
                CompoundTag dialogue = tag.getCompound(i);
                ClientLevel level = Minecraft.getInstance().level;
                LocalPlayer player = Minecraft.getInstance().player;
                Particle stringparticle = null;
                if (dialogue.contains("actions")) {
                    JsonGoalsReader.deserializeActions(ent, dialogue.getList("actions", 10), (byte) 0);
                }
                if (dialogue.contains("subtitle") && dialogue.contains("duration") && player != null && level != null) {
                    isSpeaking.put(ent, true);
                    int duration = dialogue.getInt("duration");
                    int volumemultiplier = 5;
                    if (TargetUtil.getTarget(ent) == null) {
                        if (sourceent != null) {
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
                        stringparticle = ParticleUtils.spawnStringParticle(ent, dialogue.getString("subtitle"), StringParticle.StringParticleTypes.WHITE, duration / 50 - 1);
                    }
                    TimerUtil.schedule(() -> {
                        if (ent != null) {
                            isSpeaking.remove(ent);
                        }
                    }, duration - 1, TimeUnit.MILLISECONDS);
                    TimerUtil.schedule(() -> {
                        deserializeDialogues(ent, sourceent, tag, (byte) (i + 1));
                        if (dialogue.contains("answers")) {
                            if (sourceent != null) {
                                ListTag answers = dialogue.getList("answers", 10);
                                deserializeInteractions(sourceent, ent, answers);
                            }
                        }
                    }, duration, TimeUnit.MILLISECONDS);
                }
                if (dialogue.contains("is_question")){
                    if (sourceent instanceof Player player1){
                        deserializeQuestions(ent, player1, dialogue, stringparticle);
                    }
                }
            }
        }
    }

    public static void deserializeQuestions(Entity ent, Player player, CompoundTag dialogue, Particle stringparticle){
        if (ent != null && player != null && dialogue != null){
            if (dialogue.contains("true_answer") && dialogue.contains("false_answer") && dialogue.contains("duration")){
                CompoundTag trueanswer = dialogue.getCompound("true_answer");
                CompoundTag falseanswer = dialogue.getCompound("false_answer");
                int duration = dialogue.getInt("duration");
                if (trueanswer.contains("display") && falseanswer.contains("display") && trueanswer.contains("dialogues") && falseanswer.contains("dialogues")){
                    player.getPersistentData().putString("pride_true_answer", trueanswer.getString("display"));
                    player.getPersistentData().putString("pride_false_answer", falseanswer.getString("display"));
                    player.getPersistentData().put("pride_true_answer_dialogues", trueanswer.getList("dialogues", 10));
                    player.getPersistentData().put("pride_false_answer_dialogues", falseanswer.getList("dialogues", 10));
                    Minecraft client = Minecraft.getInstance();
                    TimerUtil.schedule(()->isAnswering.put(player, new AnswerGUI(player, ent, stringparticle, player.getPersistentData(), client)), duration / 5, TimeUnit.MILLISECONDS);
                    TimerUtil.schedule(()->{
                        if (player != null && client.level != null && isAnswering.get(player) != null){
                                isAnswering.get(player).onClose();
                                isAnswering.get(player).onAnswer();
                        }
                    }, duration, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
}

