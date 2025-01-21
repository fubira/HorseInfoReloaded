package net.ironingot.horseinforeloaded.fabric.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.render_state.CamelWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.camel.Camel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class CamelWithInfoRenderer extends AgeableMobRenderer<Camel, CamelWithInfoRenderState, CamelModel>
{
    private static final ResourceLocation CAMEL_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/camel/camel.png");

    public CamelWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new CamelModel(context.bakeLayer(ModelLayers.CAMEL)), new CamelModel(context.bakeLayer(ModelLayers.CAMEL_BABY)), 0.7F);
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull CamelWithInfoRenderState state) {
        return CAMEL_LOCATION;
    }

    public @NotNull CamelWithInfoRenderState createRenderState() {
        return new CamelWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Camel entity, @NotNull CamelWithInfoRenderState renderState, float partialTick) {
        // Set standard camel info
        super.extractRenderState(entity, renderState, partialTick);
        renderState.isSaddled = entity.isSaddled();
        renderState.isRidden = entity.isVehicle();
        renderState.jumpCooldown = Math.max((float)entity.getJumpCooldown() - partialTick, 0.0F);
        renderState.sitAnimationState.copyFrom(entity.sitAnimationState);
        renderState.sitPoseAnimationState.copyFrom(entity.sitPoseAnimationState);
        renderState.sitUpAnimationState.copyFrom(entity.sitUpAnimationState);
        renderState.idleAnimationState.copyFrom(entity.idleAnimationState);
        renderState.dashAnimationState.copyFrom(entity.dashAnimationState);

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getAgeOrOwnerString(entity);
        renderState.isTamed = entity.isTamed();
        renderState.rider = EntityUtil.getRider(entity);
    }

    @Override
    public void render(@NotNull CamelWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(renderState.displayName);
        if (renderState.isTamed || renderState.isBaby) {
            infoString.add(renderState.owner);
        }

        RenderUtil.renderEntityInfo(
            renderState.rider,
            renderState,
            infoString,
            matrixStackIn,
            bufferIn,
            packedLightIn
        );
    }
}
