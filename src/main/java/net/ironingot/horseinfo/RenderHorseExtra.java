package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderHorseExtra extends AbstractHorseRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private static final Map<String, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();
    private static Logger logger = LogManager.getLogger();

    public RenderHorseExtra(EntityRendererManager renderManager) {
        super(renderManager, new HorseModel(0.0F), 1.1F);
        addLayer(new LeatherHorseArmorLayer(this));
    }

    protected ResourceLocation getEntityTexture(HorseEntity entity) {
        String texture = entity.getHorseTexture();
        ResourceLocation resourceLocation = (ResourceLocation) LAYERED_LOCATION_CACHE.get(texture);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(texture);
            Minecraft.getInstance().getTextureManager().loadTexture(resourceLocation, new LayeredTexture(entity.getVariantTexturePaths()));
            LAYERED_LOCATION_CACHE.put(texture, resourceLocation);
        }
        return resourceLocation;
    }

    @Override
    public void doRender(HorseEntity entity, double x, double y, double z, float yaw, float partialTicks) {
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
