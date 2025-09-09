package net.ironingot.horseinforeloaded.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.chat.Component;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.fabric.renderer.CatWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.DonkeyWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.MuleWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.SkeletonHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.WolfWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.ZombieHorseWithInfoRenderer;

import org.lwjgl.glfw.GLFW;

public class HorseInfoMod implements ClientModInitializer
{
    public static FabricConfig config;

    public static Component modMessageHeader = Component.empty()
        .append(Component.literal("[").withStyle(net.minecraft.ChatFormatting.GRAY))
        .append(Component.literal("HorseInfo").withStyle(net.minecraft.ChatFormatting.GOLD))
        .append(Component.literal("]").withStyle(net.minecraft.ChatFormatting.GRAY));

    @Override
    public void onInitializeClient() {
        config = FabricConfig.register();

        KeyMapping KEYBINDING_MODE = KeyBindingHelper.registerKeyBinding(
            new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category")
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KEYBINDING_MODE.consumeClick()) {
                toggle();
            }
        });

        EntityRendererRegistry.register(EntityType.HORSE, HorseWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.HORSE, HorseWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.WOLF, WolfWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.CAT, CatWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.PARROT, ParrotWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.DONKEY, DonkeyWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.MULE, MuleWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.SKELETON_HORSE, SkeletonHorseWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.ZOMBIE_HORSE, ZombieHorseWithInfoRenderer::new);

        HorseInfoCore.logger.info("*** HorseInfoReloaded initialized ***");
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();

        mc.gui.getChat().addMessage(Component.empty()
            .append(modMessageHeader)
            .append(Component.literal(" " + s))
        );
    }

    public static boolean isActive() {
        return HorseInfoMod.config.enableMod;
    }

    public static void toggle() {
        boolean flag = !isActive();
        HorseInfoMod.config.enableMod = flag;
        HorseInfoMod.config.save();
        message("HorseInfo " + (flag ? "Enabled" : "Disabled"));
    }
}
