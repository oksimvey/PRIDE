package com.robson.pride.api.data.types.item;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.robson.pride.api.data.types.skill.WeaponSkillData;
import com.robson.pride.api.utils.math.FixedRGB;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.mixins.WeaponTypeReloadListenerMixin;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.nbt.CompoundTag;
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

public class WeaponData extends GenericItemData {

    private final String skill;

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


    public WeaponData(CompoundTag tag){
        super(tag);
        this.category = tag.contains("category") ? tag.getString("category") : "sword" ;
        this.damage = tag.contains("damage") ? tag.getFloat("damage") : 0f ;
        this.speed = tag.contains("speed") ? tag.getFloat("speed") : 0.5f ;
        this.impact = tag.contains("impact") ? tag.getFloat("impact") : 1f ;
        this.max_stikes = tag.contains("max_strikes") ? tag.getInt("max_strikes") : 1;
        this.armor_negation = tag.contains("armor_negation") ? tag.getFloat("armor_negation") : 0f;
        this.weight = tag.contains("weight") ? tag.getInt("weight") : 1;
        this.skill = tag.contains("skill") ? tag.getString("skill") : "";
        this.attributeReqs = new AttributeReqs(
                tag.contains("strength_scale") ? tag.getString("strength_scale").charAt(0) : '\0',
                tag.contains("dexterity_scale") ? tag.getString("dexterity_scale").charAt(0) : '\0',
                tag.contains("mind_scale") ? tag.getString("mind_scale").charAt(0) : '\0',
                tag.contains("required_strength") ? (byte) tag.getInt("required_strength") : 0,
                tag.contains("required_dexterity") ? (byte) tag.getInt("required_dexterity") : 0,
                tag.contains("required_mind") ? (byte) tag.getInt("required_mind") : 0
        );
        if(tag.contains("trail_color")) {
            CompoundTag colorTag = tag.getCompound("trail_color");
            short red = (short) colorTag.getInt("red");
            short green = (short) colorTag.getInt("green");
            short blue = (short) colorTag.getInt("blue");
            this.trailcolor = new FixedRGB(red, green, blue);
        }
        else trailcolor = new FixedRGB((short) 255, (short) 255, (short) 255);
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
        builder.endPos(new Vec3(0, 0.2f, (-getCollider().z1() + getCollider().z0())));
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
        return LongSwordWeaponSkill.DATA;
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


    public record AttributeReqs(char strengthScale, char dexterityScale, char mindScale, byte requiredStrength, byte requiredDexterity, byte requiredMind) {
    }
}
