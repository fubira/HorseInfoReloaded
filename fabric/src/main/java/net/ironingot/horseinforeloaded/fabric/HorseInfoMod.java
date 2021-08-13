package net.ironingot.horseinforeloaded.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.fabric.renderer.CatWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.ChestedHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.LlamaWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.UndeadHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.WolfWithInfoRenderer;

import java.util.UUID;

import org.lwjgl.glfw.GLFW;

public class HorseInfoMod implements ClientModInitializer
{
    public static FabricConfig config;

    @Override
    public void onInitializeClient() {
        config = FabricConfig.register();

        KeyMapping KEYBINDING_MODE = KeyBindingHelper.registerKeyBinding(
            new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category")
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEYBINDING_MODE.consumeClick()) {
                HorseInfoCore.logger.info("KEYBINDING_MODE.isDown");
                toggle();
            }
        });

        EntityRendererRegistry.INSTANCE.register(EntityType.HORSE, HorseWithInfoRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityType.SKELETON_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.SKELETON_HORSE));
        EntityRendererRegistry.INSTANCE.register(EntityType.ZOMBIE_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.ZOMBIE_HORSE));
        EntityRendererRegistry.INSTANCE.register(EntityType.MULE, context -> new ChestedHorseWithInfoRenderer<Mule>(context, 0.92F, ModelLayers.MULE));
        EntityRendererRegistry.INSTANCE.register(EntityType.DONKEY, context -> new ChestedHorseWithInfoRenderer<Donkey>(context, 0.92F, ModelLayers.DONKEY));
        EntityRendererRegistry.INSTANCE.register(EntityType.LLAMA, context -> new LlamaWithInfoRenderer(context, ModelLayers.LLAMA));
        EntityRendererRegistry.INSTANCE.register(EntityType.CAT, CatWithInfoRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityType.WOLF, WolfWithInfoRenderer::new);
        EntityRendererRegistry.INSTANCE.register(EntityType.PARROT, ParrotWithInfoRenderer::new);
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
        return HorseInfoMod.config.enableMod;
    }

    public static void toggle() {
        boolean flag = !isActive();
        HorseInfoMod.config.enableMod = flag;
        message("HorseInfo " + (flag ? "Enabled" : "Disabled"));
    }
}
