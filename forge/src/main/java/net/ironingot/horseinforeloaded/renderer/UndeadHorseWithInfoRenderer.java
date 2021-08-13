package net.ironingot.horseinforeloaded.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.ironingot.horseinforeloaded.HorseInfoMod;
import net.ironingot.horseinforeloaded.utils.EntityInfoUtil;
import net.ironingot.horseinforeloaded.utils.RenderUtil;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

@OnlyIn(Dist.CLIENT)
public class UndeadHorseWithInfoRenderer extends UndeadHorseRenderer {
    public UndeadHorseWithInfoRenderer(EntityRendererProvider.Context context, ModelLayerLocation loc) {
        super(context, loc);
    }

    @Override
    public void render(AbstractHorse entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityInfoUtil.getDisplayNameWithRank(entity));
        infoString.addAll(EntityInfoUtil.getHorseStatsString(entity));

        RenderUtil.renderEntityInfo(
            entity,
            infoString,
            matrixStackIn,
            bufferIn,
            packedLightIn
        );
    }
}
