package net.pl3x.forge.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.util.gl.GuiUtil;
import net.pl3x.forge.util.gl.HUDPosition;

import java.text.DecimalFormat;

public class HUDBalance {
    public static long balance = 0;

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fontRenderer = mc.fontRenderer;

    private final DecimalFormat df = new DecimalFormat("#,###");

    public void draw() {
        if (!ClientConfig.balanceHud.enabled) {
            return;
        }

        String balanceStr = df.format(balance);
        mc.getTextureManager().bindTexture(GuiUtil.COIN);
        ScaledResolution scale = new ScaledResolution(mc);

        HUDPosition position = ClientConfig.balanceHud.position;
        int x, y;

        switch (position) {
            case BOTTOM_LEFT:
                x = 10;
                y = scale.getScaledHeight() - 20;
                break;
            case BOTTOM_CENTER:
                x = scale.getScaledWidth() / 2 - ((fontRenderer.getStringWidth(balanceStr) + 16) / 2);
                y = scale.getScaledHeight() - 20;
                break;
            case BOTTOM_RIGHT:
                x = scale.getScaledWidth() - (fontRenderer.getStringWidth(balanceStr) + 16) - 10;
                y = scale.getScaledHeight() - 20;
                break;
            case CENTER_LEFT:
                x = 10;
                y = scale.getScaledHeight() / 2;
                break;
            case CENTER_CENTER:
                x = scale.getScaledWidth() / 2 - ((fontRenderer.getStringWidth(balanceStr) + 16) / 2);
                y = scale.getScaledHeight() / 2;
                break;
            case CENTER_RIGHT:
                x = scale.getScaledWidth() - (fontRenderer.getStringWidth(balanceStr) + 16) - 10;
                y = scale.getScaledHeight() / 2;
                break;
            case TOP_LEFT:
                x = 10;
                y = 10;
                break;
            case TOP_RIGHT:
                x = scale.getScaledWidth() - (fontRenderer.getStringWidth(balanceStr) + 16) - 10;
                y = 10;
                break;
            case TOP_CENTER:
            default:
                x = scale.getScaledWidth() / 2 - ((fontRenderer.getStringWidth(balanceStr) + 16) / 2);
                y = 10;
        }

        x += ClientConfig.balanceHud.relativeX;
        y += ClientConfig.balanceHud.relativeY;

        Gui.drawModalRectWithCustomSizedTexture(x, y - 5, 0, 0, 16, 16, 16, 16);
        fontRenderer.drawStringWithShadow(balanceStr, x + 18, y, 16777215);
    }
}
