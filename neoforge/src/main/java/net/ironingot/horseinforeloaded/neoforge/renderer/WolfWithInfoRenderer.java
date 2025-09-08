package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.WolfWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.client.model.WolfModel;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.wolf.Wolf;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoRenderer extends WolfRenderer
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);
    }

    @Override
    public WolfRenderState createRenderState() {
        return new WolfWithInfoRenderState();
    }

    @Override
    public void extractRenderState(Wolf entity, WolfRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        WolfWithInfoRenderState withInfoRenderState = (WolfWithInfoRenderState) renderState;

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

        withInfoRenderState.infoStrings = infoString;
        withInfoRenderState.nameTagAttachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot());
    }

    public void onPostRenderInfo(RenderLivingEvent.Post<Wolf, WolfRenderState, WolfModel> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(event.getRenderState() instanceof WolfWithInfoRenderState)) {
            return;
        }

        WolfWithInfoRenderState renderState = (WolfWithInfoRenderState) event.getRenderState();
        RenderUtil.RenderInfoString(
            event.getPoseStack(),
            event.getMultiBufferSource(),
            event.getPackedLight(),
            renderState.distanceToCameraSq,
            renderState.nameTagAttachment,
            false,
            ARGB.color(224, 224, 224),
            ARGB.color(224, 224, 224),
            ARGB.color(0.4F, 0),
            renderState.infoStrings
        );
    }
}
