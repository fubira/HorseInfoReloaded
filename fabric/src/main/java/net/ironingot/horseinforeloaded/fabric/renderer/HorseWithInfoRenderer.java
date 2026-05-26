package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.animal.equine.BabyHorseModel;
import net.minecraft.client.model.animal.equine.EquineSaddleModel;
import net.minecraft.client.model.animal.equine.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.client.renderer.entity.state.HorseRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Variant;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.renderer.state.HorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

public class HorseWithInfoRenderer extends AbstractHorseRenderer<Horse, HorseRenderState, HorseModel> {
    private static final Map<Variant, Identifier> LOCATION_BY_VARIANT = Map.of(
        Variant.WHITE, Identifier.withDefaultNamespace("textures/entity/horse/horse_white.png"),
        Variant.CREAMY, Identifier.withDefaultNamespace("textures/entity/horse/horse_creamy.png"),
        Variant.CHESTNUT, Identifier.withDefaultNamespace("textures/entity/horse/horse_chestnut.png"),
        Variant.BROWN, Identifier.withDefaultNamespace("textures/entity/horse/horse_brown.png"),
        Variant.BLACK, Identifier.withDefaultNamespace("textures/entity/horse/horse_black.png"),
        Variant.GRAY, Identifier.withDefaultNamespace("textures/entity/horse/horse_gray.png"),
        Variant.DARK_BROWN, Identifier.withDefaultNamespace("textures/entity/horse/horse_darkbrown.png")
    );

    public HorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel(context.bakeLayer(ModelLayers.HORSE)), new BabyHorseModel(context.bakeLayer(ModelLayers.HORSE_BABY)));

        this.addLayer(new HorseMarkingLayer(this));
        this.addLayer(new SimpleEquipmentLayer<>(
            this,
            context.getEquipmentRenderer(),
            EquipmentClientInfo.LayerType.HORSE_BODY,
            renderState -> renderState.bodyArmorItem,
            new HorseModel(context.bakeLayer(ModelLayers.HORSE_ARMOR)),
            null,
            2
        ));
        this.addLayer(new SimpleEquipmentLayer<>(
            this,
            context.getEquipmentRenderer(),
            EquipmentClientInfo.LayerType.HORSE_SADDLE,
            renderState -> renderState.saddle,
            new EquineSaddleModel(context.bakeLayer(ModelLayers.HORSE_SADDLE)),
            null,
            2
        ));
    }

    @Override
    public Identifier getTextureLocation(HorseRenderState renderState) {
        return (Identifier)LOCATION_BY_VARIANT.get(renderState.variant);
    }

    @Override
    public HorseRenderState createRenderState() {
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

    @Override
    public void submit(HorseRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(state instanceof HorseWithInfoRenderState)) {
            return;
        }

        HorseWithInfoRenderState renderState = (HorseWithInfoRenderState) state;
        RenderUtil.RenderInfoString(
            poseStack,
            submitNodeCollector,
            cameraRenderState,
            renderState.lightCoords,
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
