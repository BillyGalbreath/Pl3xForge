package net.pl3x.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerBanker;
import net.pl3x.forge.client.inventory.SlotBanker;
import net.pl3x.forge.client.network.BankPacket;
import net.pl3x.forge.client.network.PacketHandler;

import java.util.List;

public class GuiBanker extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/banker.png");
    private final ContainerBanker container;
    private InventoryPlayer playerInv;

    public GuiBanker(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
        this.container = (ContainerBanker) inventorySlots;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
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
        String invName = "Banker";
        fontRenderer.drawString(invName, 8, 8, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
        fontRenderer.drawString(String.valueOf(container.getCoins()), 25, 40, 0xFFAA00);
        fontRenderer.drawString(String.valueOf(container.getExp()), 25, 90, 0x00AA00);

        if (container.increaseBankSlotsFailed) {
            String failed = "Expansion Failed";
            fontRenderer.drawString(failed, xSize - fontRenderer.getStringWidth(failed) - 8, ySize - 94, 0xAA0000);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        drawTexturedModalRect(x + 6, y + 35, xSize, 78, 16, 16); // coins icon
        drawTexturedModalRect(x + 9, y + 85, xSize + 16, 78, 11, 15); // exp icon
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
                mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.BANKER_DEPOSIT_COIN, mc.player.world, 0, 0, 0);
                break;
            case 1: // coin subtract
                mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.BANKER_WITHDRAW_COIN, mc.player.world, 0, 0, 0);
                break;
            case 2: // exp add
                mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.BANKER_DEPOSIT_EXP, mc.player.world, 0, 0, 0);
                break;
            case 3: // exp subtract
                mc.player.openGui(Pl3xForgeClient.instance, ModGuiHandler.BANKER_WITHDRAW_EXP, mc.player.world, 0, 0, 0);
                break;
            default:
                for (int i = 0; i < 30; i++) {
                    if (button.id == i + 100 && ((SlotBanker) container.getSlot(i)).isPurchasable()) {
                        increaseSlots();
                    }
                }
        }
    }

    private void increaseSlots() {
        PacketHandler.INSTANCE.sendToServer(new BankPacket(1, BankPacket.SLOTS_INCREASE));
    }

    private void renderHoveredPurchasableSlot(int mouseX, int mouseY) {
        SlotBanker hoveredSlot = getPurchasableSlotUnderMouse(mouseX, mouseY);
        if (hoveredSlot == null) {
            return; // not hovering a purchasable slot
        }
        drawLockTooltipText(hoveredSlot.getTooltipText(), mouseX, mouseY);
    }

    private void drawLockTooltipText(List<String> textLines, int x, int y) {
        if (textLines.isEmpty()) {
            return;
        }

        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        int i = 0;

        for (String s : textLines) {
            int j = this.fontRenderer.getStringWidth(s);
            if (j > i) {
                i = j;
            }
        }

        int l1 = x + 12;
        int i2 = y - 12;
        int k = 8;

        if (textLines.size() > 1) {
            k += 2 + (textLines.size() - 1) * 10;
        }

        if (l1 + i > this.width) {
            l1 -= 28 + i;
        }

        if (i2 + k + 6 > this.height) {
            i2 = this.height - k - 6;
        }

        this.zLevel = 300.0F;
        this.itemRender.zLevel = 300.0F;
        this.drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, -267386864, -267386864);
        this.drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, -267386864, -267386864);
        this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, -267386864, -267386864);
        this.drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, -267386864, -267386864);
        this.drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, -267386864, -267386864);
        this.drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, 1347420415, 1344798847);
        this.drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, 1347420415, 1344798847);
        this.drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, 1347420415, 1347420415);
        this.drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, 1344798847, 1344798847);

        for (int k1 = 0; k1 < textLines.size(); ++k1) {
            String s1 = textLines.get(k1);
            this.fontRenderer.drawStringWithShadow(s1, (float) l1, (float) i2, -1);

            if (k1 == 0) {
                i2 += 2;
            }

            i2 += 10;
        }

        this.zLevel = 0.0F;
        this.itemRender.zLevel = 0.0F;
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableRescaleNormal();
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
