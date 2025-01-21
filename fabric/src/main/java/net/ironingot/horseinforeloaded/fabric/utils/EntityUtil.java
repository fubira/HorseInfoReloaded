package net.ironingot.horseinforeloaded.fabric.utils;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.common.HorseInfoFormat;
import net.ironingot.horseinforeloaded.fabric.render_state.HorseWithInfoRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.Nameable;

public class EntityUtil {
    public static String getDisplayNameString(Nameable entity)
    {
        return entity.getDisplayName().getString();
    }

    private static String getOwnerString(UUID uuid) {
        if (uuid == null)
            return "(Owner: None)";
    
        String ownerName = "Unknown";
        ownerName = HorseInfoCore.playerNameManager.getPlayerName(uuid);
    
        return "(Owner: " + ownerName + ")";
    }
    
    public static String getOwnerString(OwnableEntity entity) {
        return getOwnerString(entity.getOwnerUUID());
    }

    public static String getOwnerString(AbstractHorse entity) {
        return getOwnerString(entity.getOwnerUUID());
    }

    public static String getAgeOrOwnerString(TamableAnimal entity) {
        return entity.isBaby() ? "(Baby)" : getOwnerString(entity.getOwnerUUID());
    }

    public static String getAgeOrOwnerString(AbstractHorse entity) {
        return entity.isBaby() ? "(Baby)" : getOwnerString(entity.getOwnerUUID());
    }

    public static String getDisplayNameWithRank(HorseWithInfoRenderState renderState) {
        return HorseInfoFormat.formatHorseNameWithRank(
            renderState.displayName,
            HorseEntityUtil.getEvaluateRankString(renderState)
        );
    }

    public static List<String> getHorseStatsString(HorseWithInfoRenderState renderState) {
        if (renderState.rider == null) {
            return HorseEntityUtil.getStatsStrings(renderState);
        }
        return null;
    }

    public static Entity getRider(Entity entity)
    {
        List<Entity> passengers = entity.getPassengers();
        if (passengers.isEmpty())
            return null;

        return passengers.getFirst();
    }
}
