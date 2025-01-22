package net.ironingot.horseinforeloaded.neoforge.render_state;

import net.minecraft.client.renderer.entity.state.LlamaRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaWithInfoRenderState extends LlamaRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTamed = false;
    public int strength = 0;
}
