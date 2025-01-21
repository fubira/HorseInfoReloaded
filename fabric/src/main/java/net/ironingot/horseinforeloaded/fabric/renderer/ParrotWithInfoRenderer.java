package net.ironingot.horseinforeloaded.fabric.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.render_state.ParrotWithInfoRenderState;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Parrot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public class ParrotWithInfoRenderer extends MobRenderer<Parrot, ParrotWithInfoRenderState, ParrotModel>
{
    private static final ResourceLocation RED_BLUE = ResourceLocation.withDefaultNamespace("textures/entity/parrot/parrot_red_blue.png");
    private static final ResourceLocation BLUE = ResourceLocation.withDefaultNamespace("textures/entity/parrot/parrot_blue.png");
    private static final ResourceLocation GREEN = ResourceLocation.withDefaultNamespace("textures/entity/parrot/parrot_green.png");
    private static final ResourceLocation YELLOW_BLUE = ResourceLocation.withDefaultNamespace("textures/entity/parrot/parrot_yellow_blue.png");
    private static final ResourceLocation GREY = ResourceLocation.withDefaultNamespace("textures/entity/parrot/parrot_grey.png");

    public ParrotWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new ParrotModel(context.bakeLayer(ModelLayers.PARROT)), 0.3F);
    }

    public @NotNull ResourceLocation getTextureLocation(ParrotWithInfoRenderState renderState) {
        return getVariantTexture(renderState.variant);
    }

    public @NotNull ParrotWithInfoRenderState createRenderState() {
        return new ParrotWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Parrot entity, @NotNull ParrotWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.variant = entity.getVariant();
        float f = Mth.lerp(partialTick, entity.oFlap, entity.flap);
        float f1 = Mth.lerp(partialTick, entity.oFlapSpeed, entity.flapSpeed);
        renderState.flapAngle = (Mth.sin(f) + 1.0F) * f1;
        renderState.pose = ParrotModel.getPose(entity);

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getOwnerString(entity);
        renderState.isTame = entity.isTame();
    }

    public static ResourceLocation getVariantTexture(Parrot.Variant variant) {
        ResourceLocation location;
        switch (variant) {
            case RED_BLUE -> location = RED_BLUE;
            case BLUE -> location = BLUE;
            case GREEN -> location = GREEN;
            case YELLOW_BLUE -> location = YELLOW_BLUE;
            case GRAY -> location = GREY;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return location;
    }

    @Override
    public void render(@NotNull ParrotWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(renderState.displayName);
        if (renderState.isTame) {
            infoString.add(renderState.owner);
        }

        infoString.add("Variant: " + renderState.variant.toString());

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
