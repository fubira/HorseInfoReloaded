package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.CatWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.client.model.CatModel;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.Cat;

@OnlyIn(Dist.CLIENT)
public class CatWithInfoRenderer extends CatRenderer
{
    public CatWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);
    }

    @Override
    public CatRenderState createRenderState() {
        return new CatWithInfoRenderState();
    }

    @Override
    public void extractRenderState(Cat entity, CatRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        CatWithInfoRenderState withInfoRenderState = (CatWithInfoRenderState) renderState;

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

        withInfoRenderState.infoStrings = infoString;
        withInfoRenderState.nameTagAttachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot());
    }

    public void onPostRenderInfo(RenderLivingEvent.Post<Cat, CatRenderState, CatModel> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(event.getRenderState() instanceof CatWithInfoRenderState)) {
            return;
        }

        CatWithInfoRenderState renderState = (CatWithInfoRenderState) event.getRenderState();
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
