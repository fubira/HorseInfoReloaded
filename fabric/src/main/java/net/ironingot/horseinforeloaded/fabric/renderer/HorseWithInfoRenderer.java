package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

import com.google.common.collect.Maps;

public class HorseWithInfoRenderer extends AbstractHorseRenderer<Horse, HorseRenderState, HorseModel> {
    private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), map -> {
        map.put(Variant.WHITE, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_white.png"));
        map.put(Variant.CREAMY, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_creamy.png"));
        map.put(Variant.CHESTNUT, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_chestnut.png"));
        map.put(Variant.BROWN, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_brown.png"));
        map.put(Variant.BLACK, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_black.png"));
        map.put(Variant.GRAY, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_gray.png"));
        map.put(Variant.DARK_BROWN, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel(context.bakeLayer(ModelLayers.HORSE)), new HorseModel(context.bakeLayer(ModelLayers.HORSE_BABY)));

        this.addLayer(new HorseMarkingLayer(this));
        this.addLayer(new SimpleEquipmentLayer<>(
            this,
            context.getEquipmentRenderer(),
            EquipmentClientInfo.LayerType.HORSE_BODY,
            renderState -> renderState.bodyArmorItem,
            new HorseModel(context.bakeLayer(ModelLayers.HORSE_ARMOR)),
            new HorseModel(context.bakeLayer(ModelLayers.HORSE_BABY_ARMOR))
        ));
        this.addLayer(new SimpleEquipmentLayer<>(
            this,
            context.getEquipmentRenderer(),
            EquipmentClientInfo.LayerType.HORSE_SADDLE,
            renderState -> renderState.saddle,
            new HorseModel(context.bakeLayer(ModelLayers.HORSE_SADDLE)),
            new HorseModel(context.bakeLayer(ModelLayers.HORSE_BABY_SADDLE))
        ));
    }

    @Override
    public ResourceLocation getTextureLocation(HorseRenderState renderState) {
        return (ResourceLocation)LOCATION_BY_VARIANT.get(renderState.variant);
    }

    @Override
    public HorseRenderState createRenderState() {
        return new HorseRenderState();
    }

    @Override
    public void extractRenderState(Horse entity, HorseRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        renderState.variant = entity.getVariant();
        renderState.markings = entity.getMarkings();
        renderState.bodyArmorItem = entity.getBodyArmorItem().copy();
    }

    public void render(Horse entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        // super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

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
