package net.ironingot.horseinfo;

import java.util.UUID;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.Nameable;

class AnimalInfoUtil {
    public static String getDisplayName(Nameable nameable)
    {
        return nameable.getDisplayName().getString();
    }

    public static String getOwner(OwnableEntity entity)
    {
        UUID ownerUUID = entity.getOwnerUUID();
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
