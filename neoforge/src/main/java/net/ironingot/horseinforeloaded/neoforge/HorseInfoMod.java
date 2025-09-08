package net.ironingot.horseinforeloaded.neoforge;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.EntityType;
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
import net.ironingot.horseinforeloaded.neoforge.renderer.DonkeyWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.HorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.MuleWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.ParrotWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.SkeletonHorseWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.WolfWithInfoRenderer;
import net.ironingot.horseinforeloaded.neoforge.renderer.ZombieHorseWithInfoRenderer;

import org.lwjgl.glfw.GLFW;

@Mod(HorseInfoCore.modId)
public class HorseInfoMod
{
    public static final KeyMapping KEYBINDING_MODE =
        new KeyMapping("horseinforeloaded.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinforeloaded.keybinding.category");

    public static Component modMessageHeader = Component.empty()
        .append(Component.literal("[").withStyle(net.minecraft.ChatFormatting.GRAY))
        .append(Component.literal("HorseInfo").withStyle(net.minecraft.ChatFormatting.GOLD))
        .append(Component.literal("]").withStyle(net.minecraft.ChatFormatting.GRAY));

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
        EntityRenderers.register(EntityType.WOLF, WolfWithInfoRenderer::new);
        EntityRenderers.register(EntityType.CAT, CatWithInfoRenderer::new);
        EntityRenderers.register(EntityType.PARROT, ParrotWithInfoRenderer::new);
        EntityRenderers.register(EntityType.DONKEY, DonkeyWithInfoRenderer::new);
        EntityRenderers.register(EntityType.MULE, MuleWithInfoRenderer::new);
        EntityRenderers.register(EntityType.SKELETON_HORSE, SkeletonHorseWithInfoRenderer::new);
        EntityRenderers.register(EntityType.ZOMBIE_HORSE, ZombieHorseWithInfoRenderer::new);
    }

    public static void message(String s) {
        Minecraft mc = Minecraft.getInstance();

        mc.gui.getChat().addMessage(Component.empty()
            .append(modMessageHeader)
            .append(Component.literal(" " + s))
        );
    }

    public static boolean isActive() {
        return NeoForgeConfig.enableMod.get();
    }

    public static void toggle() {
        boolean flag = !isActive();
        NeoForgeConfig.enableMod.set(flag);
        NeoForgeConfig.SPEC.save();
        message("HorseInfo " + (flag ? "Enabled" : "Disabled"));
    }

    @OnlyIn(Dist.CLIENT)
    public void onKeyInput(Key event) {
        if (KEYBINDING_MODE.consumeClick()) {
            toggle();
        }
    }
}
