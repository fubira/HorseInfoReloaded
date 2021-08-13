package net.ironingot.horseinforeloaded.utils;

import java.util.UUID;
import java.util.List;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.common.HorseInfoFormat;
import net.minecraft.world.Nameable;

public class EntityInfoUtil {
    public static String getDisplayNameString(Nameable entity)
    {
        return entity.getDisplayName().getString();
    }

    private static String getOwnerString(UUID uuid) {
        UUID ownerUUID = uuid;
        if (ownerUUID == null)
            return null;
    
        String ownerName = "Unknown";
        if (UsernameCache.containsUUID(ownerUUID))
            ownerName = UsernameCache.getLastKnownUsername(ownerUUID);
        else
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
            EntityInfoUtil.getDisplayNameString(entity),
            HorseStatsUtil.getEvaluateRankString(entity)
        );
    }

    public static List<String> getHorseStatsString(AbstractHorse entity) {
        List<Entity> passengers = entity.getPassengers();

        if (passengers == null || passengers.size() == 0) {
            return HorseStatsUtil.getHorseStatsStrings(entity);
        }
        return null;
    }
}
