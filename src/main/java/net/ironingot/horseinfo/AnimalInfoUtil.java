package net.ironingot.horseinfo;

import java.util.UUID;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.entity.passive.TameableEntity;

class AnimalInfoUtil {
    public static String getDisplayName(TameableEntity entity)
    {
        return entity.getDisplayName().getFormattedText();
    }

    public static String getOwner(TameableEntity entity)
    {
        UUID ownerUUID = entity.getOwnerId();
        if (ownerUUID == null)
            return null;

        String ownerName = "Unknown";
        if (UsernameCache.containsUUID(ownerUUID))
            ownerName = UsernameCache.getLastKnownUsername(ownerUUID);
        else
            ownerName = HorseInfoMod.playerNameManager.getPlayerName(ownerUUID);

        return "(Owner: " + ownerName + ")";
    }
}
