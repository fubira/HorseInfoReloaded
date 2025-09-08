package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;

import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.renderer.state.ParrotWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.Parrot;

@OnlyIn(Dist.CLIENT)
public class ParrotWithInfoRenderer extends ParrotRenderer
{
    public ParrotWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
        NeoForge.EVENT_BUS.addListener(this::onPostRenderInfo);
    }

    @Override
    public ParrotRenderState createRenderState() {
        return new ParrotWithInfoRenderState();
    }

    @Override
    public void extractRenderState(Parrot entity, ParrotRenderState renderState, float partialTicks) {
        super.extractRenderState(entity, renderState, partialTicks);
        ParrotWithInfoRenderState withInfoRenderState = (ParrotWithInfoRenderState) renderState;

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

        withInfoRenderState.infoStrings = infoString;
        withInfoRenderState.nameTagAttachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getYRot());
    }

    public void onPostRenderInfo(RenderLivingEvent.Post<Parrot, ParrotRenderState, ParrotModel> event) {
        if (!HorseInfoMod.isActive()) {
            return;
        }
   
        if (!(event.getRenderState() instanceof ParrotWithInfoRenderState)) {
            return;
        }

        ParrotWithInfoRenderState renderState = (ParrotWithInfoRenderState) event.getRenderState();
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
