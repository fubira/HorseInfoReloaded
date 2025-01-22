package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.LlamaRenderState;

@Environment(EnvType.CLIENT)
public class LlamaWithInfoRenderState extends LlamaRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTamed = false;
    public int strength = 0;
}
