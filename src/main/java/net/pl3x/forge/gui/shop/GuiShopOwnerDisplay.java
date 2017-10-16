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
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.gui.element.TabButton;
import net.pl3x.forge.util.GuiUtil;
import net.pl3x.forge.util.Validator;

import java.util.List;

public class GuiShopOwnerDisplay extends GuiContainer {
    private ContainerShopOwner container;

    private List<Slot> slotsToDraw;

    private GuiTextField scaleField;
    private GuiTextField yOffsetField;
    private GuiTextField xRotField;
    private GuiTextField yRotField;
    private GuiTextField zRotField;

    private int slotStart;
    private int x;
    private int y;

    public GuiShopOwnerDisplay(Container container, InventoryPlayer playerInv) {
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

        addButton(new TabButton(0, x + xSize - 52, y - 13, 47, 16, "display")).selected = true;
        addButton(new TabButton(1, x + xSize - 95, y - 13, 40, 16, "funds"));

        addButton(new Button(2, x + xSize - 50, y + 100, 40, 12, "Back"));

        scaleField = new GuiTextField(0, fontRenderer, 70, 18, 128, 10); // 20
        scaleField.setValidator(Validator::predicateDouble);
        addButton(new Button(3, x + xSize - 19, y + 17, 12, 12, "+"));
        addButton(new Button(4, x + xSize - 33, y + 17, 12, 12, "-"));

        yOffsetField = new GuiTextField(1, fontRenderer, 70, 33, 128, 10); //35
        yOffsetField.setValidator(Validator::predicateDouble);
        addButton(new Button(5, x + xSize - 19, y + 32, 12, 12, "+"));
        addButton(new Button(6, x + xSize - 33, y + 32, 12, 12, "-"));

        xRotField = new GuiTextField(2, fontRenderer, 70, 48, 128, 10); // 50
        xRotField.setValidator(Validator::predicateInteger);
        addButton(new Button(7, x + xSize - 19, y + 47, 12, 12, "+"));
        addButton(new Button(8, x + xSize - 33, y + 47, 12, 12, "-"));

        yRotField = new GuiTextField(3, fontRenderer, 70, 63, 128, 10); // 65
        yRotField.setValidator(Validator::predicateInteger);
        addButton(new Button(9, x + xSize - 19, y + 62, 12, 12, "+"));
        addButton(new Button(10, x + xSize - 33, y + 62, 12, 12, "-"));

        zRotField = new GuiTextField(4, fontRenderer, 70, 78, 128, 10); // 80
        zRotField.setValidator(Validator::predicateInteger);
        addButton(new Button(11, x + xSize - 19, y + 77, 12, 12, "+"));
        addButton(new Button(12, x + xSize - 33, y + 77, 12, 12, "-"));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        scaleField.setText(String.valueOf(container.shop.display_scale));
        yOffsetField.setText(String.valueOf(container.shop.display_yOffset));
        xRotField.setText(String.valueOf(container.shop.display_rotateX));
        yRotField.setText(String.valueOf(container.shop.display_rotateY));
        zRotField.setText(String.valueOf(container.shop.display_rotateZ));

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("Inventory", 8, 118, 0x404040);

        fontRenderer.drawString("Scale", 8, 20, 0x404040);
        fontRenderer.drawString("Y-Offset", 8, 35, 0x404040);
        fontRenderer.drawString("X Rotation", 8, 50, 0x404040);
        fontRenderer.drawString("Y Rotation", 8, 65, 0x404040);
        fontRenderer.drawString("Z Rotation", 8, 80, 0x404040);

        scaleField.drawTextBox();
        yOffsetField.drawTextBox();
        xRotField.drawTextBox();
        yRotField.drawTextBox();
        zRotField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, slotsToDraw, x, y);
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
        // funds tab
        if (button.id == 1) {
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3x.instance, ModGuiHandler.SHOP_OWNER_FUNDS,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        }

        // back button
        else if (button.id == 2) {
            BlockPos pos = container.shop.getPos();
            mc.player.openGui(Pl3x.instance, ModGuiHandler.SHOP_OWNER,
                    container.shop.getWorld(), pos.getX(), pos.getY(), pos.getZ());
        }

        // +/- buttons
        else if (button.id == 3) {
            container.shop.incrementScale();
        } else if (button.id == 4) {
            container.shop.decrementScale();
        } else if (button.id == 5) {
            container.shop.incrementYOffset();
        } else if (button.id == 6) {
            container.shop.decrementYOffset();
        } else if (button.id == 7) {
            container.shop.incrementRotX();
        } else if (button.id == 8) {
            container.shop.decrementRotX();
        } else if (button.id == 9) {
            container.shop.incrementRotY();
        } else if (button.id == 10) {
            container.shop.decrementRotY();
        } else if (button.id == 11) {
            container.shop.incrementRotZ();
        } else if (button.id == 12) {
            container.shop.decrementRotZ();
        }
    }
}
