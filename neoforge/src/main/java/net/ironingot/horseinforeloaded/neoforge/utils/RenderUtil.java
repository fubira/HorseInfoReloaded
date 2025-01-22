package net.ironingot.horseinforeloaded.neoforge.utils;

import java.awt.Color;
import java.util.List;

import net.ironingot.horseinforeloaded.neoforge.render_state.HorseWithInfoRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import org.joml.Matrix4f;


public class RenderUtil
{
    public static float NAME_TAG_RANGE = 64.0f;

    public static Color getRiderHelmColor(Entity rider)
    {
        if (rider instanceof Player player)
        {
            ItemStack helmStack = player.getInventory().armor.get(3);

            return new Color(DyedItemColor.getOrDefault(helmStack, Color.BLACK.getRGB()));
        }
        return null;
    }

    public static Color getLabelColor(Entity rider, EntityRenderState renderState)
    {
        Color color = Color.BLACK;

        if (renderState instanceof HorseWithInfoRenderState)
        {
            Color evaluateColor = HorseEntityUtil.getEvaluateRankColor((HorseWithInfoRenderState)renderState);
            if (evaluateColor != null)
                color = evaluateColor;
        }

        Color helmColor = getRiderHelmColor(rider);
        if (helmColor != null)
            color = helmColor;

        return color;
    }

    public static void renderEntityInfo(Entity rider, EntityRenderState renderState, List<String> infoString, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn)
    {
        Minecraft mc = Minecraft.getInstance();
        assert mc.player != null;
        if (mc.player.equals(rider))
            return;

        if (renderState.distanceToCameraSq >= 2048.0D) {
            return;
        }

        final float scale = 0.025F;
        Color baseColor = getLabelColor(rider, renderState);
        Color titleColor = baseColor.equals(Color.BLACK) ? Color.WHITE : baseColor;
        Color fontColor = Color.WHITE;

        float f = renderState.boundingBoxHeight + 2.0F;

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, (double)f, 0.0D);
        matrixStackIn.mulPose(mc.gameRenderer.getMainCamera().rotation());
        matrixStackIn.scale(scale, -scale, scale);

        int fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((rider != null) ? fontHeight * 3 : fontHeight);

        int width = 0;
        for (String s : infoString) {
            if (s.contains("§")) {
                String[] parts = s.split("§");
                boolean first = true;
                int w = 0;
                for (String p : parts) {
                    if (first) {
                        w = mc.font.width(p);
                        first = false;
                    } else {
                        w += mc.font.width(p.substring(1));
                    }
                }
                width = Math.max(w, width);
            } else {
                width = Math.max(mc.font.width(s), width);
            }
        }
        int widthHalf = width / 2;
        int height = fontHeight * infoString.size();

        Matrix4f matrix4f = matrixStackIn.last().pose();
        float f1 = mc.options.getBackgroundOpacity(0.4F);
        float r = (baseColor.getRed() / 255.0F) / 2.0F;
        float g = (baseColor.getGreen() / 255.0F) / 2.0F;
        float b = (baseColor.getBlue() / 255.0F) / 2.0F;
        int j = ((int)(f1 * 255.0F) << 24) + ((int)(r * 255.0F) << 16) + ((int)(g * 255.0F) << 8) + ((int)(b * 255.0F));

        VertexConsumer consumer = bufferIn.getBuffer(RenderType.textBackgroundSeeThrough());
        consumer.addVertex(matrix4f, (float)-widthHalf - 1.0F, baseY - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float)-widthHalf - 1.0F, baseY + (float)height - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float) widthHalf, baseY + (float)height - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);
        consumer.addVertex(matrix4f, (float) widthHalf, baseY - 1.0F, 0.0F).setColor(j).setLight(packedLightIn);

        for (int i = 0; i < infoString.size(); i++) {
            String line = infoString.get(i);
            if (line != null) {
                if (line.contains("§")) {
                    String[] parts = line.split("§");
                    int shift = 0;
                    boolean first = true;
                    for (String p : parts) {
                        if (first) {
                            mc.font.drawInBatch(p, -widthHalf, (int) baseY + fontHeight * i, fontColor.getRGB(), false, matrix4f, bufferIn, DisplayMode.NORMAL, 0, packedLightIn);
                            shift = mc.font.width(p);
                            first = false;
                        } else {
                            char colorChar = p.charAt(0);
                            Color statColor;
                            switch (colorChar) {
                                case 'r' -> statColor = Color.RED;
                                case 'g' -> statColor = Color.GREEN;
                                case 'c' -> statColor = Color.CYAN;
                                case 'w' -> statColor = Color.WHITE;
                                default -> statColor = fontColor;
                            }
                            String drawString = p.substring(1);
                            mc.font.drawInBatch(drawString, shift - widthHalf, (int) baseY + fontHeight * i, statColor.getRGB(), false, matrix4f, bufferIn, DisplayMode.NORMAL, 0, packedLightIn);
                            shift += mc.font.width(drawString);
                        }
                    }
                } else {
                    mc.font.drawInBatch(line, -widthHalf, (int) baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() : fontColor.getRGB(), false, matrix4f, bufferIn, DisplayMode.NORMAL, 0, packedLightIn);
                }
            }
        }
        matrixStackIn.popPose();
    }
}
