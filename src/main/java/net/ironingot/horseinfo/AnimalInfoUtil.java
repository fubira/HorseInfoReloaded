package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;

import net.ironingot.horseinfo.playername.PlayerNameManager;

class AnimalInfoUtil {
    public static String getDisplayName(EntityTameable entity)
    {
        return entity.getDisplayName().getFormattedText();
    }

    public static String getAgeOrOwnerString(EntityTameable entity)
    {
        String str = null;
        List<Entity> passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0)
        {
            int age = entity.getGrowingAge();
            if (age < 1)
            {
                str = "<BABY>";
            }
            else
            {
                UUID ownerUUID = entity.getOwnerId();
                if (ownerUUID != null)
                {
                    UUID uuid = ownerUUID;
                    String ownerName = "Unknown";
                    if (UsernameCache.containsUUID(uuid))
                        ownerName = UsernameCache.getLastKnownUsername(uuid);
                    else
                        ownerName = HorseInfoMod.playerNameManager.getPlayerName(uuid);

                    str = ("OWNER: " + ownerName);
                }
            }
        }
        return str;
    }
}
