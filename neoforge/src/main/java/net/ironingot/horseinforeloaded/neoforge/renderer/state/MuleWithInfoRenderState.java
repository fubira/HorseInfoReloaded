package net.ironingot.horseinforeloaded.neoforge.renderer.state;

import java.util.List;

import net.minecraft.client.renderer.entity.state.DonkeyRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MuleWithInfoRenderState extends DonkeyRenderState {
    public List<String> infoStrings;
    public int titleColor;
    public int fontColor;
    public int bgColor;
}
