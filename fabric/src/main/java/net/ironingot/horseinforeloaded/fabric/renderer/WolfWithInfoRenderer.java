package net.ironingot.horseinforeloaded.fabric.renderer;
/*
import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.client.renderer.MultiBufferSource;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

public class WolfWithInfoRenderer extends WolfRenderer
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(WolfRenderState state, PoseStack matrixStackIn, MultiBufferSource bufferIn, int i) {
        super.render(state, matrixStackIn, bufferIn, i);

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
*/
