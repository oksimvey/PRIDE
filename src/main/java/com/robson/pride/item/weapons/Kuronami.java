package com.robson.pride.item.weapons;

import com.robson.pride.api.data.WeaponData;
import com.robson.pride.api.skillcore.WeaponSkillBase;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import com.robson.pride.skills.weaponskills.LongSwordWeaponSkill;
import net.minecraft.world.phys.AABB;
import org.w3c.dom.Attr;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

public class Kuronami extends WeaponData {


    public Kuronami() {
        super(WeaponData.createCapability(WeaponCategoriesEnum.PRIDE_LONGSWORD, 1.0f, 1.0f, 1.0f, 10, 0.0f, 10),
                10,"minecraft:item/creationsplitter", null,
                WeaponData.createCollider(0.2f, 0.8f, 1.5f, -0.5f, -0.5f, -0.15f),
                WeaponData.createSkill(new LongSwordWeaponSkill()),
                new AttributeReqs('A', 'C', 'B', (byte) 10, (byte) 5, (byte) 10));
    }
}
