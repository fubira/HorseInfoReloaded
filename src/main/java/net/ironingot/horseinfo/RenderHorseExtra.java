package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.UsernameCache;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.renderer.entity.RenderHorse;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLLog;

@SideOnly(Side.CLIENT)
public class RenderHorseExtra extends RenderHorse
{
    private static Logger logger = FMLLog.getLogger();
    public static float NAME_TAG_RANGE = 64.0f;

    public RenderHorseExtra(RenderManager renderManager, ModelHorse modelBase, float partialTicks)
    {
        super(renderManager, modelBase, partialTicks);
    }

    @Override
    public void doRender(EntityHorse entityHorse, double x, double y, double z, float yaw, float partialTicks)
    {
        super.doRender(entityHorse, x, y, z, yaw, partialTicks);

        if (HorseInfo.isShowHorseInfo())
            renderHorseInfo(entityHorse, x, y, z);
    }

    private Color getLabelColor(EntityHorse entityHorse)
    {
        Color baseColor = Color.BLACK;
        Color evaluateColor = HorseUtils.getEvaluateRankColor(HorseUtils.getEvaluateValue(entityHorse));
        Color helmColor = HorseUtils.getRiderHelmColor(entityHorse);

        if (evaluateColor != null)
            baseColor = evaluateColor;
        if (helmColor != null)
            baseColor = helmColor;

        return baseColor;
    }

    public void renderHorseInfo(EntityHorse entity, double x, double y, double z)
    {
        Entity riddenByEntity = entity.riddenByEntity;
        if (Minecraft.getMinecraft().thePlayer.equals(riddenByEntity))
            return;

        double d0 = entity.getDistanceSqToEntity(this.renderManager.livingPlayer);
        final float f = NAME_TAG_RANGE / 2;
        final float scale = 0.02666667F;
        Color baseColor = getLabelColor(entity);
        Color fontColor = baseColor.equals(Color.BLACK) ? Color.WHITE : baseColor;

        if (d0 < (double)(f * f))
        {
            List<String> stringListHorseInfo = HorseUtils.getHorseInfoString(entity);
            String stringDisplayName = HorseUtils.getDisplayNameWithRank(entity);
            GlStateManager.alphaFunc(516, 0.1F);

            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x, (float)y + entity.height + 1.8F - (entity.isChild() ? entity.height / 2.0F : 0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-scale, -scale, scale);
            GlStateManager.translate(0.0F, 9.374999F, 0.0F);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

            float baseY = -9.0f;
            if (riddenByEntity instanceof EntityPlayer)
                baseY = -27.0f;

            int nameWidth = fontrenderer.getStringWidth(stringDisplayName);
            int statusWidth = nameWidth;
            for (int i = 0; i < stringListHorseInfo.size(); i++) {
                statusWidth = Math.max(fontrenderer.getStringWidth(stringListHorseInfo.get(i)), statusWidth);
            }
            int nameWidthHarf = nameWidth / 2;
            int statusWidthHarf = statusWidth / 2;

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float r = (baseColor.getRed() / 255.0F) / 2.0F;
            float g = (baseColor.getGreen() / 255.0F) / 2.0F;
            float b = (baseColor.getBlue() / 255.0F) / 2.0F;
            float a = 0.4F;

            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double)(-statusWidthHarf - 1), baseY , 0.0D).color(r, g, b, a).endVertex();
            worldrenderer.pos((double)(-statusWidthHarf - 1), baseY + 9.0D * (stringListHorseInfo.size() + 1), 0.0D).color(r, g, b, a).endVertex();
            worldrenderer.pos((double)(statusWidthHarf + 1), baseY + 9.0D * (stringListHorseInfo.size() + 1), 0.0D).color(r, g, b, a).endVertex();
            worldrenderer.pos((double)(statusWidthHarf + 1), baseY, 0.0D).color(r, g, b, a).endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask(true);
            fontrenderer.drawString(stringDisplayName, -statusWidthHarf, (int)baseY, fontColor.getRGB());
            for (int i = 0; i < stringListHorseInfo.size(); i++) {
                fontrenderer.drawString(stringListHorseInfo.get(i), -statusWidthHarf, (int)baseY + 9 * (i + 1), 0xFFFFFFFF);
            }
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }
}
