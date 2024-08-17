package net.ironingot.horseinforeloaded.neoforge;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.network.chat.Component;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.neoforge.renderer.CatWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.ChestedHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.LlamaWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.UndeadHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.WolfWithInfoRenderer;

import org.lwjgl.glfw.GLFW;

@Mod(HorseInfoCore.modId)
public class HorseInfoMod
{
    public static final KeyMapping KEYBINDING_MODE =
        new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category");

    public HorseInfoMod(IEventBus modBus, ModContainer modContainer) {
        modBus.addListener(this::onInterModEnqueue);
        modBus.addListener(this::onRegisterKeyMappings);
        NeoForge.EVENT_BUS.addListener(this::onKeyInput);

        modContainer.registerConfig(ModConfig.Type.CLIENT, NeoForgeConfig.SPEC);

        String modVersion = ModLoadingContext.get().getActiveContainer().getModInfo().getVersion().toString();
        HorseInfoCore.logger.info("*** HorseInfoReloaded " + modVersion + " initialized ***");
    }

    @OnlyIn(Dist.CLIENT)
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(KEYBINDING_MODE);
    }

    @OnlyIn(Dist.CLIENT)
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
            Component.Serializer.fromJson("[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"HorseInfo\",\"color\":\"gold\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" " + s + "\"}]", mc.player.registryAccess())
        );
    }

    public static boolean isActive() {
        return NeoForgeConfig.enableMod.get();
    }

    public static void toggle() {
        boolean flag = !isActive();
        NeoForgeConfig.enableMod.set(flag);
        message("HorseInfo " + (flag ? "Enabled" : "Disabled"));
    }

    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(Key event) {
        if (KEYBINDING_MODE.consumeClick()) {
            toggle();
        }
    }
}
