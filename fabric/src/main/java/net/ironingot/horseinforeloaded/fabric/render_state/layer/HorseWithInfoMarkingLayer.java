package net.ironingot.horseinforeloaded.fabric.render_state.layer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.render_state.HorseWithInfoRenderState;
import net.minecraft.Util;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Markings;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class HorseWithInfoMarkingLayer extends RenderLayer<HorseWithInfoRenderState, HorseModel> {
    private static final Map<Markings, ResourceLocation> LOCATION_BY_MARKINGS = Util.make(Maps.newEnumMap(Markings.class), (enumMap) -> {
        enumMap.put(Markings.NONE, null);
        enumMap.put(Markings.WHITE, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_markings_white.png"));
        enumMap.put(Markings.WHITE_FIELD, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_markings_whitefield.png"));
        enumMap.put(Markings.WHITE_DOTS, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_markings_whitedots.png"));
        enumMap.put(Markings.BLACK_DOTS, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_markings_blackdots.png"));
    });

    public HorseWithInfoMarkingLayer(RenderLayerParent<HorseWithInfoRenderState, HorseModel> renderLayerParent) {
        super(renderLayerParent);
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, HorseWithInfoRenderState horseRenderState, float f, float g) {
        ResourceLocation resourceLocation = (ResourceLocation)LOCATION_BY_MARKINGS.get(horseRenderState.markings);
        if (resourceLocation != null && !horseRenderState.isInvisible) {
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityTranslucent(resourceLocation));
            ((HorseModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(horseRenderState, 0.0F));
        }
    }
}
