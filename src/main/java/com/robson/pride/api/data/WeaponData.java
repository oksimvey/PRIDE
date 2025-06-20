package com.robson.pride.api.data;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.robson.pride.api.data.manager.WeaponDataManager;
import com.robson.pride.api.data.manager.WeaponSkillsDataManager;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.api.utils.math.Matrix2f;
import com.robson.pride.item.weapons.CustomWeaponItem;
import com.robson.pride.mixins.WeaponTypeReloadListenerMixin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.world.capabilities.item.*;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class WeaponData {

    private final String name;

    private final String model;

    private final AABB collider;

    private final short skill;

    private final AttributeReqs attributeReqs;

    private final byte element;

    private final int weight;

    private final String category;

    private final float damage;

    private final float speed;

    private final float impact;

    private final int max_stikes;

    private final float armor_negation;

    private final TrailParams trailInfo;

    private CapabilityItem itemcap;



    public WeaponData(String name, String category, float damage, float speed, float impact, float max_strikes, float armor_negation, int weight, String model,byte element, AABB collider, short skill, AttributeReqs attributeReqs, TrailParams trail){
        this.name = name;
        this.category = category;
        this.damage = damage;
        this.speed = speed;
        this.impact = impact;
        this.max_stikes = (int) max_strikes;
        this.armor_negation = armor_negation;
        this.weight = weight;
        this.model = model;
        this.collider = collider;
        this.skill = skill;
        this.attributeReqs = attributeReqs;
        this.element = element;
        this.trailInfo = trail;
        itemcap = null;
    }


    public static WeaponData getWeaponData(ItemStack itemStack){
        if (itemStack != null && itemStack.getItem() instanceof CustomWeaponItem){
            return WeaponDataManager.getByID(itemStack.getOrCreateTag().getInt("weaponid"));
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public byte getElement() {
        return this.element;
    }

    public int getWeight() {
        return this.weight;
    }


    public TrailInfo getTrailInfo(TrailInfo info) {
        TrailInfo.Builder builder = TrailInfo.builder();
        builder.joint(info.joint());
        builder.texture(info.texturePath());
        builder.time(info.startTime(), info.endTime());
        builder.startPos(new Vec3(trailInfo.positions().x0(), trailInfo.positions().y0(), trailInfo.positions().z0()));
        builder.endPos(new Vec3(trailInfo.positions().x1(), trailInfo.positions().y1(), trailInfo.positions().z1()));
        builder.r(trailInfo.r);
        builder.g(trailInfo.g);
        builder.b(trailInfo.b);
        builder.lifetime(trailInfo.lifetime());
        builder.type(info.particle());
        builder.interpolations(trailInfo.lifetime() / 4);
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
        return WeaponSkillsDataManager.getByID(this.skill);
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


    public record AttributeReqs(char strengthScale, char mindScale, char dexterityScale, byte requiredStrength,
                                byte requiredMind, byte requiredDexterity) {
    }


    public record TrailParams(Matrix2f positions, int r, int g, int b, int lifetime) {
    }
}
