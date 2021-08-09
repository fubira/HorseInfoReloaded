package net.ironingot.horseinfo;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.ChatFormatting;

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
    public static final String buildId ="2021-1";
    public static String modVersion;

    public static final KeyMapping KEYBINDING_MODE =
        new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category");

    public static final PlayerNameManager playerNameManager = new PlayerNameManager();

    private static boolean isActive = false;

    public HorseInfoMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, KeyInputEvent.class, this::onKeyInput);

        modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        HorseInfoMod.logger.info("*** HorseInfoReloaded " + modVersion + " initialized ***");
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(KEYBINDING_MODE);
    }

	@SubscribeEvent
	public void onConfigLoading(final ModConfigEvent.Loading event) {
	}

    @OnlyIn(Dist.CLIENT)
	@SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        EntityRenderers.register(EntityType.HORSE, RenderHorseExtra::new);
        EntityRenderers.register(EntityType.SKELETON_HORSE, context -> new RenderHorseUndeadExtra(context, ModelLayers.SKELETON_HORSE));
        EntityRenderers.register(EntityType.ZOMBIE_HORSE, context -> new RenderHorseUndeadExtra(context, ModelLayers.ZOMBIE_HORSE));
        EntityRenderers.register(EntityType.MULE, context -> new RenderHorseChestExtra<Mule>(context, 0.92F, ModelLayers.MULE));
        EntityRenderers.register(EntityType.DONKEY, context -> new RenderHorseChestExtra<Donkey>(context, 0.92F, ModelLayers.DONKEY));
        EntityRenderers.register(EntityType.LLAMA, context -> new RenderLlamaExtra(context, ModelLayers.LLAMA));
        EntityRenderers.register(EntityType.WOLF, RenderWolfExtra::new);
        EntityRenderers.register(EntityType.CAT, RenderCatExtra::new);
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();
        mc.player.sendMessage(
            new TextComponent("")
                .append(new TextComponent("[").withStyle(ChatFormatting.GRAY))
                .append(new TextComponent("HorseInfo").withStyle(ChatFormatting.GOLD))
                .append(new TextComponent("] ").withStyle(ChatFormatting.GRAY))
                .append(new TextComponent(s)),
            UUID.randomUUID()
        );
    }

    public static boolean isActive() {
        return isActive;
    }

    public static void toggle() {
        isActive = isActive ? false : true;
        message("HorseInfo " + ((isActive) ? "Enabled" : "Disabled"));
    }

    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(KeyInputEvent event) {
        if (KEYBINDING_MODE.isDown()) {
            toggle();
        }
    }
}
