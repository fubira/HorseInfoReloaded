package net.ironingot.horseinfo;

import java.util.UUID;

// import net.minecraftforge.common.UsernameCache;
import net.minecraft.entity.passive.EntityTameable;

class AnimalInfoUtil {
    public static String getDisplayName(EntityTameable entity)
    {
        return entity.getDisplayName().getFormattedText();
    }

    public static String getOwner(EntityTameable entity)
    {
        UUID ownerUUID = entity.getOwnerId();
        if (ownerUUID == null)
            return null;

        String ownerName = "Unknown";
        ownerName = HorseInfoMod.playerNameManager.getPlayerName(ownerUUID);

        return "(Owner: " + ownerName + ")";
    }
}
