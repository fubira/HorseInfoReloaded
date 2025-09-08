package net.ironingot.horseinforeloaded.fabric.renderer;
/*
import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.CamelRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.camel.Camel;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

public class CamelWithInfoRenderer extends CamelRenderer
{
    public CamelWithInfoRenderer(EntityRendererProvider.Context context, ModelLayerLocation location)
    {
        super(context, location);
    }

    @Override
    public void render(Camel entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

        if (entity.isTamed()) {
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
