package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.RenderLlama;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityLlama;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderLlamaExtra extends RenderLlama
{
    private static Logger logger = LogManager.getLogger();

    public RenderLlamaExtra(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(EntityLlama entity, double x, double y, double z, float yaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, yaw, partialTicks);

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
                    x, y, z,
                    new ArrayList<String>(Arrays.asList(stringName, stringOwner)));
            }

        }
    }
}
