package net.pl3x.forge.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.pl3x.forge.container.ContainerTrafficLightControlBox;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.tileentity.TileEntityTrafficLight;
import net.pl3x.forge.tileentity.TileEntityTrafficLightControlBox;
import net.pl3x.forge.util.gl.GuiUtil;

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

        fontRenderer.drawString("N", 35, 112, 0x404040);
        fontRenderer.drawString("S", 35, 135, 0x404040);
        fontRenderer.drawString("W", 25, 123, 0x404040);
        fontRenderer.drawString("E", 45, 123, 0x404040);
        fontRenderer.drawString("+", 35, 123, 0x404040);

        fontRenderer.drawString("Next", 79, 106, 0x404040);
        fontRenderer.drawString("Cycle", 77, 116, 0x404040);
        fontRenderer.drawString("In:", 84, 126, 0x404040);

        String timeLeft = Integer.toString(container.controlBox.ticksPerCycle
                .get(container.controlBox.intersectionState) - container.controlBox.tick);
        fontRenderer.drawString(timeLeft, 90 - fontRenderer.getStringWidth(timeLeft) / 2, 141, 0x404040);

        // draw textboxes
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, 0);

        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 26, 100, 204, 0, 24, 9);
        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 26, 145, 204, 0, 24, 9);
        GlStateManager.pushMatrix();
        GlStateManager.rotate(90, 0, 0, 1);
        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 115, -20, 204, 0, 24, 9);
        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 115, -64, 204, 0, 24, 9);
        GlStateManager.popMatrix();

        drawLights();

        GlStateManager.popMatrix();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks, 1024);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    private void drawLights() {
        TileEntityTrafficLightControlBox.IntersectionState state = container.controlBox.intersectionState;
        switch (state) {
            case NS_GREEN_EW_RED:
                drawLight(EnumFacing.NORTH, TileEntityTrafficLight.LightState.GREEN);
                drawLight(EnumFacing.SOUTH, TileEntityTrafficLight.LightState.GREEN);
                drawLight(EnumFacing.WEST, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.EAST, TileEntityTrafficLight.LightState.RED);
                break;
            case NS_YELLOW_EW_RED:
                drawLight(EnumFacing.NORTH, TileEntityTrafficLight.LightState.YELLOW);
                drawLight(EnumFacing.SOUTH, TileEntityTrafficLight.LightState.YELLOW);
                drawLight(EnumFacing.WEST, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.EAST, TileEntityTrafficLight.LightState.RED);
                break;
            case NS_RED_EW_GREEN:
                drawLight(EnumFacing.NORTH, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.SOUTH, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.WEST, TileEntityTrafficLight.LightState.GREEN);
                drawLight(EnumFacing.EAST, TileEntityTrafficLight.LightState.GREEN);
                break;
            case NS_RED_EW_YELLOW:
                drawLight(EnumFacing.NORTH, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.SOUTH, TileEntityTrafficLight.LightState.RED);
                drawLight(EnumFacing.WEST, TileEntityTrafficLight.LightState.YELLOW);
                drawLight(EnumFacing.EAST, TileEntityTrafficLight.LightState.YELLOW);
                break;
        }
    }

    private void drawLight(EnumFacing facing, TileEntityTrafficLight.LightState state) {
        switch (facing) {
            case NORTH:
                switch (state) {
                    case RED:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 28, 102, 204, 9, 6, 5);
                        break;
                    case YELLOW:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 35, 102, 216, 9, 6, 5);
                        break;
                    case GREEN:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 42, 102, 210, 9, 6, 5);
                        break;
                }
                break;
            case SOUTH:
                switch (state) {
                    case RED:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 28, 147, 204, 9, 6, 5);
                        break;
                    case YELLOW:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 35, 147, 216, 9, 6, 5);
                        break;
                    case GREEN:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 42, 147, 210, 9, 6, 5);
                }
                break;
            case WEST:
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90, 0, 0, 1);
                switch (state) {
                    case RED:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 117, -18, 204, 9, 6, 5);
                        break;
                    case YELLOW:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 124, -18, 216, 9, 6, 5);
                        break;
                    case GREEN:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 131, -18, 210, 9, 6, 5);
                }
                GlStateManager.popMatrix();
                break;
            case EAST:
                GlStateManager.pushMatrix();
                GlStateManager.rotate(90, 0, 0, 1);
                switch (state) {
                    case RED:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 117, -62, 204, 9, 6, 5);
                        break;
                    case YELLOW:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 124, -62, 216, 9, 6, 5);
                        break;
                    case GREEN:
                        GuiUtil.drawTexture(this, GuiUtil.GUI_ELEMENTS, 131, -62, 210, 9, 6, 5);
                }
                GlStateManager.popMatrix();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            // button pressed
        }
    }
}
