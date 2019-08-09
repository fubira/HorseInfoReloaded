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
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.passive.horse.ZombieHorseEntity;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.MuleEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.WolfEntity;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.StringTextComponent;
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
        EntityRendererManager manager = Minecraft.getInstance().getRenderManager();

        manager.renderers.remove(HorseEntity.class);
        manager.renderers.put(HorseEntity.class, new RenderHorseExtra(manager));
        manager.renderers.remove(SkeletonHorseEntity.class);
        manager.renderers.put(SkeletonHorseEntity.class, new RenderHorseUndeadExtra(manager));
        manager.renderers.remove(ZombieHorseEntity.class);
        manager.renderers.put(ZombieHorseEntity.class, new RenderHorseUndeadExtra(manager));
        manager.renderers.remove(MuleEntity.class);
        manager.renderers.put(MuleEntity.class, new RenderHorseChestExtra(manager, 0.92f));
        manager.renderers.remove(DonkeyEntity.class);
        manager.renderers.put(DonkeyEntity.class, new RenderHorseChestExtra(manager, 0.87f));
        manager.renderers.remove(LlamaEntity.class);
        manager.renderers.put(LlamaEntity.class, new RenderLlamaExtra(manager));
        manager.renderers.remove(WolfEntity.class);
        manager.renderers.put(WolfEntity.class, new RenderWolfExtra(manager));
        manager.renderers.remove(CatEntity.class);
        manager.renderers.put(CatEntity.class, new RenderCatExtra(manager));
    }

    public static void message(String s) {
        Minecraft.getInstance().player.sendMessage(new StringTextComponent("")
            .appendSibling((new StringTextComponent("[")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new StringTextComponent("HorseInfo")).setStyle((new Style()).setColor(TextFormatting.GOLD)))
            .appendSibling((new StringTextComponent("] ")).setStyle((new Style()).setColor(TextFormatting.GRAY)))
            .appendSibling((new StringTextComponent(s))));
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
