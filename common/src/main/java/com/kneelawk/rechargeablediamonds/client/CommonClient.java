package com.kneelawk.rechargeablediamonds.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;

import com.kneelawk.rechargeablediamonds.Constants;

public class CommonClient {
    public static void renderDiamondBar(GuiGraphics guiGraphics, int xOffset, int yOffset, int energy) {
        if (energy > 0) {
            int barWidth = getBarWidth(energy);
            int barColor = getBarColor(energy);
            int x = xOffset + 2;
            int y = yOffset + 13;
            guiGraphics.fill(RenderType.guiOverlay(), x, y, x + 13, y + 2, 0xFF000000);
            guiGraphics.fill(RenderType.guiOverlay(), x, y, x + barWidth, y + 1, barColor | 0xFF000000);
        }
    }

    private static int getBarWidth(int energy) {
        return Math.round((float) energy * 13.0f / (float) Constants.DIAMOND_CAPACITY);
    }

    private static int getBarColor(int energy) {
        float f = (float) energy / (float) Constants.DIAMOND_CAPACITY;
        return Mth.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
    }
}
