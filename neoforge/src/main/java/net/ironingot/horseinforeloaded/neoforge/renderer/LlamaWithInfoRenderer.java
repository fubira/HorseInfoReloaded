package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.render_state.layer.LlamaWithInfoDecorLayer;
import net.ironingot.horseinforeloaded.neoforge.render_state.LlamaWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Llama;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LlamaWithInfoRenderer extends AgeableMobRenderer<Llama, LlamaWithInfoRenderState, LlamaModel>
{
    private static final ResourceLocation CREAMY = ResourceLocation.withDefaultNamespace("textures/entity/llama/creamy.png");
    private static final ResourceLocation WHITE = ResourceLocation.withDefaultNamespace("textures/entity/llama/white.png");
    private static final ResourceLocation BROWN = ResourceLocation.withDefaultNamespace("textures/entity/llama/brown.png");
    private static final ResourceLocation GRAY = ResourceLocation.withDefaultNamespace("textures/entity/llama/gray.png");

    public LlamaWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context, new LlamaModel(context.bakeLayer(ModelLayers.LLAMA)), new LlamaModel(context.bakeLayer(ModelLayers.LLAMA_BABY)), 0.7F);
        this.addLayer(new LlamaWithInfoDecorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    public @NotNull ResourceLocation getTextureLocation(LlamaWithInfoRenderState renderState) {
        ResourceLocation location;
        switch (renderState.variant) {
            case CREAMY -> location = CREAMY;
            case WHITE -> location = WHITE;
            case BROWN -> location = BROWN;
            case GRAY -> location = GRAY;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        return location;
    }

    public @NotNull LlamaWithInfoRenderState createRenderState() {
        return new LlamaWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Llama entity, @NotNull LlamaWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.variant = entity.getVariant();
        renderState.hasChest = !entity.isBaby() && entity.hasChest();
        renderState.bodyItem = entity.getBodyArmorItem();
        renderState.isTraderLlama = entity.isTraderLlama();

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getAgeOrOwnerString(entity);
        renderState.isTamed = entity.isTamed();
        renderState.strength = entity.getStrength();
    }

    @Override
    public void render(@NotNull LlamaWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(renderState.displayName);
        if (renderState.isTamed || renderState.isBaby) {
            infoString.add(renderState.owner);
        }
        infoString.add("Variant: " + renderState.variant.toString());
        infoString.add(String.format("Strength: %d", renderState.strength));

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
