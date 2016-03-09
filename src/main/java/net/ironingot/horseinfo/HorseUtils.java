package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraftforge.common.UsernameCache;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor;

import net.ironingot.horseinfo.playername.PlayerNameManager;

class HorseUtils {

    public static double getSpeed(EntityHorse entityHorse)
    {
        return entityHorse.getEntityAttribute(net.minecraft.entity.SharedMonsterAttributes.movementSpeed).getAttributeValue();
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

    public static double getEvaluateValue(EntityHorse entityHorse)
    {
        double paramSpeed = HorseUtils.getSpeed(entityHorse);
        double jumpHeight = HorseUtils.getJumpHeight(entityHorse.getHorseJumpStrength());
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

    public static Color getRiderHelmColor(EntityHorse entityHorse)
    {
        Entity ridingEntity = entityHorse.riddenByEntity;
        if (ridingEntity instanceof EntityPlayer)
        {
            EntityPlayer ridingPlayer = (EntityPlayer)ridingEntity;
            ItemStack helmStack = ridingPlayer.inventory.armorItemInSlot(3);

            if (helmStack != null && helmStack.getItem() instanceof ItemArmor)
            {
                ItemArmor helmItem = (ItemArmor)helmStack.getItem();
                if (helmItem.hasColor(helmStack))
                {
                    return new Color(helmItem.getColor(helmStack));
                }
            }
        }
        return null;
    }

    public static String getDisplayNameWithRank(EntityHorse entityHorse)
    {
        return entityHorse.getDisplayName().getFormattedText() +
               " [" + HorseUtils.getEvaluateRankString(HorseUtils.getEvaluateValue(entityHorse)) + "]";
    }

    public static List<String> getHorseInfoString(EntityHorse entityHorse)
    {
        List<String> stringArray = new ArrayList<String>();

        if (entityHorse.riddenByEntity == null)
        {
            double paramHealth = entityHorse.getHealth();
            double paramMaxHealth = entityHorse.getMaxHealth();
            double paramSpeed = getSpeed(entityHorse);
            double paramJump = entityHorse.getHorseJumpStrength();
            double jumpHeight = getJumpHeight(paramJump);
            double paramRank = getEvaluateValue(entityHorse);

            stringArray.add(String.format("HP: %.2f/%.2f", paramHealth, paramMaxHealth));
            stringArray.add(String.format("SP: %.4f [%.1f(m/s)]", paramSpeed, paramSpeed * 43.0D));
            stringArray.add(String.format("JP: %.4f [%.1f(m)]", paramJump, jumpHeight));

            int age = entityHorse.getGrowingAge();
            if (age > 0)
            {
                stringArray.add("... cooling down ...");
            }
            else if (age < 0)
            {
                stringArray.add("... growing ...");
            }
            else
            {
                String ownerId = entityHorse.getOwnerId();
                if (ownerId != null && ownerId.length() > 0)
                {
                    UUID uuid = UUID.fromString(entityHorse.getOwnerId());
                    String ownerName = "Unknown";
                    if (UsernameCache.containsUUID(uuid))
                        ownerName = UsernameCache.getLastKnownUsername(uuid);
                    else
                        ownerName = HorseInfo.playerNameManager.getPlayerName(uuid);

                    stringArray.add("OWNER: " + ownerName);
                }
                else
                {
                    stringArray.add("NO OWNER");
                }
            }
        }
        return stringArray;
    }

}
