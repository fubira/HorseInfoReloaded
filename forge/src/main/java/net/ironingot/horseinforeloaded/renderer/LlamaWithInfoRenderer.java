package net.ironingot.horseinforeloaded.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.ironingot.horseinforeloaded.HorseInfoMod;
import net.ironingot.horseinforeloaded.utils.EntityUtil;
import net.ironingot.horseinforeloaded.utils.RenderUtil;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Llama;

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
