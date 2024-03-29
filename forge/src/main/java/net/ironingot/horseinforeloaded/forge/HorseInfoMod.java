package net.ironingot.horseinforeloaded.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;//.ClientRegistry;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.network.chat.Component;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.forge.renderer.CatWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.ChestedHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.LlamaWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.UndeadHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.forge.renderer.WolfWithInfoRenderer;

import org.lwjgl.glfw.GLFW;

@Mod(HorseInfoCore.modId)
public class HorseInfoMod
{
    public static final KeyMapping KEYBINDING_MODE =
        new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category");

    public HorseInfoMod() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, false, Key.class, this::onKeyInput);

        ForgeConfig.register(ModLoadingContext.get());

        String modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        HorseInfoCore.logger.info("*** HorseInfoReloaded " + modVersion + " initialized ***");
    }

    @OnlyIn(Dist.CLIENT)
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEYBINDING_MODE);
    }

	@SubscribeEvent
	public void onConfigLoading(final ModConfigEvent.Loading event) {
	}

    @OnlyIn(Dist.CLIENT)
	@SubscribeEvent
    public void onInterModEnqueue(InterModEnqueueEvent event) {
        EntityRenderers.register(EntityType.HORSE, HorseWithInfoRenderer::new);
        EntityRenderers.register(EntityType.SKELETON_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.SKELETON_HORSE));
        EntityRenderers.register(EntityType.ZOMBIE_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.ZOMBIE_HORSE));
        EntityRenderers.register(EntityType.MULE, context -> new ChestedHorseWithInfoRenderer<Mule>(context, 0.92F, ModelLayers.MULE));
        EntityRenderers.register(EntityType.DONKEY, context -> new ChestedHorseWithInfoRenderer<Donkey>(context, 0.92F, ModelLayers.DONKEY));
        EntityRenderers.register(EntityType.LLAMA, context -> new LlamaWithInfoRenderer(context, ModelLayers.LLAMA));
        EntityRenderers.register(EntityType.WOLF, WolfWithInfoRenderer::new);
        EntityRenderers.register(EntityType.CAT, CatWithInfoRenderer::new);
        EntityRenderers.register(EntityType.PARROT, ParrotWithInfoRenderer::new);
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();
        mc.player.sendSystemMessage(
            Component.Serializer.fromJson("[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"HorseInfo\",\"color\":\"gold\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" " + s + "\"}]")
        );
    }

    public static boolean isActive() {
        return ForgeConfig.enableMod.get();
    }

    public static void toggle() {
        boolean flag = !isActive();
        ForgeConfig.enableMod.set(flag);
        message("HorseInfo " + (flag ? "Enabled" : "Disabled"));
    }

    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(Key event) {
        if (KEYBINDING_MODE.consumeClick()) {
            toggle();
        }
    }
}
