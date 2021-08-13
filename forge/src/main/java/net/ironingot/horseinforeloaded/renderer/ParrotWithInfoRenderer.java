package net.ironingot.horseinforeloaded.renderer;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.ironingot.horseinforeloaded.HorseInfoMod;
import net.ironingot.horseinforeloaded.utils.EntityUtil;
import net.ironingot.horseinforeloaded.utils.RenderUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.animal.Parrot;

@OnlyIn(Dist.CLIENT)
public class ParrotWithInfoRenderer extends ParrotRenderer
{
    public ParrotWithInfoRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(Parrot entity, float yaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entity, yaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);

        if (!HorseInfoMod.isActive()) {
            return;
        }

        ArrayList<String> infoString = new ArrayList<String>();
        infoString.add(EntityUtil.getDisplayNameString(entity));
        infoString.add(EntityUtil.getOwnerString(entity));

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
