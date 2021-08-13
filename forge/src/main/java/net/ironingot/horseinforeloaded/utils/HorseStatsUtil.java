package net.ironingot.horseinforeloaded.utils;

import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.ai.attributes.Attributes;

import net.ironingot.horseinforeloaded.common.HorseInfoStats;

import java.awt.Color;
import java.util.List;

public class HorseStatsUtil {
    public static double getSpeed(AbstractHorse entity) {
        return entity.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
    }

    public static double getJumpStrength(AbstractHorse entity) {
        return entity.getAttribute(Attributes.JUMP_STRENGTH).getValue();
    }

    public static double getJumpHeight(AbstractHorse entity) {
        return HorseInfoStats.calcJumpHeight(getJumpStrength(entity));
    }

    public static double getEvaluateValue(AbstractHorse entity) {
        return HorseInfoStats.calcEvaluateValue(
            HorseStatsUtil.getSpeed(entity),
            HorseStatsUtil.getJumpHeight(entity)
        );
    }

    public static String getEvaluateRankString(AbstractHorse entity) {
        return HorseInfoStats.calcEvaluateRankString(
            HorseStatsUtil.getSpeed(entity),
            HorseStatsUtil.getJumpHeight(entity)
        );
    }

    public static Color getEvaluateRankColor(AbstractHorse entity) {
        return HorseInfoStats.calcEvaluateRankColor(
            HorseStatsUtil.getSpeed(entity),
            HorseStatsUtil.getJumpHeight(entity)
        );
    }

    public static List<String> getHorseStatsStrings(AbstractHorse entity) {
        return HorseInfoStats.formatHorseStats(
            entity.getHealth(),
            entity.getMaxHealth(),
            HorseStatsUtil.getSpeed(entity),
            HorseStatsUtil.getJumpStrength(entity),
            HorseStatsUtil.getJumpHeight(entity)
        );
    }
}
