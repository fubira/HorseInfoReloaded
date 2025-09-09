package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.renderer.state.CatWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.Cat;

public class CatWithInfoRenderer extends CatRenderer
{
    public CatWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
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

    @Override
    public void render(CatRenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(state, poseStack, bufferSource, packedLight);
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(state instanceof CatWithInfoRenderState)) {
            return;
        }

        CatWithInfoRenderState renderState = (CatWithInfoRenderState) state;
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
