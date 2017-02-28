package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderLlama;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityLlama;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;

@SideOnly(Side.CLIENT)
public class RenderLlamaExtra extends RenderLlama
{
    private static Logger logger = FMLLog.getLogger();

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
            List<String> stringInfo = new ArrayList<String>();
            stringInfo.add(HorseInfoUtil.getDisplayName(entity));

            String stringAgeOrOwner = HorseInfoUtil.getAgeOrOwnerString(entity);
            if (stringAgeOrOwner != null)
                stringInfo.add(stringAgeOrOwner);

            RenderUtil.renderEntityInfo(renderManager, getFontRendererFromRenderManager(), entity, x, y, z, stringInfo);
        }
    }
}
