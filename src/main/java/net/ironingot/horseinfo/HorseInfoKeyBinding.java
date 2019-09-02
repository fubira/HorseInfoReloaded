package net.ironingot.horseinfo;

import org.dimdev.rift.listener.client.KeyBindingAdder;
import org.dimdev.rift.listener.client.KeybindHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.glfw.GLFW;
import java.util.Collection;
import java.util.Arrays;

public class HorseInfoKeyBinding implements /* KeyBindingAdder, */ KeybindHandler
{
    public static final KeyBinding KEYBINDING_MODE =
        new KeyBinding("horseinfo.keybinding.desc.toggle", GLFW.GLFW_KEY_H, "horseinfo.keybinding.category");

    /*
    @Override
    public Collection<? extends KeyBinding> getKeyBindings() {
        KeyBinding [] bindings = {
            KEYBINDING_MODE
        };
        return Arrays.asList(bindings);
    }
    */

    @Override
    public void processKeybinds() {
        if (KEYBINDING_MODE.isPressed()) {
            HorseInfoMod.toggle();
        }
    }

}
