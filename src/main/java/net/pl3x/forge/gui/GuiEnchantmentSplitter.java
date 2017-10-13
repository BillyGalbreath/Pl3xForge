package net.pl3x.forge.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.container.ContainerEnchantmentSplitter;
import net.pl3x.forge.tileentity.TileEntityEnchantmentSplitter;
import net.pl3x.forge.util.GuiUtil;

public class GuiEnchantmentSplitter extends GuiContainer {
    private final ContainerEnchantmentSplitter container;
    private InventoryPlayer playerInv;

    private String name;
    private String splitCost = "Split Cost: 3";

    private int animFrame = 0;
    private boolean animBackwards = false;

    private int nameX;
    private int errorX;
    private int errorY;
    private int toolX;
    private int toolY;
    private int bookX;
    private int bookY;
    private int iconX;
    private int iconY;
    private int inputX;
    private int inputY;
    private int resultX;
    private int resultY;
    private int x;
    private int y;

    private int splitCostX;

    public GuiEnchantmentSplitter(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.container = (ContainerEnchantmentSplitter) inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        TileEntity te = playerInv.player.world.getTileEntity(container.selfPosition);
        if (te instanceof TileEntityEnchantmentSplitter && ((TileEntityEnchantmentSplitter) te).hasCustomName()) {
            name = ((TileEntityEnchantmentSplitter) te).getName();
        } else {
            name = I18n.format(ModBlocks.enchantmentSplitter.getUnlocalizedName() + ".name");
        }
        nameX = xSize - fontRenderer.getStringWidth(name) - 6;

        errorX = x + 101;
        errorY = y + 32;
        toolX = x + 78;
        toolY = y + 20;
        bookX = x + 78;
        bookY = y + 45;
        iconX = x + 15;
        iconY = y + 15;
        inputX = x + 79;
        inputY = y + 46;
        resultX = x + 133;
        resultY = y + 46;

        splitCostX = xSize - 8 - fontRenderer.getStringWidth(splitCost);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (animBackwards) {
            animFrame--;
            if (animFrame <= 0) {
                animBackwards = false;
            }
        } else {
            animFrame++;
            if (animFrame >= 25) {
                animBackwards = true;
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(name, nameX, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, 72, 0x404040);

        if (container.getSlot(2).getHasStack() && container.getSlot(3).getHasStack()) {
            int color = container.getSlot(2).canTakeStack(playerInv.player) ? 0x80FF20 : 0xFF6060;
            int shadowColor = -16777216 | (color & 0xFCFCFC) >> 2 | color & -16777216;
            fontRenderer.drawString(splitCost, splitCostX, 68, shadowColor);
            fontRenderer.drawString(splitCost, splitCostX + 1, 67, shadowColor);
            fontRenderer.drawString(splitCost, splitCostX + 1, 68, shadowColor);
            fontRenderer.drawString(splitCost, splitCostX, 67, color);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, inventorySlots.inventorySlots, x, y);
        GuiUtil.drawTexture(this, GuiUtil.ENCHANTED_BOOK, iconX, iconY, 0, 0, 35, 35, 35, 35);

        mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
        GlStateManager.color(1, 1, 1, 1);

        drawTexturedModalRect(inputX, inputY, 168, 0, 18, 18);
        drawTexturedModalRect(resultX, resultY, 186, 0, 18, 18);

        // draw error X between slots
        if ((container.getSlot(0).getHasStack() || container.getSlot(1).getHasStack()) &&
                (!container.getSlot(2).getHasStack() || !container.getSlot(3).getHasStack())) {
            drawTexturedModalRect(errorX, errorY, 228, 0, 28, 21);
        }

        // draw green animation around slot
        if (container.NEW_INPUT_TOOL && container.getSlot(0).getHasStack()) {
            if (animFrame < 5) {
                drawTexturedModalRect(toolX, toolY, 236, 41, 20, 20);
            } else if (animFrame < 10) {
                drawTexturedModalRect(toolX, toolY, 236, 61, 20, 20);
            } else if (animFrame < 15) {
                drawTexturedModalRect(toolX, toolY, 236, 81, 20, 20);
            } else if (animFrame < 20) {
                drawTexturedModalRect(toolX, toolY, 236, 101, 20, 20);
            } else {
                drawTexturedModalRect(toolX, toolY, 236, 121, 20, 20);
            }
        }

        // draw red box around bad slot(s)
        if (container.BAD_INPUT_TOOL && container.getSlot(0).getHasStack()) {
            drawTexturedModalRect(toolX, toolY, 236, 21, 20, 20);
        }
        if (container.BAD_INPUT_BOOK && container.getSlot(1).getHasStack()) {
            drawTexturedModalRect(bookX, bookY, 236, 21, 20, 20);
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }
}
