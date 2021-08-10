package net.ironingot.horseinfo.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Llama;

import net.ironingot.horseinfo.HorseInfoMod;
import net.ironingot.horseinfo.utils.EntityInfoUtil;
import net.ironingot.horseinfo.utils.RenderUtil;

@OnlyIn(Dist.CLIENT)
public class LlamaWithInfoRenderer extends LlamaRenderer
{
    public LlamaWithInfoRenderer(EntityRendererProvider.Context context, ModelLayerLocation location)
    {
        super(context, location);
    }

    @Override
    public void render(Llama entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityInfoUtil.getDisplayNameString(entity));
        infoString.add(EntityInfoUtil.getOwnerString(entity));

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
