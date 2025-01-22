package net.ironingot.horseinforeloaded.neoforge.render_state.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ironingot.horseinforeloaded.neoforge.render_state.CatWithInfoRenderState;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class CatWithInfoCollarLayer extends RenderLayer<CatWithInfoRenderState, CatModel> {
    private static final ResourceLocation CAT_COLLAR_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/cat/cat_collar.png");
    private final CatModel adultModel;
    private final CatModel babyModel;

    public CatWithInfoCollarLayer(RenderLayerParent<CatWithInfoRenderState, CatModel> parent, EntityModelSet modelSet) {
        super(parent);
        this.adultModel = new CatModel(modelSet.bakeLayer(ModelLayers.CAT_COLLAR));
        this.babyModel = new CatModel(modelSet.bakeLayer(ModelLayers.CAT_BABY_COLLAR));
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int i, CatWithInfoRenderState renderState, float g, float h) {
        DyeColor dyecolor = renderState.collarColor;
        if (dyecolor != null) {
            int c = dyecolor.getTextureDiffuseColor();
            CatModel catmodel = renderState.isBaby ? this.babyModel : this.adultModel;
            coloredCutoutModelCopyLayerRender(catmodel, CAT_COLLAR_LOCATION, poseStack, bufferSource, i, renderState, c);
        }
    }
}
