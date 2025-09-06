package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.wolf.Wolf;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoRenderer extends WolfRenderer
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(WolfRenderState renderState, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

        if (entity.isTame()) {
            RenderUtil.renderEntityInfo(
                entity,
                infoString,
                matrixStackIn,
                bufferIn,
                packedLightIn
            );
        }
    }
}
