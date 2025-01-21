package net.ironingot.horseinforeloaded.neoforge.render_state.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.ironingot.horseinforeloaded.neoforge.render_state.WolfWithInfoRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Crackiness;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.Equippable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoArmorLayer extends RenderLayer<WolfWithInfoRenderState, WolfModel> {
    private final WolfModel adultModel;
    private final WolfModel babyModel;
    private final EquipmentLayerRenderer equipmentRenderer;
    private static final Map<Crackiness.Level, ResourceLocation> ARMOR_CRACK_LOCATIONS;

    public WolfWithInfoArmorLayer(RenderLayerParent<WolfWithInfoRenderState, WolfModel> renderLayerParent, EntityModelSet entityModelSet, EquipmentLayerRenderer equipmentLayerRenderer) {
        super(renderLayerParent);
        this.adultModel = new WolfModel(entityModelSet.bakeLayer(ModelLayers.WOLF_ARMOR));
        this.babyModel = new WolfModel(entityModelSet.bakeLayer(ModelLayers.WOLF_BABY_ARMOR));
        this.equipmentRenderer = equipmentLayerRenderer;
    }

    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i, WolfWithInfoRenderState wolfRenderState, float f, float g) {
        ItemStack itemStack = wolfRenderState.bodyArmorItem;
        Equippable equippable = (Equippable) itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable != null && !equippable.model().isEmpty()) {
            WolfModel wolfModel = wolfRenderState.isBaby ? this.babyModel : this.adultModel;
            ResourceLocation resourceLocation = (ResourceLocation) equippable.model().get();
            wolfModel.setupAnim(wolfRenderState);
            this.equipmentRenderer.renderLayers(EquipmentModel.LayerType.WOLF_BODY, resourceLocation, wolfModel, itemStack, poseStack, multiBufferSource, i);
            this.maybeRenderCracks(poseStack, multiBufferSource, i, itemStack, wolfModel);
        }
    }

    private void maybeRenderCracks(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ItemStack itemStack, Model model) {
        Crackiness.Level level = Crackiness.WOLF_ARMOR.byDamage(itemStack);
        if (level != Crackiness.Level.NONE) {
            ResourceLocation resourceLocation = (ResourceLocation) ARMOR_CRACK_LOCATIONS.get(level);
            VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.armorTranslucent(resourceLocation));
            model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        }
    }

    static {
        ARMOR_CRACK_LOCATIONS = Map.of(Crackiness.Level.LOW, ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_low.png"), Crackiness.Level.MEDIUM, ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_medium.png"), Crackiness.Level.HIGH, ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_armor_crackiness_high.png"));
    }
}