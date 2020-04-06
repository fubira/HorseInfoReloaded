package net.ironingot.horseinfo;

import java.util.Arrays;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.passive.CatEntity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderCatExtra extends CatRenderer
{
    private static Logger logger = LogManager.getLogger();

    public RenderCatExtra(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(CatEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (HorseInfoMod.isActive())
        {
            String stringName = AnimalInfoUtil.getDisplayName(entity);
            String stringOwner = AnimalInfoUtil.getOwner(entity);

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
