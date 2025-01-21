package net.ironingot.horseinforeloaded.fabric.render_state.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.render_state.HorseWithInfoRenderState;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel.LayerType;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HorseWithInfoArmorLayer extends RenderLayer<HorseWithInfoRenderState, HorseModel> {
    private final HorseModel adultModel;
    private final HorseModel babyModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public HorseWithInfoArmorLayer(RenderLayerParent<HorseWithInfoRenderState, HorseModel> renderLayerParent, EntityModelSet modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderLayerParent);
        this.equipmentRenderer = equipmentRenderer;
        this.adultModel = new HorseModel(modelSet.bakeLayer(ModelLayers.HORSE_ARMOR));
        this.babyModel = new HorseModel(modelSet.bakeLayer(ModelLayers.HORSE_BABY_ARMOR));
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, HorseWithInfoRenderState horseRenderState, float f, float g) {
        ItemStack itemstack = horseRenderState.bodyArmorItem;
        Equippable equippable = (Equippable)itemstack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.model().isPresent()) {
            HorseModel horsemodel = horseRenderState.isBaby ? this.babyModel : this.adultModel;
            ResourceLocation resourcelocation = (ResourceLocation)equippable.model().get();
            horsemodel.setupAnim(horseRenderState);
            this.equipmentRenderer.renderLayers(LayerType.HORSE_BODY, resourcelocation, horsemodel, itemstack, poseStack, multiBufferSource, i);
        }

    }
}
