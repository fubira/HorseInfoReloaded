package net.ironingot.horseinforeloaded.neoforge;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.BooleanValue enableMod = BUILDER.comment("Enable mod").define("enableMod", true);

    public static final ModConfigSpec SPEC = BUILDER.push("general").build();
}