package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.CamelRenderState;
import net.minecraft.world.entity.Entity;

@Environment(EnvType.CLIENT)
public class CamelWithInfoRenderState extends CamelRenderState {
    public String displayName = "";
    public String owner = "";
    public Entity rider = null;
    public boolean isTamed = false;
}
