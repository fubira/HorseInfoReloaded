package net.ironingot.horseinforeloaded.neoforge.renderer.state;

import java.util.List;

import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoRenderState extends WolfRenderState {
    public List<String> infoStrings;
}
