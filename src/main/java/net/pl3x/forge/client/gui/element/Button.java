package net.pl3x.forge.client.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class Button extends GuiButton {
    private static final int HELD_REPEAT_DELAY = 10;
    private boolean held = false;
    private int heldTicks = 0;
    private int w2;
    private int h2;

    public Button(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        w2 = width / 2;
        h2 = height / 2;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }
        hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

        if (held) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180, 0, 0, 1);
            GlStateManager.translate(-x * 2 - width, -y * 2 - height, 0);
        }
        int i = getHoverState(hovered);
        drawTexturedModalRect(x, y, 0, 46 + i, w2, h2);
        drawTexturedModalRect(x, y + h2, 0, 66 + i - h2, w2, h2);
        drawTexturedModalRect(x + w2, y, 200 - w2, 46 + i, w2, h2);
        drawTexturedModalRect(x + w2, y + h2, 200 - w2, 66 + i - h2, w2, h2);
        if (held) {
            GlStateManager.popMatrix();
        }

        mouseDragged(mc, mouseX, mouseY);

        int color = 14737632;
        if (packedFGColour != 0) {
            color = packedFGColour;
        } else if (!enabled) {
            color = 10526880;
        } else if (hovered) {
            color = 16777120;
        }

        drawCenteredString(mc.fontRenderer, displayString, x + w2 + (held ? 1 : 0), y + h2 - 4 + (held ? 1 : 0), color);
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        if (!enabled) {
            return 0;
        } else if (mouseOver) {
            return 40;
        }
        return 20;
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return held = super.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        held = false;
        heldTicks = 0;
    }

    public boolean tick() {
        if (held) {
            if (hovered) {
                return heldTicks++ > HELD_REPEAT_DELAY;
            }
        }
        mouseReleased(0, 0);
        return false;
    }
}
