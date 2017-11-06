package net.pl3x.forge.gui.shop;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.pl3x.forge.container.ContainerShopCustomer;
import net.pl3x.forge.gui.HUDBalance;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.network.ShopPurchasePacket;
import net.pl3x.forge.util.gl.GuiUtil;

import java.util.List;

public class GuiShopCustomer extends GuiContainer {
    private ContainerShopCustomer container;

    private List<Slot> slotsToDraw;

    private Button purchaseButton;

    private Error error;

    private int slotStart;
    private float itemRot = 0;
    private int itemWidth;
    private int itemHeight;
    private int itemMinX;
    private int itemMaxX;
    private int itemMinY;
    private int itemMaxY;
    private int itemX;
    private int itemY;
    private int coinX;
    private int coinY;
    private int x;
    private int y;

    public GuiShopCustomer(Container container, InventoryPlayer playerInv) {
        super(container);
        this.container = (ContainerShopCustomer) container;

        slotStart = inventorySlots.inventorySlots.size() - playerInv.getSizeInventory() + 5;
        slotsToDraw = inventorySlots.inventorySlots.subList(slotStart, inventorySlots.inventorySlots.size());

        ySize = 195;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        coinX = xSize - 25;
        coinY = 82;

        itemWidth = 81;
        itemHeight = 81;
        itemMinX = x + 7;
        itemMaxX = itemMinX + itemWidth;
        itemMinY = y + 17;
        itemMaxY = itemMinY + itemHeight;
        itemX = x + 45;
        itemY = y + 70;

        purchaseButton = addButton(new Button(0, x + 92, y + 64, 77, 15, "Purchase"));
        purchaseButton.enabled = false;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (container.shop.quantity < 1 ||
                container.shop.stackCount < 1 ||
                container.shop.stackCount < container.shop.quantity) {
            error = Error.SHOP_IS_CLOSED;
        } else {
            if (HUDBalance.balance < container.shop.price) {
                error = Error.INSUFFICIENT_FUNDS;
            } else {
                error = null;
            }
        }

        purchaseButton.enabled = error == null;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = container.shop.stack.getDisplayName();
        fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 6, 6, 0x404040);
        fontRenderer.drawString("Inventory", 8, 101, 0x404040);

        fontRenderer.drawString("Price:", 93, 25, 0x404040);
        fontRenderer.drawStringWithShadow(String.valueOf(container.shop.price), 125, 25, 0xFFFFFF);

        fontRenderer.drawString("Quantity:", 93, 40, 0x404040);
        fontRenderer.drawStringWithShadow(String.valueOf(container.shop.quantity), 139, 40, 0xFFFFFF);

        String balance = String.valueOf(HUDBalance.balance);
        fontRenderer.drawStringWithShadow(balance, xSize - 28 - fontRenderer.getStringWidth(balance), 86, 0xFFFFFF);

        if (error != null) {
            fontRenderer.drawString(error.getMessage(), xSize - error.getWidth() - 8, 101, 0xFF0000);
        }

        GuiUtil.drawTexture(this, GuiUtil.COIN, coinX, coinY, 0, 0, 16, 16, 16, 16);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, slotsToDraw, x, y);
        GuiUtil.drawBlackWindow(this, itemMinX, itemMinY, itemWidth, itemHeight);
        itemRot = GuiUtil.drawItem(this, container.shop.stack, partialTicks, itemX, itemY, 100, itemRot);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks, slotStart);
        renderHoveredToolTip(mouseX, mouseY);
        if (!container.shop.stack.isEmpty() &&
                mouseX > itemMinX && mouseX < itemMaxX &&
                mouseY > itemMinY && mouseY < itemMaxY) {
            renderToolTip(container.shop.stack, mouseX, mouseY);
        }
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // purchase button
        if (button.id == 0) {
            PacketHandler.INSTANCE.sendToServer(new ShopPurchasePacket(container.shop));
        }
    }

    private enum Error {
        SHOP_IS_CLOSED("Shop is Closed"),
        INSUFFICIENT_FUNDS("Insufficient Funds");

        private String message;
        private int width;

        Error(String message) {
            this.message = message;
            width = Minecraft.getMinecraft().fontRenderer.getStringWidth(message);
        }

        public String getMessage() {
            return message;
        }

        public int getWidth() {
            return width;
        }
    }
}
