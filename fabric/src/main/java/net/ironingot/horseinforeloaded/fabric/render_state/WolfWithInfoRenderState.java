package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.WolfRenderState;

@Environment(EnvType.CLIENT)
public class WolfWithInfoRenderState extends WolfRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
