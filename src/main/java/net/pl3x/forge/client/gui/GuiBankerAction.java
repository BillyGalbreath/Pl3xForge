package net.pl3x.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.StringUtils;
import net.pl3x.forge.client.ExperienceManager;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.gui.element.Button;
import net.pl3x.forge.client.network.BankPacket;
import net.pl3x.forge.client.network.PacketHandler;
import net.pl3x.forge.client.util.GuiUtil;
import net.pl3x.forge.client.util.Validator;

import java.io.IOException;

public class GuiBankerAction extends GuiContainer {
    private final EntityPlayer player;
    private GuiTextField amountField;
    private ExperienceManager expMan;

    private int action;
    private int iconX;
    private int iconY;
    private int textX;
    private int textY;
    private int x;
    private int y;

    public GuiBankerAction(Container container, EntityPlayer player, int action) {
        super(container);
        this.player = player;
        this.action = action;

        xSize = 80;
        ySize = 61;

        expMan = new ExperienceManager(player);
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        iconX = x + 6;
        iconY = y + 6;
        textX = x + 28;
        textY = y + 10;

        addButton(new Button(0, x + 43, y + 45, 30, 10, "Ok"));

        amountField = new GuiTextField(0, fontRenderer, x + 9, y + 27, 62, 12);
        amountField.setTextColor(-1);
        amountField.setDisabledTextColour(-1);
        amountField.setEnableBackgroundDrawing(true);
        amountField.setMaxStringLength(9);
        amountField.setCanLoseFocus(false);
        amountField.setFocused(true);
        amountField.setValidator(Validator::predicateInteger);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        if (action == BankPacket.DEPOSIT_COIN || action == BankPacket.DEPOSIT_EXP) {
            fontRenderer.drawString("Deposit", textX, textY, 0x404040);
        } else {
            fontRenderer.drawString("Withdraw", textX, textY, 0x404040);
        }

        amountField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);

        if (action == BankPacket.DEPOSIT_COIN || action == BankPacket.WITHDRAW_COIN) {
            GuiUtil.drawTexture(this, GuiUtil.COIN, iconX, iconY, 0, 0, 16, 16, 16, 16);
        } else {
            GuiUtil.drawTexture(this, GuiUtil.EXP_BOTTLE, iconX, iconY, 0, 0, 16, 16, 16, 16);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawScreenOverrideGuiContainer(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    // dont draw any slots at all
    private void drawScreenOverrideGuiContainer(int mouseX, int mouseY, float partialTicks) {
        drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawScreenOverrideGuiScreen(mouseX, mouseY, partialTicks);
        drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    private void drawScreenOverrideGuiScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton aButtonList : buttonList) {
            aButtonList.drawButton(mc, mouseX, mouseY, partialTicks);
        }

        for (GuiLabel aLabelList : labelList) {
            aLabelList.drawLabel(mc, mouseX, mouseY);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            int amount = getAmountEntered();
            switch (action) {
                case BankPacket.DEPOSIT_COIN:
                    if (amount > 0 && amount <= getCurrentCoins()) {
                        PacketHandler.INSTANCE.sendToServer(new BankPacket(amount, BankPacket.DEPOSIT_COIN));
                    }
                    break;
                case BankPacket.WITHDRAW_COIN:
                    if (amount > 0 && amount <= getCurrentBankCoins()) {
                        PacketHandler.INSTANCE.sendToServer(new BankPacket(amount, BankPacket.WITHDRAW_COIN));
                    }
                    break;
                case BankPacket.DEPOSIT_EXP:
                    if (amount > 0 && amount <= expMan.getCurrentExp()) {
                        PacketHandler.INSTANCE.sendToServer(new BankPacket(amount, BankPacket.DEPOSIT_EXP));
                    }
                    break;
                case BankPacket.WITHDRAW_EXP:
                    if (amount > 0 && amount <= getCurrentBankExp()) {
                        PacketHandler.INSTANCE.sendToServer(new BankPacket(amount, BankPacket.WITHDRAW_EXP));
                    }
                    break;
            }
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (amountField.textboxKeyTyped(typedChar, keyCode)) {
            if (StringUtils.isNullOrEmpty(amountField.getText())) {
                amountField.setTextColor(-1);
                return;
            }
            int typed = getAmountEntered();
            if (typed < 0) {
                amountField.setTextColor(0xAA0000);
                return;
            }
            amountField.setTextColor(-1);
            switch (action) {
                case BankPacket.DEPOSIT_COIN:
                    if (typed > getCurrentCoins()) {
                        amountField.setTextColor(0xAA0000);
                    }
                    break;
                case BankPacket.WITHDRAW_COIN:
                    if (typed > getCurrentBankCoins()) {
                        amountField.setTextColor(0xAA0000);
                    }
                    break;
                case BankPacket.DEPOSIT_EXP:
                    if (typed > expMan.getCurrentExp()) {
                        amountField.setTextColor(0xAA0000);
                    }
                    break;
                case BankPacket.WITHDRAW_EXP:
                    if (typed > getCurrentBankExp()) {
                        amountField.setTextColor(0xAA0000);
                    }
                    break;
            }
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private int getAmountEntered() {
        try {
            return Integer.valueOf(amountField.getText());
        } catch (NumberFormatException ignore) {
        }
        return 0;
    }

    private long getCurrentCoins() {
        PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
        if (capability == null) {
            return 0;
        }
        return capability.getCoins();
    }

    private long getCurrentBankCoins() {
        PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
        if (capability == null) {
            return 0;
        }
        return capability.getBankCoins();
    }

    private long getCurrentBankExp() {
        PlayerData capability = player.getCapability(CapabilityProvider.CAPABILITY, null);
        if (capability == null) {
            return 0;
        }
        return capability.getBankExp();
    }
}
