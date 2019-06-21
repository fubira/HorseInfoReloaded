package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.RenderHorseChest;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.AbstractHorse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderHorseChestExtra extends RenderHorseChest {
    private static Logger logger = LogManager.getLogger();

    public RenderHorseChestExtra(RenderManager renderManager, float f) {
        super(renderManager, f);
    }

    @Override
    public void doRender(AbstractHorse entity, double x, double y, double z, float yaw, float partialTicks) {
        super.doRender(entity, x, y, z, yaw, partialTicks);

        if (HorseInfoMod.isActive()) {
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
