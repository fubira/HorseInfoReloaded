package net.ironingot.horseinforeloaded.neoforge.renderer.state;

import java.util.List;

import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HorseWithInfoRenderState extends HorseRenderState {
    public List<String> infoStrings;
    public int titleColor;
    public int fontColor;
    public int bgColor;
}
