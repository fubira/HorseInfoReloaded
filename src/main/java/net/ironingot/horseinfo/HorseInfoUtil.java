package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.ironingot.horseinfo.playername.PlayerNameManager;

class HorseInfoUtil {

    public static double getSpeed(EntityHorse entity)
    {
        return entity.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
    }

    public static double getJumpHeight(double jumpStrength)
    {
        double yVelocity = jumpStrength;
        double jumpHeight = 0.0d;

        while (yVelocity > 0.0D)
        {
            jumpHeight += yVelocity;
            yVelocity -= 0.08D;
            yVelocity *= 0.98D;
        }

        return Math.floor(jumpHeight * 10.0D) / 10.0D ;
    }

    public static double getEvaluateValue(EntityHorse entity)
    {
        double paramSpeed = HorseInfoUtil.getSpeed(entity);
        double jumpHeight = HorseInfoUtil.getJumpHeight(entity.getHorseJumpStrength());
        double jumpRating = Math.floor(jumpHeight * 2.0D) / (2.0D * 5.0D);

        final double speedHeavy = 10.0D;
        final double heightHeavy = 1.0D;

        final double valueMax = 0.3375D * speedHeavy + 1.0D * heightHeavy;
        double value = (paramSpeed * speedHeavy) + jumpRating * heightHeavy;
        return value / valueMax;
    }

    public static String getEvaluateRankString(double horseEvaluate) {
        final String [] rankString = {
            "G", "G", "G",
            "F", "F", "F",
            "E", "E", "E",
            "D", "D", "D",
            "C", "C+", "C++",
            "B", "B+", "B++",
            "A", "A+", "A++",
            "S", "S+", "S++",
            "LEGEND"
        };
        double rate = horseEvaluate * 2.0D - 1.0;
        int pt = (int)(rate * rankString.length);
        if (pt >= rankString.length)
            return rankString[rankString.length-1];
        if (pt < 0)
            return rankString[0];
        return rankString[pt];
    }

    public static Color getEvaluateRankColor(double horseEvaluate) {
        final Color [] rankColor = {
            Color.BLACK, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK,
            Color.BLACK, Color.BLACK, Color.BLACK,
            new Color(0x55, 0x55, 0xFF), new Color(0x55, 0x55, 0xFF), new Color(0x00, 0xAA, 0xFF),
            new Color(0x55, 0xFF, 0xFF), new Color(0x55, 0xFF, 0x55), new Color(0xFF, 0xFF, 0x55),
            new Color(0xFF, 0xAA, 0x00), new Color(0xFF, 0x55, 0x55), new Color(0xFF, 0x55, 0xFF),
            new Color(0xFF, 0xCC, 0xFF)
        };
        double rate = horseEvaluate * 2.0D - 1.0;
        int pt = (int)(rate * rankColor.length);
        if (pt >= rankColor.length)
            return rankColor[rankColor.length-1];
        if (pt < 0)
            return rankColor[0];
        return rankColor[pt];
    }

    public static String getDisplayName(EntityHorse entity)
    {
        return entity.getDisplayName().getFormattedText();
    }

    public static String getDisplayNameWithRank(EntityHorse entity)
    {
        return getDisplayName(entity) +
               " [" + HorseInfoUtil.getEvaluateRankString(HorseInfoUtil.getEvaluateValue(entity)) + "]";
    }

    public static String getOwner(EntityHorse entity)
    {
        UUID ownerUUID = entity.getOwnerUniqueId();
        if (ownerUUID == null)
            return null;

        String ownerName = "Unknown";
        if (UsernameCache.containsUUID(ownerUUID))
            ownerName = UsernameCache.getLastKnownUsername(ownerUUID);
        else
            ownerName = HorseInfoMod.playerNameManager.getPlayerName(ownerUUID);

        return "(Owner: " + ownerName + ")";
    }

    public static String getAgeOrOwnerString(EntityHorse entity)
    {
        String str = null;
        List<Entity> passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0)
        {
            if (entity.isChild())
            {
                str = "(Baby)";
            }
            else if (entity.getHasReproduced())
            {
                str = "(Waiting)";
            }
            else
            {
                str = getOwner(entity);
            }
        }
        return str;
    }

    public static List<String> getHorseInfoString(EntityHorse entity)
    {
        List<String> stringArray = new ArrayList<String>();
        List<Entity> passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0)
        {
            double paramHealth = entity.getHealth();
            double paramMaxHealth = entity.getMaxHealth();
            double paramSpeed = getSpeed(entity);
            double paramJump = entity.getHorseJumpStrength();
            double jumpHeight = getJumpHeight(paramJump);
            double paramRank = getEvaluateValue(entity);

            stringArray.add(String.format("HP: %.2f/%.2f", paramHealth, paramMaxHealth));
            stringArray.add(String.format("SP: %.4f [%.1f(m/s)]", paramSpeed, paramSpeed * 43.0D));
            stringArray.add(String.format("JP: %.4f [%.1f(m)]", paramJump, jumpHeight));
        }
        return stringArray;
    }
}
