package net.ironingot.horseinforeloaded.fabric.renderer;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

import net.ironingot.horseinforeloaded.fabric.HorseInfoMod;
import net.ironingot.horseinforeloaded.fabric.utils.EntityUtil;
import net.ironingot.horseinforeloaded.fabric.utils.RenderUtil;

public class ChestedHorseWithInfoRenderer<T extends AbstractChestedHorse> extends ChestedHorseRenderer<T> {
    public ChestedHorseWithInfoRenderer(EntityRendererProvider.Context context, float f, ModelLayerLocation loc) {
        super(context, f, loc);
    }

    @Override
    public void render(T entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameWithRank(entity));

        List<String> statsString = EntityUtil.getHorseStatsString(entity);
        if (statsString != null) {
            infoString.addAll(statsString);
        }

        String stringAgeOrOwner = EntityUtil.getAgeOrOwnerString(entity);
        if (stringAgeOrOwner != null) {
            infoString.add(stringAgeOrOwner);
        }

        RenderUtil.renderEntityInfo(
            entity,
            infoString,
            matrixStackIn,
            bufferIn,
            packedLightIn
        );
    }
}
