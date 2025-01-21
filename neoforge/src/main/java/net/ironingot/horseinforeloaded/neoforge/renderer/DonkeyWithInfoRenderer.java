package net.ironingot.horseinforeloaded.neoforge.renderer;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.ironingot.horseinforeloaded.neoforge.HorseInfoMod;
import net.ironingot.horseinforeloaded.neoforge.render_state.HorseWithInfoRenderState;
import net.ironingot.horseinforeloaded.neoforge.render_state.model.DonkeyWithInfoModel;
import net.ironingot.horseinforeloaded.neoforge.utils.EntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.HorseEntityUtil;
import net.ironingot.horseinforeloaded.neoforge.utils.RenderUtil;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DonkeyWithInfoRenderer<T extends AbstractChestedHorse> extends AbstractHorseRenderer<T, HorseWithInfoRenderState, DonkeyWithInfoModel> {
    public static final ResourceLocation DONKEY_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/horse/donkey.png");
    public static final ResourceLocation MULE_TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/horse/mule.png");
    private final ResourceLocation texture;

    public DonkeyWithInfoRenderer(EntityRendererProvider.Context context, float f, ModelLayerLocation loc, ModelLayerLocation loc_baby, boolean mule) {
        super(context, new DonkeyWithInfoModel(context.bakeLayer(loc)), new DonkeyWithInfoModel(context.bakeLayer(loc_baby)), f);
        this.texture = mule ? MULE_TEXTURE : DONKEY_TEXTURE;
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull HorseWithInfoRenderState renderState) {
        return this.texture;
    }

    public @NotNull HorseWithInfoRenderState createRenderState() {
        return new HorseWithInfoRenderState();
    }

    public void extractRenderState(@NotNull T entity, @NotNull HorseWithInfoRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.hasChest = entity.hasChest();

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
