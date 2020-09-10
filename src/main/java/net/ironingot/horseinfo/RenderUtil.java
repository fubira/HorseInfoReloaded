package net.ironingot.horseinfo;

import java.awt.Color;
import java.util.List;


import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.util.math.vector.Matrix4f;
import com.mojang.blaze3d.matrix.MatrixStack;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

public class RenderUtil
{
    // private static Logger logger = LogManager.getLogger();
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

    public static void renderEntityInfo(EntityRendererManager renderManager, Entity entity, List<String> infoString, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player.equals(getRider(entity)))
            return;

        double d0 = entity.getDistanceSq(mc.getRenderViewEntity());
        if (d0 >= 4096.0D) {
            return;
        }

        final float scale = 0.025F;
        Color baseColor = getLabelColor(entity);
        Color titleColor = baseColor.equals(Color.BLACK) ? Color.WHITE : baseColor;
        Color fontColor = Color.WHITE;

        float f = entity.getHeight() + 2.0F;

        matrixStackIn.push();
        matrixStackIn.translate(0.0D, (double)f, 0.0D);
        matrixStackIn.rotate(renderManager.getCameraOrientation());
        matrixStackIn.scale(-scale, -scale, scale);

        FontRenderer fontrenderer = renderManager.getFontRenderer();

        int fontHeight = 10;
        float baseY = (4 - infoString.size()) * fontHeight - ((getRider(entity) != null) ? fontHeight * 3 : fontHeight);

        int width = fontrenderer.getStringWidth(entity.getName().getString());
        for (int i = 0; i < infoString.size(); i++) {
            width = Math.max(fontrenderer.getStringWidth(infoString.get(i)), width);
        }
        int widthHarf = width / 2;

        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
        float f1 = mc.gameSettings.getTextBackgroundOpacity(0.4F);
        float r = (baseColor.getRed() / 255.0F) / 2.0F;
        float g = (baseColor.getGreen() / 255.0F) / 2.0F;
        float b = (baseColor.getBlue() / 255.0F) / 2.0F;
        int j = ((int)(f1 * 255.0F) << 24) + ((int)(r * 255.0F) << 16) + ((int)(g * 255.0F) << 8) + ((int)(b * 255.0F));

        for (int i = 0; i < infoString.size(); i++) {
            fontrenderer.renderString(infoString.get(i), -widthHarf, (int)baseY + fontHeight * i, (i == 0) ? titleColor.getRGB() : fontColor.getRGB(), false, matrix4f, bufferIn, false, j, packedLightIn);
        }
        matrixStackIn.pop();
    }
}
