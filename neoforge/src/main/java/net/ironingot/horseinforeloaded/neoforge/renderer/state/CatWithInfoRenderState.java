package net.ironingot.horseinforeloaded.neoforge.renderer.state;

import java.util.List;

import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatWithInfoRenderState extends CatRenderState {
    public List<String> infoStrings;
}
