package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.DonkeyWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.DonkeyModel;
import net.minecraft.client.renderer.entity.DonkeyRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.DonkeyRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;

public class DonkeyWithInfoRenderer extends DonkeyRenderer<Donkey> {
    public DonkeyWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, DonkeyRenderer.Type.DONKEY);
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);
    }

    @Override
    public DonkeyWithInfoRenderState createRenderState() {
        return new DonkeyWithInfoRenderState();
    }

    @Override
    public void extractRenderState(Donkey entity, DonkeyRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);

        DonkeyWithInfoRenderState withInfoRenderState = (DonkeyWithInfoRenderState) renderState;
        withInfoRenderState.hasChest = entity.hasChest();

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

    public void onPostRenderInfo(RenderLivingEvent.Post<Donkey, DonkeyRenderState, DonkeyModel> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(event.getRenderState() instanceof DonkeyWithInfoRenderState)) {
            return;
        }

        DonkeyWithInfoRenderState renderState = (DonkeyWithInfoRenderState) event.getRenderState();

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
