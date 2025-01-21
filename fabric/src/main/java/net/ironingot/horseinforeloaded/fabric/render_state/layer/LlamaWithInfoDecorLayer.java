package net.ironingot.horseinforeloaded.fabric.render_state.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ironingot.horseinforeloaded.fabric.render_state.LlamaWithInfoRenderState;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.EquipmentModels;
import net.minecraft.world.item.equipment.Equippable;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class LlamaWithInfoDecorLayer extends RenderLayer<LlamaWithInfoRenderState, LlamaModel> {
    private final LlamaModel adultModel;
    private final LlamaModel babyModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public LlamaWithInfoDecorLayer(RenderLayerParent<LlamaWithInfoRenderState, LlamaModel> parent, EntityModelSet modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(parent);
        this.equipmentRenderer = equipmentRenderer;
        this.adultModel = new LlamaModel(modelSet.bakeLayer(ModelLayers.LLAMA_DECOR));
        this.babyModel = new LlamaModel(modelSet.bakeLayer(ModelLayers.LLAMA_BABY_DECOR));
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource source, int i, LlamaWithInfoRenderState renderState, float f, float g) {
        ItemStack itemstack = renderState.bodyItem;
        Equippable equippable = itemstack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && equippable.model().isPresent()) {
            this.renderEquipment(poseStack, source, renderState, itemstack, (ResourceLocation)equippable.model().get(), i);
        } else if (renderState.isTraderLlama) {
            this.renderEquipment(poseStack, source, renderState, ItemStack.EMPTY, EquipmentModels.TRADER_LLAMA, i);
        }

    }

    private void renderEquipment(PoseStack poseStack, MultiBufferSource source, LlamaWithInfoRenderState renderState, ItemStack itemStack, ResourceLocation resourceLocation, int i) {
        LlamaModel llamamodel = renderState.isBaby ? this.babyModel : this.adultModel;
        llamamodel.setupAnim(renderState);
        this.equipmentRenderer.renderLayers(EquipmentModel.LayerType.LLAMA_BODY, resourceLocation, llamamodel, itemStack, poseStack, source, i);
    }
}
