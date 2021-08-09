package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.Arrays;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.Cat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderCatExtra extends CatRenderer
{
    private static Logger logger = LogManager.getLogger();

    public RenderCatExtra(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Cat entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        if (HorseInfoMod.isActive())
        {
            String stringName = AnimalInfoUtil.getDisplayName(entity);
            String stringOwner = AnimalInfoUtil.getOwner(entity);

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
