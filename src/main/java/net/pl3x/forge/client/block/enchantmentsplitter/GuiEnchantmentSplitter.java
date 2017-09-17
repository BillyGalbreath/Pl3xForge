package net.pl3x.forge.client.block.enchantmentsplitter;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.block.ModBlocks;

public class GuiEnchantmentSplitter extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/enchantment_splitter.png");
    private final ContainerEnchantmentSplitter splitter;
    private InventoryPlayer playerInv;

    public GuiEnchantmentSplitter(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.splitter = (ContainerEnchantmentSplitter) inventorySlots;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name;
        TileEntity te = playerInv.player.world.getTileEntity(splitter.selfPosition);
        if (te instanceof TileEntityEnchantmentSplitter && ((TileEntityEnchantmentSplitter) te).hasCustomName()) {
            name = ((TileEntityEnchantmentSplitter) te).getName();
        } else {
            name = I18n.format(ModBlocks.enchantmentSplitter.getUnlocalizedName() + ".name");
        }
        fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 6, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);

        if (splitter.getSlot(2).getHasStack() &&
                splitter.getSlot(3).getHasStack()) {
            int color = 8453920; // green
            String text = "Split Cost: 3";

            if (!splitter.getSlot(2).canTakeStack(playerInv.player)) {
                color = 16736352; // red
            }

            int shadowColor = -16777216 | (color & 16579836) >> 2 | color & -16777216;
            int xPos = xSize - 8 - fontRenderer.getStringWidth(text);

            if (fontRenderer.getUnicodeFlag()) {
                drawRect(xPos - 3, 65, xSize - 7, 77, -16777216);
                drawRect(xPos - 2, 66, xSize - 8, 76, -12895429);
            } else {
                fontRenderer.drawString(text, xPos, 68, shadowColor);
                fontRenderer.drawString(text, xPos + 1, 67, shadowColor);
                fontRenderer.drawString(text, xPos + 1, 68, shadowColor);
            }

            fontRenderer.drawString(text, xPos, 67, color);
        }

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        if (splitter.getSlot(0).getHasStack() && splitter.getSlot(1).getHasStack() &&
                (!splitter.getSlot(2).getHasStack() || !splitter.getSlot(3).getHasStack())) {
            drawTexturedModalRect(x + 101, y + 32, xSize, 0, 28, 21);
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
