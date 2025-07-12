package com.robson.pride.api.data.types;

import com.robson.pride.api.client.ItemRenderingParams;
import com.robson.pride.api.utils.AttributeUtils;
import com.robson.pride.api.utils.ElementalUtils;
import com.robson.pride.api.utils.HealthUtils;
import com.robson.pride.api.utils.math.MathUtils;
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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.List;

public abstract class ElementData extends GenericData {

    private final ParticleOptions particle;

    private final ChatFormatting color;

    private final SoundEvent sound;

    private final byte particleAmount;

    private final ItemRenderingParams itemRenderingParams;

    private final SchoolType school;

    private final List<String> weaknesses;

    private final List<String> resistances;

    private final Attribute powerattribute;

    private final Attribute resistanceattribute;

    public ElementData(ParticleOptions particle, ChatFormatting color, SoundEvent sound, byte particleAmount, SchoolType school, ItemRenderingParams itemRenderingParams, Attribute powerattribute, Attribute resistanceattribute,  List<String> weaknesses, List<String> resistances) {
        super(10000);
        this.particle = particle;
        this.color = color;
        this.sound = sound;
        this.particleAmount = particleAmount;
        this.school = school;
        this.itemRenderingParams = itemRenderingParams;
        this.powerattribute = powerattribute;
        this.resistanceattribute = resistanceattribute;
        this.weaknesses = weaknesses;
        this.resistances = resistances;
    }

    public ParticleOptions getNormalParticleType(){
        return this.particle;
    }

    public ChatFormatting getChatColor(){
        return this.color;
    }

    public SoundEvent getSound(){
        return this.sound;
    }

    public byte getParticleAmount(){
        return this.particleAmount;
    }

    public ItemRenderingParams getItemRenderingParams(){
        return this.itemRenderingParams;
    }

    public DamageSource createDamageSource(Entity ent) {
        assert Minecraft.getInstance().level != null;
        Holder<DamageType> damageTypeHolder = Minecraft.getInstance().level.registryAccess()
                .registryOrThrow(Registries.DAMAGE_TYPE)
                .getHolderOrThrow(this.getSchool().getDamageType());
        return ent != null ? new DamageSource(damageTypeHolder, ent) : new DamageSource(damageTypeHolder);
    }

    public void damageEntity(Entity ent, Entity dmgent, float amount, boolean blockable, boolean spellSource) {
        amount = onHit(ent, dmgent, amount, spellSource);
        if (blockable) {
            HealthUtils.dealBlockableDmg(dmgent, ent, amount);
        }
        else {
            HealthUtils.hurtEntity(ent, amount, this.createDamageSource(dmgent));
        }
    }

    public abstract float onHit(Entity ent, Entity dmgent, float amount, boolean spellSource);

    public final float calculateFinalDamage(Entity dmgent, Entity ent, float amount) {
        if (dmgent != null && ent != null) {
            String element = ElementalUtils.getElement(ent);
            float multiplier = 1;
            if (weaknesses.contains(element)) {
                multiplier = 0.5f;
            }
            else if (resistances.contains(element)) {
                multiplier = 1.5f;
            }
            return MathUtils.getValueWithPercentageIncrease(multiplier *
                            MathUtils.getValueWithPercentageDecrease(amount, AttributeUtils.getAttributeValue(ent, this.resistanceattribute)),
                    AttributeUtils.getAttributeValue(dmgent, this.powerattribute));
        }
        return amount;
    }

    public SchoolType getSchool(){
        return this.school;
    }

    public void playSound(Entity ent, float volume) {
        ClientLevel level = Minecraft.getInstance().level;
        LocalPlayer player = Minecraft.getInstance().player;
        if (level != null) {
            level.playSound(player, ent, this.getSound(), SoundSource.NEUTRAL, volume, 1);
        }
    }
}
