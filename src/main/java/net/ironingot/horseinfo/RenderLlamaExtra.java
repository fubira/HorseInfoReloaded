package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.LlamaRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.passive.horse.LlamaEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderLlamaExtra extends LlamaRenderer
{
    private static Logger logger = LogManager.getLogger();

    public RenderLlamaExtra(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void render(LlamaEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (HorseInfoMod.isActive())
        {
            String stringName = HorseInfoUtil.getDisplayName(entity);
            String stringOwner = HorseInfoUtil.getOwner(entity);

            if (stringOwner != null)
            {
                RenderUtil.renderEntityInfo(
                    renderManager,
                    getFontRendererFromRenderManager(),
                    entity,
                    new ArrayList<String>(Arrays.asList(stringName, stringOwner)));
            }

        }
    }
}
