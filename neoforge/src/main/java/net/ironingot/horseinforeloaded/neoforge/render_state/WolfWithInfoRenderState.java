package net.ironingot.horseinforeloaded.neoforge.render_state;

import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoRenderState extends WolfRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
