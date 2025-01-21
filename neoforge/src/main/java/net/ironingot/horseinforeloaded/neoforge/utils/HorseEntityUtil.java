package net.ironingot.horseinforeloaded.neoforge.utils;

import net.ironingot.horseinforeloaded.neoforge.render_state.HorseWithInfoRenderState;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.ironingot.horseinforeloaded.common.HorseInfoFormat;
import net.ironingot.horseinforeloaded.common.HorseInfoStats;

import java.awt.Color;
import java.util.List;
import java.util.Objects;

public class HorseEntityUtil {
    public static double getSpeed(AbstractHorse entity) {
        return Objects.requireNonNull(entity.getAttribute(Attributes.MOVEMENT_SPEED)).getValue();
    }

    public static double getJumpStrength(AbstractHorse entity) {
        return Objects.requireNonNull(entity.getAttribute(Attributes.JUMP_STRENGTH)).getValue();
    }

    public static double getJumpHeight(AbstractHorse entity) {
        return HorseInfoStats.calcJumpHeight(getJumpStrength(entity));
    }

    public static double getEvaluateValue(HorseWithInfoRenderState renderState) {
        return HorseInfoStats.calcEvaluateValue(
                renderState.speed,
                renderState.jumpHeight
        );
    }

    public static String getEvaluateRankString(HorseWithInfoRenderState renderState) {
        return HorseInfoStats.calcEvaluateRankString(
                renderState.speed,
                renderState.jumpHeight
        );
    }

    public static Color getEvaluateRankColor(HorseWithInfoRenderState renderState) {
        return HorseInfoStats.calcEvaluateRankColor(
            renderState.speed,
            renderState.jumpHeight
        );
    }

    public static List<String> getStatsStrings(HorseWithInfoRenderState renderState) {
        return HorseInfoFormat.formatHorseStats(
            renderState.health,
            renderState.maxHealth,
            renderState.speed,
            renderState.jumpStrength,
            renderState.jumpHeight
        );
    }
}
