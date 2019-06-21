package net.ironingot.horseinfo;

import java.util.Arrays;
import java.util.ArrayList;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityOcelot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderOcelotExtra extends RenderOcelot
{
    private static Logger logger = LogManager.getLogger();

    public RenderOcelotExtra(RenderManager renderManager)
    {
        super(renderManager);
    }

    @Override
    public void doRender(EntityOcelot entity, double x, double y, double z, float yaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, yaw, partialTicks);

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
                    x, y, z,
                    new ArrayList<String>(Arrays.asList(stringName, stringOwner)));
            }
        }
    }
}
