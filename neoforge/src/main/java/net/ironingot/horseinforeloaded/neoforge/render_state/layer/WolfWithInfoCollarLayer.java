package net.ironingot.horseinforeloaded.neoforge.render_state.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ironingot.horseinforeloaded.neoforge.render_state.WolfWithInfoRenderState;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoCollarLayer extends RenderLayer<WolfWithInfoRenderState, WolfModel> {
    private static final ResourceLocation WOLF_COLLAR_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_collar.png");

    public WolfWithInfoCollarLayer(RenderLayerParent<WolfWithInfoRenderState, WolfModel> renderLayerParent) {
        super(renderLayerParent);
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, WolfWithInfoRenderState wolfRenderState, float f, float g) {
        DyeColor dyeColor = wolfRenderState.collarColor;
        if (dyeColor != null && !wolfRenderState.isInvisible) {
            int j = dyeColor.getTextureDiffuseColor();
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(WOLF_COLLAR_LOCATION));
            ((WolfModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, j);
        }
    }
}
