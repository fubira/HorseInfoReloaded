package net.ironingot.horseinforeloaded.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class ForgeConfig {
    public static final ForgeConfigSpec spec;
    public static final BooleanValue enableMod;

    public static void register(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, spec);
    }

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("general");

        enableMod = builder.define("enableMod", true);

        builder.pop();
        spec = builder.build();
    }
}