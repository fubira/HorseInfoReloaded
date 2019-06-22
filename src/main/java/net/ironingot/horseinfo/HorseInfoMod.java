package net.ironingot.horseinfo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import net.ironingot.horseinfo.playername.PlayerNameManager;

@Mod(HorseInfoMod.modId)
public class HorseInfoMod
{
    private static final Logger logger = LogManager.getLogger();

    public static final String modId ="horseinfo";
    public static final String buildId ="2019-6";
    public static String modVersion;

    public static final KeyBinding KEYBINDING_MODE =
        new KeyBinding("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category");

    public static final PlayerNameManager playerNameManager = new PlayerNameManager();

    private static boolean isActive = false;

    public HorseInfoMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        HorseInfoMod.logger.info("*** HorseInfoReloaded " + modVersion + " initialized ***");
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KEYBINDING_MODE);
    }

	@SubscribeEvent
	public void onConfigLoading(final ModConfig.Loading event) {
	}

	@SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        RenderManager manager = Minecraft.getInstance().getRenderManager();

        manager.entityRenderMap.remove(EntityHorse.class);
        manager.entityRenderMap.put(EntityHorse.class, new RenderHorseExtra(manager));
        manager.entityRenderMap.remove(EntitySkeletonHorse.class);
        manager.entityRenderMap.put(EntitySkeletonHorse.class, new RenderHorseUndeadExtra(manager));
        manager.entityRenderMap.remove(EntityZombieHorse.class);
        manager.entityRenderMap.put(EntityZombieHorse.class, new RenderHorseUndeadExtra(manager));
        manager.entityRenderMap.remove(EntityMule.class);
        manager.entityRenderMap.put(EntityMule.class, new RenderHorseChestExtra(manager, 0.92f));
        manager.entityRenderMap.remove(EntityDonkey.class);
        manager.entityRenderMap.put(EntityDonkey.class, new RenderHorseChestExtra(manager, 0.87f));
        manager.entityRenderMap.remove(EntityLlama.class);
        manager.entityRenderMap.put(EntityLlama.class, new RenderLlamaExtra(manager));
        manager.entityRenderMap.remove(EntityWolf.class);
        manager.entityRenderMap.put(EntityWolf.class, new RenderWolfExtra(manager));
        manager.entityRenderMap.remove(EntityOcelot.class);
        manager.entityRenderMap.put(EntityOcelot.class, new RenderOcelotExtra(manager));
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

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KEYBINDING_MODE.isPressed()) {
            toggle();
        }
    }
}
