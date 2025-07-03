package com.robson.pride.api.ai.actions;

import com.robson.pride.api.ai.combat.ActionBase;
import com.robson.pride.api.entity.PrideMobPatch;
import com.robson.pride.api.utils.ParticleUtils;
import com.robson.pride.api.utils.PlaySoundUtils;
import com.robson.pride.particles.StringParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.player.LocalPlayer;

public class DialogueAction extends ActionBase {

    private final String subtitle;

    private final int duration;

    private final String sound;

    private final float volume;

    public DialogueAction(String subtitle, int duration, String sound, float volume) {
        this.subtitle = subtitle;
        this.duration = duration;
        this.sound = sound;
        this.volume = volume;
    }

    protected void start(PrideMobPatch<?> ent) {
       if (this.subtitle != null){
           ClientLevel level = Minecraft.getInstance().level;
           LocalPlayer player = Minecraft.getInstance().player;
           if (player != null && level != null) {
               float volumeMultiplier = 5;
               if (this.sound != null && !this.sound.isEmpty()){
                   volumeMultiplier *= this.volume;
                   PlaySoundUtils.playNonRegisteredSound(ent.getOriginal(), this.sound, volumeMultiplier, 1);
               }
              if (ent.getOriginal().distanceTo(player) < ent.getOriginal().getBbHeight() * volumeMultiplier) {
                   ParticleUtils.spawnStringParticle(ent.getOriginal(), this.subtitle, StringParticle.StringParticleTypes.WHITE, duration / 50 - 1);
               }
           }
       }
    }
}
