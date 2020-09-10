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
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.horse.DonkeyEntity;
import net.minecraft.entity.passive.horse.MuleEntity;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.awt.Color;
import java.util.UUID;

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
        Minecraft mc = Minecraft.getInstance();
        EntityRendererManager manager = mc.getRenderManager();

        manager.renderers.remove(EntityType.HORSE);
        manager.renderers.put(EntityType.HORSE, new RenderHorseExtra(manager));
        manager.renderers.remove(EntityType.SKELETON_HORSE);
        manager.renderers.put(EntityType.SKELETON_HORSE, new RenderHorseUndeadExtra(manager));
        manager.renderers.remove(EntityType.ZOMBIE_HORSE);
        manager.renderers.put(EntityType.ZOMBIE_HORSE, new RenderHorseUndeadExtra(manager));
        manager.renderers.remove(EntityType.MULE);
        manager.renderers.put(EntityType.MULE, new RenderHorseChestExtra<MuleEntity>(manager, 0.92f));
        manager.renderers.remove(EntityType.DONKEY);
        manager.renderers.put(EntityType.DONKEY, new RenderHorseChestExtra<DonkeyEntity>(manager, 0.87f));
        manager.renderers.remove(EntityType.LLAMA);
        manager.renderers.put(EntityType.LLAMA, new RenderLlamaExtra(manager));
        manager.renderers.remove(EntityType.WOLF);
        manager.renderers.put(EntityType.WOLF, new RenderWolfExtra(manager));
        manager.renderers.remove(EntityType.CAT);
        manager.renderers.put(EntityType.CAT, new RenderCatExtra(manager));
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();
        mc.player.sendMessage(new StringTextComponent("")
            .func_230529_a_((new StringTextComponent("[")).func_240699_a_(TextFormatting.GRAY))
            .func_230529_a_((new StringTextComponent("HorseInfo")).func_240699_a_(TextFormatting.GOLD))
            .func_230529_a_((new StringTextComponent("] ")).func_240699_a_(TextFormatting.GRAY))
            .func_230529_a_((new StringTextComponent(s))), UUID.randomUUID());
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
