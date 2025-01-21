package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;

@Environment(EnvType.CLIENT)
public class ParrotWithInfoRenderState extends ParrotRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
