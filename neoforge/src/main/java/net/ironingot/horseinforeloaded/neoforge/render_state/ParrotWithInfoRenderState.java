package net.ironingot.horseinforeloaded.neoforge.render_state;

import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotWithInfoRenderState extends ParrotRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
