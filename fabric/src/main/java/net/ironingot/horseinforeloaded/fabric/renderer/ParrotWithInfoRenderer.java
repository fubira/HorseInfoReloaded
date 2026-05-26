package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.renderer.state.ParrotWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.animal.parrot.Parrot;

public class ParrotWithInfoRenderer extends ParrotRenderer
{
    public ParrotWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
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

    @Override
    public void submit(ParrotRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        super.submit(state, poseStack, submitNodeCollector, cameraRenderState);
        if (!HorseInfoMod.isActive()) {
            return;
        }

        if (!(state instanceof ParrotWithInfoRenderState)) {
            return;
        }

        ParrotWithInfoRenderState renderState = (ParrotWithInfoRenderState) state;
        RenderUtil.RenderInfoString(
            poseStack,
            submitNodeCollector,
            cameraRenderState,
            renderState.lightCoords,
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
