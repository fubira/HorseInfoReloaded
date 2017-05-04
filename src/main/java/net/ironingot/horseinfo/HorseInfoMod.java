package net.ironingot.horseinfo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.apache.logging.log4j.Logger;

import net.ironingot.horseinfo.playername.PlayerNameManager;

@Mod(modid="horseinforeloaded",
     name="HorseInfoReloaded",
     dependencies = "required-after:forge@[13.19.1,)",
     acceptableRemoteVersions = "*",
     acceptedMinecraftVersions = "",
     version="@VERSION@")
public class HorseInfoMod
{
    private static final Logger logger = FMLLog.getLogger();
    public static final KeyBinding KEYBINDING_MODE = new KeyBinding("HorseInfo Mode", Keyboard.KEY_H, "HorseInfoReloaded");
    public static final PlayerNameManager playerNameManager = new PlayerNameManager();

    private static boolean isActive = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientRegistry.registerKeyBinding(KEYBINDING_MODE);

        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityHorse.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityHorse.class, new RenderHorseExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntitySkeletonHorse.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntitySkeletonHorse.class, new RenderAbstractHorseExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityZombieHorse.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityZombieHorse.class, new RenderAbstractHorseExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityMule.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityMule.class, new RenderAbstractHorseExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityDonkey.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityDonkey.class, new RenderAbstractHorseExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityLlama.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityLlama.class, new RenderLlamaExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityWolf.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityWolf.class, new RenderWolfExtra(Minecraft.getMinecraft().getRenderManager()));
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityOcelot.class);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntityOcelot.class, new RenderOcelotExtra(Minecraft.getMinecraft().getRenderManager()));

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void message(String s)
    {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("")
            .appendSibling((new TextComponentString("[")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new TextComponentString("HorseInfo")).setStyle((new Style()).setColor(TextFormatting.GREEN)))
            .appendSibling((new TextComponentString("] ")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new TextComponentString(s))));
    }

    public static boolean isShowKeyDown()
    {
        int keyCode = KEYBINDING_MODE.getKeyCode();
        if (keyCode > 0)
        {
            return Keyboard.isKeyDown(keyCode);
        }
        else
        {
            return Mouse.isButtonDown(100 + keyCode);
        }
    }

    public static boolean isActive()
    {
        return isActive;
    }

    public static void toggle()
    {
        isActive = isActive ? false : true;
        message("HorseInfo " + ((isActive) ? "is Activeated." : "is Deactivated."));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (HorseInfoMod.isShowKeyDown())
            toggle();
    }
}
