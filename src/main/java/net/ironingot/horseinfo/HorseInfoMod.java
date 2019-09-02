package net.ironingot.horseinfo;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import org.dimdev.riftloader.listener.InitializationListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import net.ironingot.horseinfo.playername.PlayerNameManager;

public class HorseInfoMod implements InitializationListener
{
    public static final Logger logger = LogManager.getLogger();
    public static PlayerNameManager playerNameManager = new PlayerNameManager();
    private static boolean isActive = false;

    @Override
    public void onInitialization() {
    }

    public static void message(String s) {
        Minecraft.getInstance().player.sendMessage(new TextComponentString("")
            .appendSibling((new TextComponentString("[")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new TextComponentString("HorseInfo")).setStyle((new Style()).setColor(TextFormatting.GOLD)))
            .appendSibling((new TextComponentString("] ")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new TextComponentString(s))));
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void toggle() {
        isActive = isActive ? false : true;
        message("HorseInfo " + ((isActive) ? "Enabled" : "Disabled"));
    }    
}
