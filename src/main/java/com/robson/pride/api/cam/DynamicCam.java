package com.robson.pride.api.cam;

import com.github.leawind.thirdperson.config.AbstractConfig;
import com.robson.pride.api.data.player.ClientDataManager;
import com.robson.pride.api.utils.CameraUtils;
import com.robson.pride.api.utils.ItemStackUtils;
import com.robson.pride.api.utils.math.MathUtils;
import com.robson.pride.api.utils.TargetUtil;
import com.robson.pride.epicfight.styles.PrideStyles;
import com.robson.pride.epicfight.weapontypes.WeaponCategoriesEnum;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.ArrayList;
import java.util.List;

public class DynamicCam {

    public static void dynamicCamTick(Player player) {
        if (player != null) {
            boolean ismounted = player.getVehicle() != null;
            float zmodifier = ismounted ? 1.5f : getDefaultZModifier(player);
            if (!player.level().canSeeSky(player.blockPosition())){
               
            }
            float xmodifier = -0.12f + (zmodifier / 50);
            float ymodifier = -0.02f;
            List<LivingEntity> targets = new ArrayList<>();
            for (Entity ent : player.level().getEntities(player, MathUtils.createAABBAroundEnt(player, 25))) {
                if (ent != null && TargetUtil.getTarget(ent) == player) {
                    targets.add((LivingEntity) ent);
                }
            }
           correctCameraRot(ismounted, targets);
            if (!targets.isEmpty()) {
                if (!ismounted) {
                    xmodifier = 0;
                }
                float targetingSizeModifier = 0;
                byte targetingEntities = (byte) targets.size();
                for (Entity ent : targets) {
                    if (ent != null && ent.getBbHeight() / 5 > targetingSizeModifier) {
                        targetingSizeModifier = ent.getBbHeight() / 5;
                    }
                }
                if (targetingEntities > 5) {
                    targetingEntities = 5;
                }
                ymodifier = 0 + (targetingSizeModifier / 20) + (targetingEntities / 100f);
                zmodifier += targetingSizeModifier + targetingEntities / 5f;
            }
            CameraUtils.changeCamOffset(xmodifier, ymodifier, zmodifier);
        }
    }

    public static void correctCameraRot(boolean ismounted, List<LivingEntity> targets) {
        if (ismounted || targets == null || targets.isEmpty()) {
           CameraUtils.changeRotateMode(AbstractConfig.PlayerRotateMode.INTEREST_POINT);
           return;
        }
        CameraUtils.changeRotateMode(AbstractConfig.PlayerRotateMode.PARALLEL_WITH_CAMERA);
    }

    public static float getDefaultZModifier(Player player) {
        return player.isUsingItem() &&
                (ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) == WeaponCategoriesEnum.PRIDE_GUN ||
                        ItemStackUtils.getStyle(player) == PrideStyles.GUN_OFFHAND ||
                        ItemStackUtils.getWeaponCategory(player, InteractionHand.MAIN_HAND) == CapabilityItem.WeaponCategories.RANGED) ?
                1 : (float) ((1 + ItemStackUtils.getColliderSize(player.getMainHandItem()) / 2) + ((player.getDeltaMovement().x + player.getDeltaMovement().z) * 2));
    }
}
