package net.pl3x.forge.gui.shop;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.BlockPos;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.container.ContainerShopOwner;
import net.pl3x.forge.gui.HUDBalance;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.gui.element.TabButton;
import net.pl3x.forge.util.gl.GuiUtil;

import java.util.List;

public class GuiShopOwnerFunds extends GuiContainer {
    private final ContainerShopOwner container;

    private final List<Slot> slotsToDraw;

    private GuiTextField shopCoinField;
    private GuiTextField playerCoinField;

    private String strShop;
    private String strName;

    private final int slotStart;
    private int strShopX;
    private int strNameX;
    private int strInvY;
    private int coinX;
    private int coinY;
    private int x;
    private int y;

    public GuiShopOwnerFunds(Container container, InventoryPlayer playerInv) {
        super(container);
        this.container = (ContainerShopOwner) container;

        slotStart = inventorySlots.inventorySlots.size() - playerInv.getSizeInventory() + 5;
        slotsToDraw = inventorySlots.inventorySlots.subList(slotStart, inventorySlots.inventorySlots.size());

        xSize = 234;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;
        coinX = x + xSize / 2 - 15;
        coinY = y + 17;

        strShop = "Shop";
        strName = mc.player.getName();
        strShopX = (int) (xSize * 0.25 - mc.fontRenderer.getStringWidth(strShop) / 2);
        strNameX = (int) (xSize * 0.75 - mc.fontRenderer.getStringWidth(strName) / 2);
        strInvY = ySize - 94;

        addButton(new TabButton(0, x + xSize - 52, y - 13, 47, 16, "display"));
        addButton(new TabButton(1, x + xSize - 95, y - 13, 40, 16, "funds")).selected = true;
        addButton(new Button(2, x + xSize - 50, y + 100, 40, 12, "Back"));
        addButton(new Button(3, x + xSize / 2 - 10, y + 62, 20, 14, "<<"));
        addButton(new Button(4, x + xSize / 2 - 10, y + 77, 20, 14, ">>"));

        shopCoinField = new GuiTextField(0, mc.fontRenderer, 15, 70, 80, 12);
        playerCoinField = new GuiTextField(0, mc.fontRenderer, 139, 70, 80, 12);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        shopCoinField.setText(String.valueOf(container.shop.coins));
        playerCoinField.setText(String.valueOf(HUDBalance.balance));

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Inventory", 8, strInvY, 0x404040);
        fontRenderer.drawString(strShop, strShopX, 55, 0x404040);
        fontRenderer.drawString(strName, strNameX, 55, 0x404040);

        shopCoinField.drawTextBox();
        playerCoinField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, slotsToDraw, x, y);
        GuiUtil.drawTexture(this, GuiUtil.COIN, coinX, coinY, 0, 0, 30, 30, 30, 30);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks, slotStart);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // display tab
        if (button.id == 0) {
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3x.instance, ModGuiHandler.SHOP_OWNER_DISPLAY,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        }

        // back button
        else if (button.id == 2) {
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3x.instance, ModGuiHandler.SHOP_OWNER,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        }

        // << button (player to shop)
        else if (button.id == 3) {
            container.shop.addCoin();
        }

        // >> button (shop to player)
        else if (button.id == 4) {
            container.shop.removeCoin();
        }
    }
}
