package net.ironingot.horseinforeloaded.fabric.utils;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.joml.Matrix4f;

public class RenderUtil
{
    public static float NAME_TAG_RANGE = 64.0f;

    public static Color getRiderHelmColor(Entity entity)
    {
        Entity rider = EntityUtil.getRider(entity);

        if (rider instanceof Player player)
        {
            ItemStack helmStack = player.getInventory().armor.get(3);

            return new Color(DyedItemColor.getOrDefault(helmStack, Color.BLACK.getRGB()));
        }
        return null;
    }

    public static Color getLabelColor(Entity entity)
    {
        Color color = Color.BLACK;

        if (entity instanceof AbstractHorse)
        {
            Color evaluateColor = HorseEntityUtil.getEvaluateRankColor((AbstractHorse)entity);
            if (evaluateColor != null)
                color = evaluateColor;
        }

        Color helmColor = getRiderHelmColor(entity);
        if (helmColor != null) {
            color = helmColor;
        }


        return color;
    }

    public static void renderEntityInfo(Entity entity, List<String> infoString, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.equals(EntityUtil.getRider(entity)))
            return;

        double d0 = entity.distanceToSqr(mc.getCameraEntity());
        if (d0 >= 2048.0D) {
            return;
        }

        final float scale = 0.025F;
        Color baseColor = getLabelColor(entity);
        Color titleColor = baseColor.equals(Color.BLACK) ? Color.WHITE : baseColor;
        Color fontColor = Color.WHITE;

        float f = entity.getBbHeight() + 2.0F;

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, (double)f, 0.0D);
        matrixStackIn.mulPose(mc.gameRenderer.getMainCamera().rotation());
        matrixStackIn.scale(scale, -scale, scale);

        int fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((EntityUtil.getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        int width = mc.font.width(entity.getName().getString());
        for (int i = 0; i < infoString.size(); i++) {
            width = Math.max(mc.font.width(infoString.get(i)), width);
        }
        int widthHarf = width / 2;
        int height = fontHeight * infoString.size();

        Matrix4f matrix4f = matrixStackIn.last().pose();
        float f1 = mc.options.getBackgroundOpacity(0.4F);
        float r = (baseColor.getRed() / 255.0F) / 2.0F;
        float g = (baseColor.getGreen() / 255.0F) / 2.0F;
        float b = (baseColor.getBlue() / 255.0F) / 2.0F;
        int j = ((int)(f1 * 255.0F) << 24) + ((int)(r * 255.0F) << 16) + ((int)(g * 255.0F) << 8) + ((int)(b * 255.0F));

        VertexConsumer consumer = bufferIn.getBuffer(RenderType.textBackgroundSeeThrough());
        consumer.addVertex(matrix4f, (float)-widthHarf - 1.0F, baseY - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float)-widthHarf - 1.0F, baseY + (float)height - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float) widthHarf, baseY + (float)height - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float) widthHarf, baseY - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);

        for (int i = 0; i < infoString.size(); i++) {
            String line = infoString.get(i);
            if (line != null) {
                mc.font.drawInBatch(line, -widthHarf, (int)baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() : fontColor.getRGB(), false, matrix4f, bufferIn, DisplayMode.NORMAL, 0, packedLightIn);
            }
        }
        matrixStackIn.popPose();
    }
}
