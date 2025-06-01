package com.robson.pride.api.elements;

import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;

public abstract class ElementBase {

    public abstract ParticleOptions getNormalParticleType();

    public abstract ChatFormatting getChatColor();

    public abstract SoundEvent getSound();

    public abstract byte getParticleAmount();

    public DamageSource createDamageSource(Entity ent){
        assert Minecraft.getInstance().level != null;
        Holder<DamageType> damageTypeHolder = Minecraft.getInstance().level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(this.getSchool().getDamageType());

        return ent != null ? new DamageSource(damageTypeHolder, ent) : new DamageSource(damageTypeHolder);
    }

    public abstract void onHit(Entity ent, Entity dmgent, float amount, boolean spellSource);

    public abstract float calculateFinalDamage(Entity ent, float amount);

    public abstract SchoolType getSchool();

    public void playSound(Entity ent, float volume){
        ClientLevel level = Minecraft.getInstance().level;
        LocalPlayer player = Minecraft.getInstance().player;
        if (level != null) {
           level.playSound(player, ent, this.getSound(), SoundSource.NEUTRAL, volume, 1);
        }
    }
}
