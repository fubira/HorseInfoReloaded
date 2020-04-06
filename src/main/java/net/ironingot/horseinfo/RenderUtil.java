package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.DyeableArmorItem;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenderUtil
{
    private static Logger logger = LogManager.getLogger();
    public static float NAME_TAG_RANGE = 64.0f;

    public static Entity getRider(Entity entity)
    {
        List<Entity> passengers = entity.getPassengers();
        if (passengers == null || passengers.size() == 0)
            return null;

        return passengers.get(0);
    }

    public static Color getRiderHelmColor(Entity entity)
    {
        Entity ridingEntity = getRider(entity);

        if (ridingEntity instanceof PlayerEntity)
        {
            PlayerEntity ridingPlayer = (PlayerEntity)ridingEntity;
            ItemStack helmStack = ridingPlayer.inventory.armorItemInSlot(3);

            if (helmStack != null && helmStack.getItem() instanceof DyeableArmorItem)
            {
                DyeableArmorItem helmItem = (DyeableArmorItem)helmStack.getItem();
                return new Color(helmItem.getColor(helmStack));
            }
        }
        return null;
    }

    private static Color getLabelColor(Entity entity)
    {
        Color color = Color.BLACK;

        if (entity instanceof AbstractHorseEntity)
        {
            double evaluateValue = HorseInfoUtil.getEvaluateValue((AbstractHorseEntity)entity);
            Color evaluateColor = HorseInfoUtil.getEvaluateRankColor(evaluateValue);
            if (evaluateColor != null)
                color = evaluateColor;
        }

        Color helmColor = getRiderHelmColor(entity);
        if (helmColor != null)
            color = helmColor;

        return color;
    }

    public static void renderEntityInfo(EntityRendererManager renderManager, FontRenderer fontRenderer, Entity entity, List<String> infoString)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.equals(getRider(entity)))
            return;

        double x = entity.getPosX();
        double y = entity.getPosY();
        double z = entity.getPosZ();

        double d0 = entity.getDistanceSq(mc.getRenderViewEntity());
        final float f = NAME_TAG_RANGE / 2;
        final float scale = 0.02666667F;
        Color baseColor = getLabelColor(entity);
        Color titleColor = baseColor.equals(Color.BLACK) ? Color.WHITE : baseColor;
        Color fontColor = Color.WHITE;

        if (d0 >= (double)(f * f))
            return;

        GlStateManager.alphaFunc(516, 0.1F);
        FontRenderer fontrenderer = fontRenderer;
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)x, (float)y + entity.getHeight() + 1.8F /*- (entity.isChild() ? entity.height / 2.0F : 0.0F)*/, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(-renderManager.info.getYaw(), 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(renderManager.info.getPitch(), 1.0F, 0.0F, 0.0F);
        GlStateManager.scalef(-scale, -scale, scale);
        GlStateManager.translatef(0.0F, 9.374999F, 0.0F);
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);

        int fontHeight = 9;
        float baseY = (4 - infoString.size()) * fontHeight - ((getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        int width = fontrenderer.getStringWidth(entity.getName().getString());
        for (int i = 0; i < infoString.size(); i++) {
            width = Math.max(fontrenderer.getStringWidth(infoString.get(i)), width);
        }
        int widthHarf = width / 2;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        float r = (baseColor.getRed() / 255.0F) / 2.0F;
        float g = (baseColor.getGreen() / 255.0F) / 2.0F;
        float b = (baseColor.getBlue() / 255.0F) / 2.0F;
        float a = 0.4F;

        vertexBuffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        vertexBuffer.pos((double)(-widthHarf - 1), baseY , 0.0D).color(r, g, b, a).endVertex();
        vertexBuffer.pos((double)(-widthHarf - 1), baseY + fontHeight * (infoString.size()), 0.0D).color(r, g, b, a).endVertex();
        vertexBuffer.pos((double)(widthHarf + 1), baseY + fontHeight * (infoString.size()), 0.0D).color(r, g, b, a).endVertex();
        vertexBuffer.pos((double)(widthHarf + 1), baseY, 0.0D).color(r, g, b, a).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.depthMask(true);
        for (int i = 0; i < infoString.size(); i++) {
            fontrenderer.drawString(infoString.get(i), -widthHarf, (int)baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() : fontColor.getRGB());
        }
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
    }
}
