package net.pl3x.forge.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.pl3x.forge.container.ContainerTrafficLightControlBox;
import net.pl3x.forge.gui.element.Button;
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

        xSize = 234;
        ySize = 212;
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

        // draw textboxes
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
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
