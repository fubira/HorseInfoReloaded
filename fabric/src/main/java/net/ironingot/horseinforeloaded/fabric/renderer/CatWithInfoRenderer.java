package net.ironingot.horseinforeloaded.fabric.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.render_state.CatWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.render_state.layer.CatWithInfoCollarLayer;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class CatWithInfoRenderer extends AgeableMobRenderer<Cat, CatWithInfoRenderState, CatModel>
{
    public CatWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new CatModel(context.bakeLayer(ModelLayers.CAT)), new CatModel(context.bakeLayer(ModelLayers.CAT_BABY)), 0.4F);
        this.addLayer(new CatWithInfoCollarLayer(this, context.getModelSet()));
    }

    public @NotNull ResourceLocation getTextureLocation(CatWithInfoRenderState renderState) {
        return renderState.texture;
    }

    public @NotNull CatWithInfoRenderState createRenderState() {
        return new CatWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Cat entity, @NotNull CatWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.texture = entity.getVariant().value().texture();
        renderState.isCrouching = entity.isCrouching();
        renderState.isSprinting = entity.isSprinting();
        renderState.isSitting = entity.isInSittingPose();
        renderState.lieDownAmount = entity.getLieDownAmount(partialTick);
        renderState.lieDownAmountTail = entity.getLieDownAmountTail(partialTick);
        renderState.relaxStateOneAmount = entity.getRelaxStateOneAmount(partialTick);
        renderState.isLyingOnTopOfSleepingPlayer = entity.isLyingOnTopOfSleepingPlayer();
        renderState.collarColor = entity.isTame() ? entity.getCollarColor() : null;

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getOwnerString(entity);
        renderState.isTame = entity.isTame();
    }

    protected void setupRotations(@NotNull CatWithInfoRenderState renderState, @NotNull PoseStack poseStack, float g, float h) {
        super.setupRotations(renderState, poseStack, g, h);
        float f = renderState.lieDownAmount;
        if (f > 0.0F) {
            poseStack.translate(0.4F * f, 0.15F * f, 0.1F * f);
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp(f, 0.0F, 90.0F)));
            if (renderState.isLyingOnTopOfSleepingPlayer) {
                poseStack.translate(0.15F * f, 0.0F, 0.0F);
            }
        }

    }

    @Override
    public void render(@NotNull CatWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(renderState.displayName);
        if (renderState.isTame) {
            infoString.add(renderState.owner);
        }

        RenderUtil.renderEntityInfo(
            null,
            renderState,
            infoString,
            matrixStackIn,
            bufferIn,
            packedLightIn
        );
    }
}
