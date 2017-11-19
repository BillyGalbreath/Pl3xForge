package net.pl3x.forge.gui.claim;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.pl3x.forge.util.gl.GuiUtil;

import java.io.IOException;

public class GuiClaim extends GuiScreen {
    private int xSize = 300;
    private int ySize = 220;
    private int x;
    private int y;

    public GuiClaim() {
        //
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void initGui() {
        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void onGuiClosed() {
    }

    private void drawForeground(int mouseX, int mouseY, float partialTicks) {
        fontRenderer.drawString("Claim", 8, 8, 0x404040);
    }

    private void drawBackground(int mouseX, int mouseY, float partialTicks) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // draw background tint
        drawDefaultBackground();

        // draw background stuffs
        drawBackground(mouseX, mouseY, partialTicks);

        // setup GL stuffs
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        // draw buttons and labels
        super.drawScreen(mouseX, mouseY, partialTicks);

        // more GL stuffs
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);

        // draw foreground
        drawForeground(mouseX, mouseY, partialTicks);

        // cleanup GL stuffs
        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        //
    }
}
