package net.pl3x.forge.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.pl3x.forge.util.gl.GuiUtil;

public class GuiBedsideTable extends GuiContainer {
    public GuiBedsideTable(Container container) {
        super(container);
        this.allowUserInput = false;
        this.ySize = 133;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Bedside Table", 8, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, 39, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, guiLeft, guiTop, xSize, ySize);
        GuiUtil.drawSlots(this, inventorySlots.inventorySlots, guiLeft, guiTop);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }
}
