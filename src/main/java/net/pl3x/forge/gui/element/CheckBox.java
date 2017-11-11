package net.pl3x.forge.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.util.gl.GuiUtil;

@SideOnly(Side.CLIENT)
public class CheckBox extends Button {
    protected boolean checked;

    public CheckBox(int id, int x, int y, String label, boolean checked) {
        super(id, x, y, 10, 10, label);
        this.checked = checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!visible) {
            return;
        }
        hovered = mouseX >= x && mouseY >= y && mouseX < x + 13 + mc.fontRenderer.getStringWidth(displayString) && mouseY < y + height;
        mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
        GlStateManager.color(1, 1, 1, 1);
        int xOffset = 0;
        int yOffset = 0;
        if (checked) {
            xOffset += 10;
        }
        if (hovered) {
            yOffset += 10;
        }
        drawTexturedModalRect(x, y, 204 + xOffset, 21 + yOffset, 10, 10);
        mc.fontRenderer.drawString(displayString, x + 13, y + 1, 0x404040);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (enabled && visible && hovered) {
            checked = !checked;
            return true;
        }
        return false;
    }
}