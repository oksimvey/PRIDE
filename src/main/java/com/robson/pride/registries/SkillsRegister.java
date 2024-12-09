package com.robson.pride.registries;


import com.robson.pride.epicfight.skills.innate.AdvancedParrying;
import com.robson.pride.main.Pride;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import com.robson.pride.epicfight.skills.innate.TwoHanded;

@Mod.EventBusSubscriber(
        modid = "pride",
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class SkillsRegister {
    public static Skill TWO_HANDED;
    public static Skill ADVANCED_PARRYING;

    @SubscribeEvent
    public static void buildSkillEvent(SkillBuildEvent build) {
        SkillBuildEvent.ModRegistryWorker modRegistry = build.createRegistryWorker(Pride.MODID);
        TWO_HANDED = modRegistry.build("two_handed", TwoHanded::new, WeaponInnateSkill.createWeaponInnateBuilder().setActivateType(Skill.ActivateType.DURATION));
        ADVANCED_PARRYING = modRegistry.build("advanced_parrying", AdvancedParrying::new, AdvancedParrying.createActiveGuardBuilder());
    }

    public SkillsRegister() {
    }
}
