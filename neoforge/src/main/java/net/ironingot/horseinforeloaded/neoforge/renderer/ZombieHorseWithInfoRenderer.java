package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.ZombieHorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.equine.AbstractHorse;

public class ZombieHorseWithInfoRenderer extends UndeadHorseRenderer {
    public ZombieHorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, EquipmentClientInfo.LayerType.ZOMBIE_HORSE_SADDLE, ModelLayers.ZOMBIE_HORSE_SADDLE, UndeadHorseRenderer.Type.ZOMBIE, UndeadHorseRenderer.Type.ZOMBIE_BABY);
    }

    @Override
    public EquineRenderState createRenderState() {
        return new ZombieHorseWithInfoRenderState();
    }

    @Override
    public void extractRenderState(AbstractHorse entity, EquineRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);

        ZombieHorseWithInfoRenderState withInfoRenderState = (ZombieHorseWithInfoRenderState) renderState;

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
    public void submit(EquineRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(state instanceof ZombieHorseWithInfoRenderState)) {
            return;
        }

        ZombieHorseWithInfoRenderState renderState = (ZombieHorseWithInfoRenderState) state;
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
