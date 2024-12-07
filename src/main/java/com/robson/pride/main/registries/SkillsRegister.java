package com.robson.pride.main.registries;


import com.robson.pride.epicfight.skills.innate.KatanaSkill;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import com.robson.pride.epicfight.skills.passive.TwoHanded;

@Mod.EventBusSubscriber(
        modid = "pride",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class SkillsRegister {
    public static Skill KATANASKILL;
    public static Skill TWO_HANDED;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker("pride");
        WeaponInnateSkill deadlybackflip = (WeaponInnateSkill)modRegistry.build("katana_skill", KatanaSkill::new, WeaponInnateSkill.createWeaponInnateBuilder());
        KATANASKILL = deadlybackflip;
        TWO_HANDED = modRegistry.build("two_handed", TwoHanded::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
    }

    public SkillsRegister() {
    }
}
