package com.robson.pride.api.skillcore;

public enum WeaponArtRegister implements SkillCore.WeaponSkill {

    Darkness_Cut(new DarknessCut()),
    Heaven_Strike(new HeavensStrike()),
    Flame_Slash(new FlameSlashSkill()),
    Tornado(new Tornado()),
    Ground_Stomp(new GroundStomp()),
    Kick(new Kick());

    final WeaponSkillBase skillBase;
    final int id;

    WeaponArtRegister(WeaponSkillBase skillBase) {
        this.skillBase = skillBase;
        this.id = SkillCore.WeaponSkill.ENUM_MANAGER.assign(this);
    }

    @Override
    public WeaponSkillBase skill() {
        return this.skillBase;
    }

    @Override
    public int universalOrdinal() {
        return this.id;
    }
}
