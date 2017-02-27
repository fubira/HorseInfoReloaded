package net.ironingot.horseinfo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityHorse;

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
public class HorseInfo
{
    private static final Logger logger = FMLLog.getLogger();
    public static final KeyBinding KEYBINDING_MODE = new KeyBinding("HorseInfo Mode", Keyboard.KEY_H, "HorseInfoReloaded");
    public static final PlayerNameManager playerNameManager = new PlayerNameManager();

    private static boolean isShow = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        ClientRegistry.registerKeyBinding(KEYBINDING_MODE);
        Minecraft.getMinecraft().getRenderManager().entityRenderMap.remove(EntityHorse.class);

        // RenderingRegistry.registerEntityRenderingHandler(EntityHorse.class, new RenderHorseExtra(Minecraft.getMinecraft().getRenderManager(), new ModelHorse(), 0.75F));

        RenderingRegistry.registerEntityRenderingHandler(EntityHorse.class, new IRenderFactory<EntityHorse>() {
            public Render<EntityHorse> createRenderFor(RenderManager manager) {
                return new RenderHorseExtra(Minecraft.getMinecraft().getRenderManager());
            }
        });

        MinecraftForge.EVENT_BUS.register(this);
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

    public static boolean isShowHorseInfo()
    {
        return isShow;
    }

    public static void toggleShowHide()
    {
        isShow = isShow ? false : true;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if (HorseInfo.isShowKeyDown())
            toggleShowHide();
    }
}
