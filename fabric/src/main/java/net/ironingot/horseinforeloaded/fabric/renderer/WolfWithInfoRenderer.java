package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.renderer.state.WolfWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.wolf.Wolf;

public class WolfWithInfoRenderer extends WolfRenderer
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
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

    @Override
    public void render(WolfRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(state, poseStack, bufferSource, packedLight);
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(state instanceof WolfWithInfoRenderState)) {
            return;
        }

        WolfWithInfoRenderState renderState = (WolfWithInfoRenderState) state;
        RenderUtil.RenderInfoString(
            poseStack,
            bufferSource,
            packedLight,
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
