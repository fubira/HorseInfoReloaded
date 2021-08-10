package net.ironingot.horseinfo.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

import net.ironingot.horseinfo.HorseInfoMod;
import net.ironingot.horseinfo.utils.EntityInfoUtil;
import net.ironingot.horseinfo.utils.RenderUtil;

@OnlyIn(Dist.CLIENT)
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
        infoString.add(EntityInfoUtil.getDisplayNameWithRank(entity));
        infoString.addAll(EntityInfoUtil.getHorseStatsString(entity));

        String ageOrOwner = EntityInfoUtil.getAgeOrOwnerString(entity);
        if (ageOrOwner != null) {
            infoString.add(ageOrOwner);
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
