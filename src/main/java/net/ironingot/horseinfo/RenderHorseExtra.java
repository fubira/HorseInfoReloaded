package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.UsernameCache;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.passive.EntityHorse;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;

@SideOnly(Side.CLIENT)
public class RenderHorseExtra extends RenderHorse
{
    private static Logger logger = FMLLog.getLogger();

    public RenderHorseExtra(RenderManager renderManager)
    {
        super(renderManager, new ModelHorse(), 0.75f);
    }

    @Override
    public void doRender(EntityHorse entity, double x, double y, double z, float yaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, yaw, partialTicks);

        if (HorseInfoMod.isActive())
        {
            List<String> stringInfo = new ArrayList<String>();
            stringInfo.add(HorseInfoUtil.getDisplayNameWithRank(entity));
            stringInfo.addAll(HorseInfoUtil.getHorseInfoString(entity));

            String stringAgeOrOwner = HorseInfoUtil.getAgeOrOwnerString(entity);
            if (stringAgeOrOwner != null)
                stringInfo.add(stringAgeOrOwner);

            RenderUtil.renderEntityInfo(renderManager, getFontRendererFromRenderManager(), entity, x, y, z, stringInfo);
        }
    }
}
