package net.ironingot.horseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.EnumMap;
import java.util.Map;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.client.renderer.entity.layers.LeatherHorseArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.passive.horse.CoatColors;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RenderHorseExtra extends AbstractHorseRenderer<HorseEntity, HorseModel<HorseEntity>> {
    private static Logger logger = LogManager.getLogger();

    private static final Map<CoatColors, ResourceLocation> textureMap = (Map)Util.make(Maps.newEnumMap(CoatColors.class), map -> {
        map.put(CoatColors.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
        map.put(CoatColors.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
        map.put(CoatColors.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
        map.put(CoatColors.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
        map.put(CoatColors.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
        map.put(CoatColors.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
        map.put(CoatColors.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
    });

    public RenderHorseExtra(EntityRendererManager renderManager) {
        super(renderManager, new HorseModel<HorseEntity>(0.0F), 1.1F);
        addLayer(new LeatherHorseArmorLayer(this));
    }

    public ResourceLocation getEntityTexture(HorseEntity entity) {
        return (ResourceLocation)textureMap.get(entity.func_234239_eK_());
    }

    @Override
    public void render(HorseEntity entity, float yaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (HorseInfoMod.isActive()) {
            List<String> stringInfo = new ArrayList<String>();
            stringInfo.add(HorseInfoUtil.getDisplayNameWithRank(entity));
            stringInfo.addAll(HorseInfoUtil.getHorseInfoString(entity));

            String stringAgeOrOwner = HorseInfoUtil.getAgeOrOwnerString(entity);
            if (stringAgeOrOwner != null)
                stringInfo.add(stringAgeOrOwner);

            RenderUtil.renderEntityInfo(
                renderManager,
                entity,
                stringInfo,
                matrixStackIn,
                bufferIn,
                packedLightIn
            );
        }
    }
}
