package net.ironingot.horseinforeloaded.fabric.utils;

import java.util.UUID;
import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.Nameable;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.common.HorseInfoFormat;

public class EntityUtil {
    public static String getDisplayNameString(Nameable entity)
    {
        return entity.getDisplayName().getString();
    }

    private static String getOwnerString(UUID uuid) {
        UUID ownerUUID = uuid;
        if (ownerUUID == null)
            return null;
    
        String ownerName = "Unknown";
        ownerName = HorseInfoCore.playerNameManager.getPlayerName(ownerUUID);
    
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

    public static String getDisplayNameWithRank(AbstractHorse entity) {
        return HorseInfoFormat.formatHorseNameWithRank(
            EntityUtil.getDisplayNameString(entity),
            HorseEntityUtil.getEvaluateRankString(entity)
        );
    }

    public static List<String> getHorseStatsString(AbstractHorse entity) {
        List<Entity> passengers = entity.getPassengers();

        if (passengers == null || passengers.size() == 0) {
            return HorseEntityUtil.getStatsStrings(entity);
        }
        return null;
    }

    public static Entity getRider(Entity entity)
    {
        List<Entity> passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0)
            return null;

        return passengers.get(0);
    }
}
