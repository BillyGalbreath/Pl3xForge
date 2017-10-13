package net.pl3x.forge.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.util.GuiUtil;

@SideOnly(Side.CLIENT)
public class TabButton extends Button {
    public boolean selected = false;
    private int w2;
    private int h2;

    public TabButton(int buttonId, int x, int y, int width, int height, String displayString) {
        super(buttonId, x, y, width, height, displayString);
        w2 = width / 2;
        h2 = height / 2;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }
        hovered = !selected && mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        int i = selected ? 16 : 0;
        drawTexturedModalRect(x, y, 0, i, w2, h2);
        drawTexturedModalRect(x, y + h2, 0, 16 + i - h2, w2, h2);
        drawTexturedModalRect(x + w2, y, 150 - w2, i, w2, h2);
        drawTexturedModalRect(x + w2, y + h2, 150 - w2, 16 + i - h2, w2, h2);

        mouseDragged(mc, mouseX, mouseY);

        int color = 14737632;
        if (packedFGColour != 0) {
            color = packedFGColour;
        } else if (!enabled) {
            color = 10526880;
        } else if (hovered) {
            color = 16777120;
        }

        drawCenteredString(mc.fontRenderer, displayString, x + w2, y + h2 - 4, color);
    }
}
