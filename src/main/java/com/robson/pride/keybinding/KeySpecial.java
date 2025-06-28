package com.robson.pride.keybinding;

import com.robson.pride.api.keybinding.LongPressKey;
import com.robson.pride.api.skillcore.SkillCore;
import com.robson.pride.api.utils.ArmatureUtils;
import com.robson.pride.api.utils.TimerUtil;
import com.robson.pride.api.utils.math.PrideVec3f;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.robson.pride.epicfight.styles.SheatProvider.unsheat;

public class KeySpecial extends LongPressKey {

    public KeySpecial() {
        super((byte) 10);
    }

    @Override
    public void onPress(Player player) {
        unsheat(player);
        TimerUtil.schedule(()->{
            List<PrideVec3f> points = ArmatureUtils.getJointInterpolatedBezier(Minecraft.getInstance().player, player, 2);
            Vec3 look = player.getLookAngle().scale(0.75f);
            for (PrideVec3f point : points) {
                Minecraft.getInstance().particleEngine.createParticle(ParticleRegistry.FIRE_PARTICLE.get(),
                        point.x() + player.getX(),
                        point.y() + player.getY(),
                        point.z() + player.getZ(), look.x, look.y, look.z);
            }
        }, 333, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onLongPress(Player player) {
        SkillCore.onSkillExecute(player);
    }
}
