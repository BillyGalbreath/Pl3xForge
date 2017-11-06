package net.pl3x.forge.gui.banker;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.container.ContainerBanker;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.gui.element.LockButton;
import net.pl3x.forge.inventory.SlotBanker;
import net.pl3x.forge.network.BankPacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.util.gl.GuiUtil;

public class GuiBanker extends GuiContainer {
    private final ContainerBanker container;

    private final String failed = "Insufficient Funds";

    private int failedX;
    private int coinX;
    private int coinY;
    private int expX;
    private int expY;
    private int x;
    private int y;

    public GuiBanker(Container container) {
        super(container);
        this.container = (ContainerBanker) inventorySlots;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        failedX = xSize - fontRenderer.getStringWidth(failed) - 8;
        coinX = x + 6;
        coinY = y + 35;
        expX = x + 9;
        expY = y + 85;

        buttonList.add(new Button(0, x + 8, y + 55, 30, 10, "+"));
        buttonList.add(new Button(1, x + 43, y + 55, 30, 10, "-"));
        buttonList.add(new Button(2, x + 8, y + 105, 30, 10, "+"));
        buttonList.add(new Button(3, x + 43, y + 105, 30, 10, "-"));

        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 5; ++j) {
                int slot = j + i * 5;
                buttonList.add(new LockButton(container, slot, 100 + slot, x + 79 + j * 18, y + 7 + i * 18));
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Banker", 8, 8, 0x404040);
        fontRenderer.drawString("Inventory", 8, 118, 0x404040);
        fontRenderer.drawString(String.valueOf(container.getCoins()), 25, 40, 0xFFAA00);
        fontRenderer.drawString(String.valueOf(container.getExp()), 25, 90, 0x00AA00);

        if (container.increaseBankSlotsFailed) {
            fontRenderer.drawString(failed, failedX, 118, 0xFF0000);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, inventorySlots.inventorySlots, x, y);

        GuiUtil.drawTexture(this, GuiUtil.COIN, coinX, coinY, 0, 0, 16, 16, 16, 16);
        GuiUtil.drawTexture(this, GuiUtil.EXP_BOTTLE, expX, expY, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        renderHoveredPurchasableSlot(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0: // coin add
                mc.player.openGui(Pl3x.instance, ModGuiHandler.BANKER_DEPOSIT_COIN, mc.player.world, 0, 0, 0);
                break;
            case 1: // coin subtract
                mc.player.openGui(Pl3x.instance, ModGuiHandler.BANKER_WITHDRAW_COIN, mc.player.world, 0, 0, 0);
                break;
            case 2: // exp add
                mc.player.openGui(Pl3x.instance, ModGuiHandler.BANKER_DEPOSIT_EXP, mc.player.world, 0, 0, 0);
                break;
            case 3: // exp subtract
                mc.player.openGui(Pl3x.instance, ModGuiHandler.BANKER_WITHDRAW_EXP, mc.player.world, 0, 0, 0);
                break;
            default:
                for (int i = 0; i < 30; i++) {
                    if (button.id == i + 100 && ((SlotBanker) container.getSlot(i)).isPurchasable()) {
                        PacketHandler.INSTANCE.sendToServer(new BankPacket(1, BankPacket.SLOTS_INCREASE));
                        return;
                    }
                }
        }
    }

    private void renderHoveredPurchasableSlot(int mouseX, int mouseY) {
        if (mc.player.inventory.getItemStack().isEmpty()) {
            SlotBanker hoveredSlot = getPurchasableSlotUnderMouse(mouseX, mouseY);
            if (hoveredSlot != null) {
                drawHoveringText(hoveredSlot.getTooltipText(), mouseX, mouseY);
            }
        }
    }

    private SlotBanker getPurchasableSlotUnderMouse(int mouseX, int mouseY) {
        for (int i = 0; i < 30; ++i) {
            SlotBanker slot = (SlotBanker) inventorySlots.inventorySlots.get(i);
            if (slot.isPurchasable() && isPointInRegion(slot.xPos, slot.yPos, 16, 16, mouseX, mouseY)) {
                return slot;
            }
        }
        return null;
    }
}
