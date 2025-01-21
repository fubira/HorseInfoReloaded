package net.ironingot.horseinforeloaded.fabric.render_state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.state.CatRenderState;

@Environment(EnvType.CLIENT)
public class CatWithInfoRenderState extends CatRenderState {
    public String displayName = "";
    public String owner = "";
    public boolean isTame = false;
}
