package com.robson.pride.skills.special;

import com.robson.pride.api.mechanics.PerilousType;
import io.redspace.ironsspellbooks.api.events.SpellDamageEvent;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.entity.spells.ExtendedWitherSkull;
import io.redspace.ironsspellbooks.entity.spells.acid_orb.AcidOrb;
import io.redspace.ironsspellbooks.entity.spells.ball_lightning.BallLightning;
import io.redspace.ironsspellbooks.entity.spells.creeper_head.CreeperHeadProjectile;
import io.redspace.ironsspellbooks.entity.spells.fireball.MagicFireball;
import io.redspace.ironsspellbooks.entity.spells.firebolt.FireboltProjectile;
import io.redspace.ironsspellbooks.entity.spells.guiding_bolt.GuidingBoltProjectile;
import io.redspace.ironsspellbooks.entity.spells.icicle.IcicleProjectile;
import io.redspace.ironsspellbooks.entity.spells.lightning_lance.LightningLanceProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_arrow.MagicArrowProjectile;
import io.redspace.ironsspellbooks.entity.spells.magic_missile.MagicMissileProjectile;
import io.redspace.ironsspellbooks.entity.spells.magma_ball.FireBomb;
import io.redspace.ironsspellbooks.entity.spells.poison_arrow.PoisonArrow;
import io.redspace.ironsspellbooks.entity.spells.ray_of_frost.RayOfFrostVisualEntity;
import io.redspace.ironsspellbooks.entity.spells.wisp.WispEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import java.util.List;

public interface DodgeMikiri {

    GenericMikiri DATA = new GenericMikiri(List.of(PerilousType.PIERCE_ONE_HAND, PerilousType.PIERCE_TWO_HAND),
            List.of()) {

        @Override
        public boolean isCounterableEntity(Entity entity) {
            return entity instanceof AbstractArrow ||
            entity instanceof ExtendedWitherSkull ||
                    entity instanceof MagicArrowProjectile ||
                    entity instanceof MagicMissileProjectile ||
                    entity instanceof CreeperHeadProjectile ||
                    entity instanceof MagicFireball ||
                    entity instanceof FireboltProjectile ||
                    entity instanceof FireBomb ||
                    entity instanceof GuidingBoltProjectile ||
                    entity instanceof WispEntity ||
                    entity instanceof IcicleProjectile ||
                    entity instanceof RayOfFrostVisualEntity ||
                    entity instanceof LightningLanceProjectile ||
                    entity instanceof BallLightning ||
                    entity instanceof AcidOrb ||
                    entity instanceof PoisonArrow;
        }

        @Override
        public void onPerilousCountered(LivingEntity ent, LivingEntity dmgent, PerilousType type, LivingAttackEvent event) {

        }

        @Override
        public void onEntityCountered(LivingEntity ent, Entity dmgent, LivingAttackEvent event) {

        }

        @Override
        public void onSpellCountered(LivingEntity ent, AbstractSpell spell, SpellDamageEvent event) {

        }
    };
}
