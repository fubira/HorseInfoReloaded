package net.ironingot.horseinforeloaded.fabric;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.network.chat.Component;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.ironingot.horseinforeloaded.common.HorseInfoCore;
import net.ironingot.horseinforeloaded.fabric.renderer.CamelWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.CatWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.DonkeyWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.LlamaWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.UndeadHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.fabric.renderer.WolfWithInfoRenderer;

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
                toggle();
            }
        });

        EntityRendererRegistry.register(EntityType.HORSE, HorseWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.SKELETON_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.SKELETON_HORSE, ModelLayers.SKELETON_HORSE_BABY, true));
        EntityRendererRegistry.register(EntityType.ZOMBIE_HORSE, context -> new UndeadHorseWithInfoRenderer(context, ModelLayers.ZOMBIE_HORSE, ModelLayers.ZOMBIE_HORSE_BABY, false));
        EntityRendererRegistry.register(EntityType.MULE, context -> new DonkeyWithInfoRenderer<Mule>(context, 0.92F, ModelLayers.MULE, ModelLayers.MULE_BABY, true));
        EntityRendererRegistry.register(EntityType.DONKEY, context -> new DonkeyWithInfoRenderer<Donkey>(context, 0.87F, ModelLayers.DONKEY, ModelLayers.DONKEY_BABY, false));
        EntityRendererRegistry.register(EntityType.LLAMA, LlamaWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.CAMEL, CamelWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.CAT, CatWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.WOLF, WolfWithInfoRenderer::new);
        EntityRendererRegistry.register(EntityType.PARROT, ParrotWithInfoRenderer::new);
        HorseInfoCore.logger.info("*** HorseInfoReloaded initialized ***");
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        mc.player.displayClientMessage(
            Component.Serializer.fromJson("[\"\",{\"text\":\"[\",\"color\":\"gray\"},{\"text\":\"HorseInfo\",\"color\":\"gold\"},{\"text\":\"]\",\"color\":\"gray\"},{\"text\":\" " + s + "\"}]", mc.player.registryAccess()),
            false
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
