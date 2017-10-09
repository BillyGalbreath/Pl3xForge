package net.pl3x.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerShopOwner;
import net.pl3x.forge.client.gui.element.Button;
import net.pl3x.forge.client.gui.element.TabButton;
import net.pl3x.forge.client.util.GuiUtil;
import net.pl3x.forge.client.util.Validator;

public class GuiShopOwner extends GuiContainer {
    private ContainerShopOwner container;
    private GuiTextField priceField;
    private GuiTextField qtyField;

    private float itemRot = 0;
    private int itemWidth;
    private int itemHeight;
    private int itemMinX;
    private int itemMaxX;
    private int itemMinY;
    private int itemMaxY;
    private int itemX;
    private int itemY;
    private int x;
    private int y;

    public GuiShopOwner(Container container) {
        super(container);
        this.container = (ContainerShopOwner) container;

        xSize = 234;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        itemWidth = 54;
        itemHeight = 54;
        itemMinX = x + 173;
        itemMaxX = itemMinX + itemWidth;
        itemMinY = y + 7;
        itemMaxY = itemMinY + itemHeight;
        itemX = x + 198;
        itemY = y + 42;

        addButton(new Button(0, x + 173, y + 77, 12, 12, "+"));
        addButton(new Button(1, x + 215, y + 77, 12, 12, "-"));
        addButton(new Button(2, x + 173, y + 102, 12, 12, "+"));
        addButton(new Button(3, x + 215, y + 102, 12, 12, "-"));

        addButton(new TabButton(4, x + xSize - 52, y - 13, 47, 16, "display"));
        addButton(new TabButton(5, x + xSize - 95, y - 13, 40, 16, "funds"));

        priceField = new GuiTextField(1, fontRenderer, 186, 78, 28, 10);
        priceField.setMaxStringLength(4);
        priceField.setValidator(Validator::predicateInteger);

        qtyField = new GuiTextField(0, fontRenderer, 186, 103, 28, 10);
        qtyField.setMaxStringLength(4);
        qtyField.setValidator(Validator::predicateInteger);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        priceField.setText(String.valueOf(container.shop.price));
        qtyField.setText(String.valueOf(container.shop.quantity));

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Inventory", 8, 118, 0x404040);
        fontRenderer.drawString("Quantity:", 173, 93, 0x404040);
        fontRenderer.drawString("Price:", 173, 68, 0x404040);

        priceField.drawTextBox();
        qtyField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, inventorySlots.inventorySlots, x, y);
        GuiUtil.drawBlackWindow(this, itemMinX, itemMinY, itemWidth, itemHeight);
        itemRot = GuiUtil.drawItem(this, container.shop.stack, partialTicks, itemX, itemY, 70, itemRot);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
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
        if (button.id == 0) { // price +
            container.shop.incrementPrice();
        } else if (button.id == 1) { // price -
            container.shop.decrementPrice();
        } else if (button.id == 2) { // qty +
            container.shop.incrementQuantity();
        } else if (button.id == 3) { // qty -
            container.shop.decrementQuantity();
        } else if (button.id == 4) { // display tab
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.SHOP_OWNER_DISPLAY,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        } else if (button.id == 5) { // funds tab
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.SHOP_OWNER_FUNDS,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
