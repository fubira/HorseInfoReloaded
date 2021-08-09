package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Llama;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderLlamaExtra extends LlamaRenderer
{
    private static Logger logger = LogManager.getLogger();

    public RenderLlamaExtra(EntityRendererProvider.Context context, ModelLayerLocation location)
    {
        super(context, location);
    }

    @Override
    public void render(Llama entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (HorseInfoMod.isActive())
        {
            String stringName = HorseInfoUtil.getDisplayName(entity);
            String stringOwner = HorseInfoUtil.getOwner(entity);
            logger.debug(stringName + ":" + stringOwner);

            if (stringOwner != null)
            {
                RenderUtil.renderEntityInfo(
                    entity,
                    new ArrayList<String>(Arrays.asList(stringName, stringOwner)),
                    matrixStackIn,
                    bufferIn,
                    packedLightIn
                );
            }
        }
    }
}
