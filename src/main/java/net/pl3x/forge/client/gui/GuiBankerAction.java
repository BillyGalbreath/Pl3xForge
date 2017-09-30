package net.pl3x.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.pl3x.forge.client.ExperienceManager;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.network.BankPacket;
import net.pl3x.forge.client.network.PacketHandler;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiBankerAction extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/banker.png");
    private final EntityPlayer player;
    private int parentXSize;
    private GuiTextField amountField;
    private int action;
    private ExperienceManager expMan;

    public GuiBankerAction(Container container, EntityPlayer player, int action) {
        super(container);
        this.player = player;
        this.action = action;
        this.parentXSize = 176;
        this.xSize = 80;
        this.ySize = 61;

        expMan = new ExperienceManager(player);
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        buttonList.add(new Button(0, x + 43, y + 45, 30, 10, "Ok"));
        amountField = new GuiTextField(0, fontRenderer, x + 9, y + 27, 62, 12);
        amountField.setTextColor(-1);
        amountField.setDisabledTextColour(-1);
        amountField.setEnableBackgroundDrawing(true);
        amountField.setMaxStringLength(9);
        amountField.setCanLoseFocus(false);
        amountField.setFocused(true);
        amountField.setValidator(input -> {
            if (StringUtils.isNullOrEmpty(input)) {
                return true;
            }
            try {
                return Integer.valueOf(input) != null;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        if (action == BankPacket.DEPOSIT_COIN || action == BankPacket.DEPOSIT_EXP) {
            fontRenderer.drawString("Deposit", x + 28, y + 10, 0x404040);
        } else {
            fontRenderer.drawString("Withdraw", x + 28, y + 10, 0x404040);
        }
        amountField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, parentXSize, 0, xSize, ySize);

        if (action == BankPacket.DEPOSIT_COIN || action == BankPacket.WITHDRAW_COIN) {
            drawTexturedModalRect(x + 6, y + 6, parentXSize, 78, 16, 16); // coins icon
        } else {
            drawTexturedModalRect(x + 8, y + 7, parentXSize + 16, 78, 11, 15); // exp icon
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawScreenOverrideGuiContainer(mouseX, mouseY, partialTicks);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    private void drawScreenOverrideGuiContainer(int mouseX, int mouseY, float partialTicks) {
        drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        drawScreenOverrideGuiScreen(mouseX, mouseY, partialTicks);
        drawGuiContainerForegroundLayer(mouseX, mouseY);
    }

    private void drawScreenOverrideGuiScreen(int mouseX, int mouseY, float partialTicks) {
        for (GuiButton aButtonList : this.buttonList) {
            aButtonList.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        for (GuiLabel aLabelList : this.labelList) {
            aLabelList.drawLabel(this.mc, mouseX, mouseY);
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
