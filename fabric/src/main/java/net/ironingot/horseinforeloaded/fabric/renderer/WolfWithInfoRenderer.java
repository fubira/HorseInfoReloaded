package net.ironingot.horseinforeloaded.fabric.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.render_state.WolfWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.render_state.layer.WolfWithInfoArmorLayer;
import net.ironingot.horseinforeloaded.fabric.render_state.layer.WolfWithInfoCollarLayer;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.WolfVariant;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class WolfWithInfoRenderer extends AgeableMobRenderer<Wolf, WolfWithInfoRenderState, WolfModel>
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context, new WolfModel(context.bakeLayer(ModelLayers.WOLF)), new WolfModel(context.bakeLayer(ModelLayers.WOLF_BABY)), 0.5F);
        addLayer(new WolfWithInfoArmorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
        addLayer(new WolfWithInfoCollarLayer(this));
    }

    protected int getModelTint(WolfRenderState p_365181_) {
        float f = p_365181_.wetShade;
        return f == 1.0F ? -1 : ARGB.colorFromFloat(1.0F, f, f, f);
    }

    public @NotNull ResourceLocation getTextureLocation(WolfWithInfoRenderState renderState) {
        return renderState.texture;
    }

    public @NotNull WolfWithInfoRenderState createRenderState() {
        return new WolfWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Wolf entity, @NotNull WolfWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.isAngry = entity.isAngry();
        renderState.isSitting = entity.isInSittingPose();
        renderState.tailAngle = entity.getTailAngle();
        renderState.headRollAngle = entity.getHeadRollAngle(partialTick);
        renderState.shakeAnim = entity.getShakeAnim(partialTick);
        renderState.texture = entity.getTexture();
        renderState.wetShade = entity.getWetShade(partialTick);
        renderState.collarColor = entity.isTame() ? entity.getCollarColor() : null;
        renderState.bodyArmorItem = entity.getBodyArmorItem().copy();

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getOwnerString(entity);
        renderState.isTame = entity.isTame();
    }

    @Override
    public void render(@NotNull WolfWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
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
