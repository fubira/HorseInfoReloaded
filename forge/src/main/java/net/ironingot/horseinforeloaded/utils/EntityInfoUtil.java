package net.ironingot.horseinforeloaded.utils;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.ironingot.horseinforeloaded.HorseInfoMod;
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
            ownerName = HorseInfoMod.playerNameManager.getPlayerName(ownerUUID);

        return "(Owner: " + ownerName + ")";
    }

    public static String getOwnerString(OwnableEntity entity) {
        return getOwnerString(entity.getOwnerUUID());
    }

    public static String getOwnerString(AbstractHorse entity) {
        return getOwnerString(entity.getOwnerUUID());
    }

    private static String getAgeOrOwnerString(boolean isBaby, UUID uuid) {
        if (isBaby) {
            return "(Baby)";
        } else {
            return getOwnerString(uuid);
        }
    }

    public static String getAgeOrOwnerString(TamableAnimal entity) {
        return getAgeOrOwnerString(entity.isBaby(), entity.getOwnerUUID());
    }

    public static String getAgeOrOwnerString(AbstractHorse entity) {
        return getAgeOrOwnerString(entity.isBaby(), entity.getOwnerUUID());
    }

    public static String getDisplayNameWithRank(AbstractHorse entity) {
        return EntityInfoUtil.getDisplayNameString(entity) +
               " [" + HorseStatsUtil.getEvaluateRankString(HorseStatsUtil.getEvaluateValue(entity)) + "]";
    }

    public static List<String> getHorseStatsString(AbstractHorse entity) {
        List<String> stringArray = new ArrayList<String>();
        List<Entity> passengers = entity.getPassengers();

        if (passengers == null || passengers.size() == 0) {
            double paramHealth = entity.getHealth();
            double paramMaxHealth = entity.getMaxHealth();
            double paramSpeed = HorseStatsUtil.getSpeed(entity);
            double paramJump = HorseStatsUtil.getJumpStrength(entity);
            double jumpHeight = HorseStatsUtil.getJumpHeight(entity);
            // double paramRank = getEvaluateValue(entity);

            stringArray.add(String.format("HP: %.2f/%.2f", paramHealth, paramMaxHealth));
            stringArray.add(String.format("SP: %.4f [%.1f(m/s)]", paramSpeed, paramSpeed * 43.0D));
            stringArray.add(String.format("JP: %.4f [%.1f(m)]", paramJump, jumpHeight));
        }
        return stringArray;
    }
}
