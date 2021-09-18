package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseArmorLayer;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

import com.google.common.collect.Maps;

public class HorseWithInfoRenderer extends AbstractHorseRenderer<Horse, HorseModel<Horse>> {
    private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), map -> {
        map.put(Variant.WHITE, new ResourceLocation("textures/entity/horse/horse_white.png"));
        map.put(Variant.CREAMY, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
        map.put(Variant.CHESTNUT, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
        map.put(Variant.BROWN, new ResourceLocation("textures/entity/horse/horse_brown.png"));
        map.put(Variant.BLACK, new ResourceLocation("textures/entity/horse/horse_black.png"));
        map.put(Variant.GRAY, new ResourceLocation("textures/entity/horse/horse_gray.png"));
        map.put(Variant.DARKBROWN, new ResourceLocation("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel<Horse>(context.bakeLayer(ModelLayers.HORSE)), 1.1F);
        addLayer(new HorseMarkingLayer(this));
        addLayer(new HorseArmorLayer(this, context.getModelSet()));
    }

    public ResourceLocation getTextureLocation(Horse entity) {
        return (ResourceLocation)LOCATION_BY_VARIANT.get(entity.getVariant());
    }

    @Override
    public void render(Horse entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameWithRank(entity));

        List<String> statsString = EntityUtil.getHorseStatsString(entity);
        if (statsString != null) {
            infoString.addAll(statsString);
        }

        String stringAgeOrOwner = EntityUtil.getAgeOrOwnerString(entity);
        if (stringAgeOrOwner != null) {
            infoString.add(stringAgeOrOwner);
        }

        RenderUtil.renderEntityInfo(
            entity,
            infoString,
            matrixStackIn,
            bufferIn,
            packedLightIn
        );
    }
}
