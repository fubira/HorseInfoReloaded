package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.passive.WolfEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderWolfExtra extends WolfRenderer
{
    private static Logger logger = LogManager.getLogger();

    public RenderWolfExtra(EntityRendererManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void render(WolfEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (HorseInfoMod.isActive())
        {
            String stringName = AnimalInfoUtil.getDisplayName(entity);
            String stringOwner = AnimalInfoUtil.getOwner(entity);

            if (stringOwner != null)
            {
                RenderUtil.renderEntityInfo(
                    renderManager,
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
