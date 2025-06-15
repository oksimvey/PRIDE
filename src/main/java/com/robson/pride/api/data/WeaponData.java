package com.robson.pride.api.data;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.robson.pride.api.maps.WeaponsMap;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import org.w3c.dom.Attr;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;
import yesman.epicfight.world.capabilities.provider.ItemCapabilityProvider;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.security.DrbgParameters;
import java.util.Map;
import java.util.UUID;

public class WeaponData {

    private final CapabilityItem itemcap;

    private final String model;

    private final AABB collider;

    private final WeaponSkillBase skill;

    private final AttributeReqs attributeReqs;

    private final String element;

    private final int weight;

    public WeaponData(CapabilityItem itemcap, int weight, String model, String element, AABB collider, WeaponSkillBase skill, AttributeReqs attributeReqs){
        this.itemcap = itemcap;
        this.weight = weight;
        this.model = model;
        this.collider = collider;
        this.skill = skill;
        this.attributeReqs = attributeReqs;
        this.element = element;
    }

    public String getElement() {
        return this.element;
    }

    public int getWeight() {
        return this.weight;
    }

    public static WeaponData getWeaponData(ItemStack itemStack){
        if (itemStack != null){
            return WeaponsMap.WEAPONS.get(itemStack.getOrCreateTag().getString("weaponid"));
        }
        return null;
    }

    public AttributeReqs getAttributeReqs() {
        return this.attributeReqs;
    }


    public static CapabilityItem createCapability(WeaponCategory weaponCategory, float damage, float speed, float impact, int max_strikes, float armor_negation, int weight){
        CapabilityItem.Builder innerDefaultCapabilityBuilder = CapabilityItem.builder();
        innerDefaultCapabilityBuilder.category(weaponCategory);
        Map<Attribute, AttributeModifier> attributeEntry = deserializeAttributes(damage, speed, impact, max_strikes, armor_negation, weight);
        for (Map.Entry<Attribute, AttributeModifier> attribute : attributeEntry.entrySet()) {
            innerDefaultCapabilityBuilder.addStyleAttibutes(CapabilityItem.Styles.COMMON, Pair.of(attribute.getKey(), attribute.getValue()));
        }
        return innerDefaultCapabilityBuilder.build();
    }

    public static AABB createCollider(float maxx, float maxy, float maxz, float minx, float miny, float minz){
        return new AABB(maxx, maxy, maxz, minx, miny, minz);
    }

    public AABB getCollider() {
        return this.collider;
    }

    public static WeaponSkillBase createSkill(WeaponSkillBase weaponSkillBase){
        return weaponSkillBase;
    }

    public WeaponSkillBase getSkill(){
        return this.skill;
    }

    public String getModel() {
        return this.model;
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

    public CapabilityItem getItemcap() {
        return this.itemcap;
    }

    public static class AttributeReqs {

        private final char strengthScale;

        private final char mindScale;

        private final char dexterityScale;

        private final byte requiredStrength;

        private final byte requiredMind;

        private final byte requiredDexterity;

        public AttributeReqs(char strengthScale, char mindScale, char dexterityScale, byte requiredStrength, byte requiredMind, byte requiredDexterity){
            this.strengthScale = strengthScale;
            this.mindScale = mindScale;
            this.dexterityScale = dexterityScale;
            this.requiredStrength = requiredStrength;
            this.requiredMind = requiredMind;
            this.requiredDexterity = requiredDexterity;

        }

        public char getStrengthScale() {
            return this.strengthScale;
        }

        public char getMindScale() {
            return this.mindScale;
        }

        public char getDexterityScale() {
            return this.dexterityScale;
        }

        public byte getRequiredStrength() {
            return this.requiredStrength;
        }

        public byte getRequiredMind() {
            return this.requiredMind;
        }

        public byte getRequiredDexterity() {
            return this.requiredDexterity;
        }
    }
}
