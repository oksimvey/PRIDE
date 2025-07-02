package com.robson.pride.skills.special;

import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.data.manager.SkillDataManager;
import com.robson.pride.api.data.types.DurationSkillData;
import com.robson.pride.api.data.types.WeaponSkillData;
import com.robson.pride.api.mechanics.perilous.PerilousType;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.List;

public abstract class GenericMikiri extends DurationSkillData {

    private final List<PerilousType> counterablePerilous;

    private final List<AbstractSpell> counterableSpells;

    public GenericMikiri(List<PerilousType> counterablePerilous, List<AbstractSpell> counterableSpells) {
        this.counterablePerilous = counterablePerilous;
        this.counterableSpells = counterableSpells;
    }

    public List<PerilousType> getCounterablePerilous() {
        return counterablePerilous;
    }

    public List<AbstractSpell> getCounterableSpells() {
        return counterableSpells;
    }

    public abstract boolean isCounterableEntity(Entity entity);

    @Override
    public void onAttacked(LivingEntity ent, LivingAttackEvent event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity living && SkillDataManager.ACTIVE_WEAPON_SKILL.get(living) != null) {
            WeaponSkillData data = SkillDataManager.ACTIVE_WEAPON_SKILL.get(living);
            if (data != null && counterablePerilous.contains(data.getPerilousType())) {
                onPerilousCountered(ent, living, data.getPerilousType(), event);
            }
        }
        if (isCounterableEntity(event.getSource().getDirectEntity())){
            onEntityCountered(ent, event.getSource().getDirectEntity(), event);
        }
    }

    @Override
    public void onSpellDamage(LivingEntity ent, SpellDamageEvent event) {
        if (counterableSpells.contains(event.getSpellDamageSource().spell())){
            onSpellCountered(ent, event.getSpellDamageSource().spell(), event);
        }
    }

    public abstract void onPerilousCountered(LivingEntity ent, LivingEntity dmgent, PerilousType type, LivingAttackEvent event);

    public abstract void onEntityCountered(LivingEntity ent, Entity dmgent, LivingAttackEvent event);

    public abstract void onSpellCountered(LivingEntity ent, AbstractSpell spell, SpellDamageEvent event);
}
