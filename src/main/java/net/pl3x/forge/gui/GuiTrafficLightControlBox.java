package net.pl3x.forge.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pl3x.forge.container.ContainerTrafficLightControlBox;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.tileentity.TileEntityTrafficLightControlBox;
import net.pl3x.forge.util.GL;
import net.pl3x.forge.util.GuiUtil;

public class GuiTrafficLightControlBox extends GuiContainer {
    public final EntityPlayer player;
    public final ContainerTrafficLightControlBox container;

    private int x;
    private int y;

    public GuiTrafficLightControlBox(Container container, EntityPlayer player) {
        super(container);

        this.player = player;
        this.container = (ContainerTrafficLightControlBox) container;

        xSize = 300;
        //ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        // add buttons and fields
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        // update fields and other stuffs

        // tick buttons for hovers
        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Traffic Light Control Box", 8, 8, 0x404040);

        int i = 0;
        for (TileEntityTrafficLightControlBox.IntersectionState state :
                TileEntityTrafficLightControlBox.IntersectionState.values()) {
            fontRenderer.drawString(state.name(), 10, 30 + 12 * i,
                    state == container.controlBox.intersectionState ? 0xffffff : 0x404040);
            i++;
        }

        String timeLeft = Integer.toString(container.controlBox.ticksPerCycle
                .get(container.controlBox.intersectionState) - container.controlBox.tick);
        fontRenderer.drawString("Next", 275 - fontRenderer.getStringWidth("Next") / 2, 20, 0x404040);
        fontRenderer.drawString("Cycle", 275 - fontRenderer.getStringWidth("Cycle") / 2, 30, 0x404040);
        fontRenderer.drawString("In:", 275 - fontRenderer.getStringWidth("In") / 2, 40, 0x404040);
        fontRenderer.drawString(timeLeft, 275 - fontRenderer.getStringWidth(timeLeft) / 2, 55, 0x404040);

        // draw textboxes
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);

        TileEntityTrafficLightControlBox.IntersectionState state = container.controlBox.intersectionState;
        switch (state) {
            case NS_GREEN_EW_RED:
                drawRect(x + 9, y + 27, x + 107, y + 39, 0xFF336699);
                break;
            case NS_YELLOW_EW_RED:
                drawRect(x + 9, y + 39, x + 107, y + 51, 0xFF336699);
                break;
            case NS_RED_EW_GREEN:
                drawRect(x + 9, y + 51, x + 107, y + 63, 0xFF336699);
                break;
            case NS_RED_EW_YELLOW:
                drawRect(x + 9, y + 63, x + 107, y + 75, 0xFF336699);
                break;
        }

        GlStateManager.disableTexture2D();
        GlStateManager.glLineWidth(1);
        GL.drawLine(x + 9, y + 27, x + 107, y + 27, 0xffffff);
        GL.drawLine(x + 9, y + 39, x + 107, y + 39, 0xffffff);
        GL.drawLine(x + 9, y + 51, x + 107, y + 51, 0xffffff);
        GL.drawLine(x + 9, y + 63, x + 107, y + 63, 0xffffff);
        GL.drawLine(x + 9, y + 75, x + 107, y + 75, 0xffffff);
        GL.drawLine(x + 9, y + 27, x + 9, y + 75, 0xffffff);
        GL.drawLine(x + 107, y + 27, x + 107, y + 75, 0xffffff);
        GlStateManager.enableTexture2D();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks, 1024);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            // button pressed
        }
    }
}
