package net.ironingot.horseinforeloaded.neoforge.renderer.state;

import java.util.List;

import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonHorseWithInfoRenderState extends EquineRenderState {
    public List<String> infoStrings;
    public int titleColor;
    public int fontColor;
    public int bgColor;
}
