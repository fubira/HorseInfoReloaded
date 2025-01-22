package net.ironingot.horseinforeloaded.neoforge.render_state;

import net.minecraft.client.renderer.entity.state.CamelRenderState;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CamelWithInfoRenderState extends CamelRenderState {
    public String displayName = "";
    public String owner = "";
    public Entity rider = null;
    public boolean isTamed = false;
}
