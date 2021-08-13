package net.ironingot.horseinforeloaded.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.ironingot.horseinforeloaded.HorseInfoMod;
import net.ironingot.horseinforeloaded.utils.EntityInfoUtil;
import net.ironingot.horseinforeloaded.utils.RenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.Wolf;

@OnlyIn(Dist.CLIENT)
public class WolfWithInfoRenderer extends WolfRenderer
{
    public WolfWithInfoRenderer(EntityRendererProvider.Context context)
    {
        super(context);
    }

    @Override
    public void render(Wolf entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityInfoUtil.getDisplayNameString(entity));
        infoString.add(EntityInfoUtil.getOwnerString(entity));

        if (entity.isTame()) {
            RenderUtil.renderEntityInfo(
                entity,
                infoString,
                matrixStackIn,
                bufferIn,
                packedLightIn
            );
        }
    }
}
