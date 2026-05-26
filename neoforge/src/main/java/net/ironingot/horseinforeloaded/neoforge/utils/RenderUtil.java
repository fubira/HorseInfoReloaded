package net.ironingot.horseinforeloaded.neoforge.utils;

import java.awt.Color;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.phys.Vec3;

public class RenderUtil
{
    public static double NAME_TAG_RANGE = 1024.0D;

    public static int getRiderHelmColorOrDefault(Entity entity, int defaultColor)
    {
        Entity rider = entity.getFirstPassenger();

        if (rider instanceof Player player)
        {
            ItemStack helmStack = player.getItemBySlot(EquipmentSlot.HEAD);

            return DyedItemColor.getOrDefault(helmStack, Color.BLACK.getRGB());
        }
        return defaultColor;
    }

    public static int getLabelColor(Entity entity)
    {
        int color = ARGB.color(0, 0, 0);

        if (entity instanceof AbstractHorse)
        {
            color = HorseEntityUtil.getEvaluateRankColor((AbstractHorse)entity);
        }

        return getRiderHelmColorOrDefault(entity, color);
    }

    public static void RenderInfoString(
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        CameraRenderState cameraRenderState,
        int lightCoords,
        double distanceToCameraSq,
        Vec3 pos,
        boolean isRidden,
        int titleColor,
        int fontColor,
        int bgColor,
        List<String> infoStrings
    ) {
        if (distanceToCameraSq >= RenderUtil.NAME_TAG_RANGE) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        poseStack.pushPose();
        poseStack.translate(pos.x, pos.y + 2.5, pos.z);
        poseStack.mulPose(cameraRenderState.orientation);
        poseStack.scale(0.025F, -0.025F, 0.025F);

        int fontHeight = 10;
        float baseY = (4 - infoStrings.size()) * fontHeight - (isRidden ? fontHeight * 3 : fontHeight);
        int leftX = -infoStrings.stream()
            .filter(line -> line != null)
            .map(mc.font::width)
            .max(Integer::compare)
            .orElse(0) / 2;

        for (int i = 0; i < infoStrings.size(); i++) {
            String line = infoStrings.get(i);
            int lineFontColor = (i == 0) ? titleColor : fontColor;
            if (line != null) {
                submitNodeCollector.submitText(
                    poseStack,
                    leftX,
                    baseY + fontHeight * i,
                    Component.literal(line).withColor(lineFontColor).getVisualOrderText(),
                    false,
                    DisplayMode.NORMAL,
                    lightCoords,
                    lineFontColor,
                    bgColor,
                    0
                );
            }
        }
        poseStack.popPose();
    }
}
