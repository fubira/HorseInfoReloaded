package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.HorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.Util;

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
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);

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
    public HorseWithInfoRenderState createRenderState() {
        return new HorseWithInfoRenderState();
    }

    @Override
    public void extractRenderState(Horse entity, HorseRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);

        HorseWithInfoRenderState withInfoRenderState = (HorseWithInfoRenderState) renderState;

        withInfoRenderState.variant = entity.getVariant();
        withInfoRenderState.markings = entity.getMarkings();
        withInfoRenderState.bodyArmorItem = entity.getBodyArmorItem().copy();

        ArrayList<String> infoString = new ArrayList<>();
        String nameWithRankString = EntityUtil.getDisplayNameWithRank(entity);
        List<String> statsStrings = HorseEntityUtil.getStatsStrings(entity);
        String ageString = EntityUtil.getAgeString(entity);
        LivingEntity owner = entity.getOwner();
        String ownerString = EntityUtil.getOwnerString(owner != null ? owner.getUUID() : null);

        infoString.add(nameWithRankString);
        infoString.addAll(statsStrings);
        if (ageString != null) {
            infoString.add(ageString);
        }
        if (ownerString != null) {
            infoString.add(ownerString);
        }
        withInfoRenderState.infoStrings = infoString;
        withInfoRenderState.nameTagAttachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot());

        int DEFAULT_FONT_COLOR = ARGB.color(224, 224, 224);
        int DEFAULT_BASE_COLOR = ARGB.color(0, 0, 0);
        int baseColor = RenderUtil.getLabelColor(entity);
        int titleColor = baseColor == DEFAULT_BASE_COLOR ? DEFAULT_FONT_COLOR : baseColor;
        int fontColor = DEFAULT_FONT_COLOR;
        int bgColor = ARGB.color(0.4F, baseColor);

        withInfoRenderState.titleColor = titleColor;
        withInfoRenderState.fontColor = fontColor;
        withInfoRenderState.bgColor = bgColor; 
    }

    public void onPostRenderInfo(RenderLivingEvent.Post<Horse, HorseRenderState, HorseModel> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }
   
        if (!(event.getRenderState() instanceof HorseWithInfoRenderState)) {
            return;
        }

        HorseWithInfoRenderState renderState = (HorseWithInfoRenderState) event.getRenderState();

        RenderUtil.RenderInfoString(
            event.getPoseStack(),
            event.getMultiBufferSource(),
            event.getPackedLight(),
            renderState.distanceToCameraSq,
            renderState.nameTagAttachment,
            renderState.isRidden,
            renderState.titleColor,
            renderState.fontColor,
            renderState.bgColor,
            renderState.infoStrings
        );
    }

}
