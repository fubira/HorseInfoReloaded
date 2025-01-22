package net.ironingot.horseinforeloaded.neoforge.render_state;

import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.world.entity.animal.CatVariant;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatWithInfoRenderState extends CatRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
