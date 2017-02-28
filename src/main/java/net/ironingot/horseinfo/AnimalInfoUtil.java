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

    public static String getOwner(EntityTameable entity)
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
