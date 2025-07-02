package com.robson.pride.api.data.types;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.robson.pride.api.data.manager.ServerDataManager;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.mixins.WeaponTypeReloadListenerMixin;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.world.capabilities.item.*;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class WeaponData extends GenericData {

    private final short skill;

    private final AttributeReqs attributeReqs;

    private final int weight;

    private final String category;

    private final float damage;

    private final float speed;

    private final float impact;

    private final int max_stikes;

    private final float armor_negation;

    private final FixedRGB trailcolor;

    private CapabilityItem itemcap;

    private TrailInfo trailInfo;


    public WeaponData(Component name, String category, float damage, float speed, float impact, float max_strikes, float armor_negation, int weight, String model, byte element, Matrix2f collider, short skill, AttributeReqs attributeReqs, FixedRGB trailcolor){
        super(name, model, collider, element, (byte) 1);
        this.category = category;
        this.damage = damage;
        this.speed = speed;
        this.impact = impact;
        this.max_stikes = (int) max_strikes;
        this.armor_negation = armor_negation;
        this.weight = weight;
        this.skill = skill;
        this.attributeReqs = attributeReqs;
        this.trailcolor = trailcolor;
        itemcap = null;
        trailInfo = null;
    }

    public int getWeight() {
        return this.weight;
    }

    public TrailInfo getTrailInfo(TrailInfo info) {
      if (this.trailInfo == null){
          this.trailInfo = create(info);
      }
      return this.trailInfo;
    }

    public TrailInfo create(TrailInfo info){
        TrailInfo.Builder builder = TrailInfo.builder();
        builder.joint(info.joint());
        builder.texture(info.texturePath());
        builder.time(info.startTime(), info.endTime());
        builder.startPos(new Vec3(0, 0, getCollider().z0()));
        builder.endPos(new Vec3(0, 0.2f, (-getCollider().z1() * (getCollider().z1() - 1.75))- getCollider().z0()));
        builder.r(this.trailcolor.r());
        builder.g(this.trailcolor.g());
        builder.b(this.trailcolor.b());
        builder.lifetime(info.trailLifetime());
        builder.type(info.particle());
        builder.interpolations(info.trailLifetime() / 3);
        return builder.create();
    }

    public AttributeReqs getAttributeReqs() {
        return this.attributeReqs;
    }

    public CapabilityItem getItemcap(ItemStack stack){
        if (itemcap == null || itemcap == CapabilityItem.EMPTY){
            itemcap = createItemCap(stack);
        }
        return itemcap;
    }

    private CapabilityItem createItemCap(ItemStack stack){
        ResourceLocation rl = new ResourceLocation("pride:pride_" + this.category);
        if (WeaponTypeReloadListenerMixin.getPRESETS().containsKey(rl)) {
            CapabilityItem.Builder builder = ((CapabilityItem.Builder)((Function)WeaponTypeReloadListenerMixin.getPRESETS().getOrDefault(rl, WeaponCapabilityPresets.SWORD)).apply(stack.getItem()));
            Map<Attribute, AttributeModifier> attributeEntry = deserializeAttributes(this.damage, this.speed, this.impact, this.max_stikes, this.armor_negation, this.weight);
            for (Map.Entry<Attribute, AttributeModifier> attribute : attributeEntry.entrySet()) {
                builder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(attribute.getKey(), attribute.getValue()));
            }
            return builder.build();
        }
        return CapabilityItem.EMPTY;
    }


    public WeaponSkillData getSkill(){
        return ServerDataManager.getWeaponSkillData(this.skill);
    }

    private static Map<Attribute, AttributeModifier> deserializeAttributes(float damage, float speed, float impact, int max_strikes, float armor_negation, int weight) {
        Map<Attribute, AttributeModifier> modifierMap = Maps.newHashMap();
        modifierMap.put(EpicFightAttributes.ARMOR_NEGATION.get(), EpicFightAttributes.getArmorNegationModifier(armor_negation));
        modifierMap.put(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(impact));
        modifierMap.put(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(max_strikes));
        modifierMap.put(Attributes.ATTACK_DAMAGE, EpicFightAttributes.getDamageBonusModifier(damage));
        modifierMap.put(Attributes.ATTACK_SPEED, EpicFightAttributes.getSpeedBonusModifier(speed));
        modifierMap.put(EpicFightAttributes.WEIGHT.get(), new AttributeModifier(UUID.fromString("5975a582-14e0-4d16-b6ef-8cbe2c9593c0"), "epicfight:weapon_modifier", weight, AttributeModifier.Operation.ADDITION));
        return modifierMap;
    }


    public record AttributeReqs(char strengthScale, char mindScale, char dexterityScale, byte requiredStrength,
                                byte requiredMind, byte requiredDexterity) {
    }

}
