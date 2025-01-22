package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ironingot.horseinforeloaded.neoforge.render_state.layer.HorseWithInfoArmorLayer;
import net.ironingot.horseinforeloaded.neoforge.render_state.layer.HorseWithInfoMarkingLayer;
import net.ironingot.horseinforeloaded.neoforge.render_state.HorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;

import com.google.common.collect.Maps;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class HorseWithInfoRenderer extends AbstractHorseRenderer<Horse, HorseWithInfoRenderState, HorseModel> {
    private static final Map<Variant, ResourceLocation> LOCATION_BY_VARIANT = Util.make(Maps.newEnumMap(Variant.class), map -> {
        map.put(Variant.WHITE, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_white.png"));
        map.put(Variant.CREAMY, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_creamy.png"));
        map.put(Variant.CHESTNUT, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_chestnut.png"));
        map.put(Variant.BROWN, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_brown.png"));
        map.put(Variant.BLACK, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_black.png"));
        map.put(Variant.GRAY, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_gray.png"));
        map.put(Variant.DARK_BROWN, ResourceLocation.withDefaultNamespace("textures/entity/horse/horse_darkbrown.png"));
    });

    public HorseWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context, new HorseModel(context.bakeLayer(ModelLayers.HORSE)), new HorseModel(context.bakeLayer(ModelLayers.HORSE_BABY)), 1.1F);
        addLayer(new HorseWithInfoMarkingLayer(this));
        addLayer(new HorseWithInfoArmorLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(HorseWithInfoRenderState renderState) {
        return (ResourceLocation)LOCATION_BY_VARIANT.get(renderState.variant);
    }

    public @NotNull HorseWithInfoRenderState createRenderState() {
        return new HorseWithInfoRenderState();
    }

    public void extractRenderState(@NotNull Horse entity, @NotNull HorseWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.variant = entity.getVariant();
        renderState.markings = entity.getMarkings();
        renderState.bodyArmorItem = entity.getBodyArmorItem().copy();

        // Set our own variables
        renderState.displayName = EntityUtil.getDisplayNameString(entity);
        renderState.owner = EntityUtil.getAgeOrOwnerString(entity);
        renderState.rider = EntityUtil.getRider(entity);

        renderState.speed = HorseEntityUtil.getSpeed(entity);
        renderState.jumpHeight = HorseEntityUtil.getJumpHeight(entity);
        renderState.jumpStrength = HorseEntityUtil.getJumpStrength(entity);
        renderState.health = entity.getHealth();
        renderState.maxHealth = entity.getMaxHealth();

        renderState.isTamed = entity.isTamed();
    }

    @Override
    public void render(@NotNull HorseWithInfoRenderState renderState, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
        super.render(renderState, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<>();
        infoString.add(EntityUtil.getDisplayNameWithRank(renderState));
        if (renderState.isTamed || renderState.isBaby) {
            infoString.add(renderState.owner);
        }

        infoString.add("Variant: " + renderState.variant.toString());

        List<String> statsString = EntityUtil.getHorseStatsString(renderState);
        if (statsString != null) {
            infoString.addAll(statsString);
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
