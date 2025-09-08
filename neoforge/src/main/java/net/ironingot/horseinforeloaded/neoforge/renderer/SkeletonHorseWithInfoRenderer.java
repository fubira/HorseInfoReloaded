package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.SkeletonHorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.AbstractEquineModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;

public class SkeletonHorseWithInfoRenderer extends UndeadHorseRenderer {
    public SkeletonHorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, UndeadHorseRenderer.Type.SKELETON);
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);
    }

    @Override
    public EquineRenderState createRenderState() {
        return new SkeletonHorseWithInfoRenderState();
    }

    @Override
    public void extractRenderState(AbstractHorse entity, EquineRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);

        SkeletonHorseWithInfoRenderState withInfoRenderState = (SkeletonHorseWithInfoRenderState) renderState;

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

    public void onPostRenderInfo(RenderLivingEvent.Post<SkeletonHorse, EquineRenderState, AbstractEquineModel<EquineRenderState>> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }
   
        if (!(event.getRenderState() instanceof SkeletonHorseWithInfoRenderState)) {
            return;
        }

        SkeletonHorseWithInfoRenderState renderState = (SkeletonHorseWithInfoRenderState) event.getRenderState();
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
