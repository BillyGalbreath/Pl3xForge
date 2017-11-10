package net.pl3x.forge.gui.armorstand;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.util.math.Rotations;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.container.ContainerArmorstand;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.gui.element.Button;
import net.pl3x.forge.gui.element.CheckBox;
import net.pl3x.forge.gui.element.IntegerInput;
import net.pl3x.forge.gui.element.TabButton;
import net.pl3x.forge.network.ArmorStandPacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.util.gl.GuiUtil;

import java.io.IOException;
import java.util.List;

public class GuiArmorStandDisplay extends GuiContainer {
    private final ContainerArmorstand container;
    private final EntityPlayer player;
    private final int entityId;

    private final List<Slot> slotsToDraw;
    private final int slotStart;

    private int x;
    private int y;
    private float oldMouseX;
    private float oldMouseY;

    private boolean drag = false;
    private int dragX;
    private int dragY;
    private int dragXDiff;
    private int dragYDiff;
    private int rotX;
    private int rotY;

    private CheckBox baby;
    private CheckBox arms;
    private CheckBox base;
    private IntegerInput rotate;
    private IntegerInput headX;
    private IntegerInput headY;
    private IntegerInput headZ;
    private IntegerInput bodyX;
    private IntegerInput bodyY;
    private IntegerInput bodyZ;
    private IntegerInput leftLegX;
    private IntegerInput leftLegY;
    private IntegerInput leftLegZ;
    private IntegerInput rightLegX;
    private IntegerInput rightLegY;
    private IntegerInput rightLegZ;
    private IntegerInput leftArmX;
    private IntegerInput leftArmY;
    private IntegerInput leftArmZ;
    private IntegerInput rightArmX;
    private IntegerInput rightArmY;
    private IntegerInput rightArmZ;

    public GuiArmorStandDisplay(Container container, EntityPlayer player, int entityId) {
        super(container);
        this.container = (ContainerArmorstand) container;
        this.player = player;
        this.entityId = entityId;

        slotStart = inventorySlots.inventorySlots.size() - player.inventory.getSizeInventory() + 5;
        slotsToDraw = inventorySlots.inventorySlots.subList(slotStart, inventorySlots.inventorySlots.size());

        xSize = 214;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2 - 19;
        y = (height - ySize) / 2;

        guiLeft -= 19;

        addButton(new TabButton(0, x + xSize - 46, y - 13, 40, 16, "items"));
        addButton(new TabButton(1, x + xSize - 91, y - 13, 47, 16, "display")).selected = true;

        addButton(baby = new CheckBox(5, x + 7, y + 7, false));
        addButton(arms = new CheckBox(6, x + 47, y + 7, false));
        addButton(base = new CheckBox(7, x + 87, y + 7, true));

        int x1 = x + 45;
        int x2 = x + 99;
        int x3 = x + 153;
        int id = 8;

        // rotation
        addButton(rotate = new IntegerInput(id, x1, y + 20, 53, 10, 0, 359, 0, true, fontRenderer));

        // head
        addButton(headX = new IntegerInput(++id, x1, y + 33, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(headY = new IntegerInput(++id, x2, y + 33, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(headZ = new IntegerInput(++id, x3, y + 33, 53, 10, 0, 359, 0, true, fontRenderer));

        // body
        addButton(bodyX = new IntegerInput(++id, x1, y + 46, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(bodyY = new IntegerInput(++id, x2, y + 46, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(bodyZ = new IntegerInput(++id, x3, y + 46, 53, 10, 0, 359, 0, true, fontRenderer));

        // left leg
        addButton(leftLegX = new IntegerInput(++id, x1, y + 59, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftLegY = new IntegerInput(++id, x2, y + 59, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftLegZ = new IntegerInput(++id, x3, y + 59, 53, 10, 0, 359, 0, true, fontRenderer));

        // right leg
        addButton(rightLegX = new IntegerInput(++id, x1, y + 72, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightLegY = new IntegerInput(++id, x2, y + 72, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightLegZ = new IntegerInput(++id, x3, y + 72, 53, 10, 0, 359, 0, true, fontRenderer));

        // left arm
        addButton(leftArmX = new IntegerInput(++id, x1, y + 85, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftArmY = new IntegerInput(++id, x2, y + 85, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftArmZ = new IntegerInput(++id, x3, y + 85, 53, 10, 0, 359, 0, true, fontRenderer));

        // right arm
        addButton(rightArmX = new IntegerInput(++id, x1, y + 98, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightArmY = new IntegerInput(++id, x2, y + 98, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightArmZ = new IntegerInput(++id, x3, y + 98, 53, 10, 0, 359, 0, true, fontRenderer));

        initialValues();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
            if (button instanceof IntegerInput && ((IntegerInput) button).tick()) {
                actionPerformed(button);
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("ArmorStand", 148, 8, 0x404040);
        fontRenderer.drawString("Inventory", 27, 118, 0x404040);

        fontRenderer.drawString("Baby", 20, 8, 0x404040);
        fontRenderer.drawString("Arms", 60, 8, 0x404040);
        fontRenderer.drawString("Base", 100, 8, 0x404040);

        fontRenderer.drawString("Rotate", 8, 22, 0x404040);
        fontRenderer.drawString("Head", 8, 35, 0x404040);
        fontRenderer.drawString("Body", 8, 48, 0x404040);
        fontRenderer.drawString("L Leg", 8, 61, 0x404040);
        fontRenderer.drawString("R Leg", 8, 74, 0x404040);
        fontRenderer.drawString("L Arm", 8, 87, 0x404040);
        fontRenderer.drawString("R Arm", 8, 100, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, slotsToDraw, x, y);
        drawEntityOnScreen(x + 275, y + 175, 60, container.armorStand);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks, slotStart);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // items tab
        if (button.id == 0) {
            mc.player.openGui(Pl3x.instance, ModGuiHandler.ARMOR_STAND,
                    mc.player.world, 0, 0, 0, entityId);
        }

        // rotation buttons
        else if (button.id >= 5) {
            updateValues();
            PacketHandler.INSTANCE.sendToServer(new ArmorStandPacket(container.armorStand));
        }
    }

    private void initialValues() {
        baby.setChecked(container.armorStand.isSmall());
        arms.setChecked(container.armorStand.getShowArms());
        base.setChecked(!container.armorStand.hasNoBasePlate());
        rotate.setValue((int) container.armorStand.rotationYaw);
        headX.setValue((int) container.armorStand.getHeadRotation().getX());
        headY.setValue((int) container.armorStand.getHeadRotation().getY());
        headZ.setValue((int) container.armorStand.getHeadRotation().getZ());
        bodyX.setValue((int) container.armorStand.getBodyRotation().getX());
        bodyY.setValue((int) container.armorStand.getBodyRotation().getY());
        bodyZ.setValue((int) container.armorStand.getBodyRotation().getZ());
        leftLegX.setValue((int) container.armorStand.getLeftLegRotation().getX());
        leftLegY.setValue((int) container.armorStand.getLeftLegRotation().getY());
        leftLegZ.setValue((int) container.armorStand.getLeftLegRotation().getZ());
        rightLegX.setValue((int) container.armorStand.getRightLegRotation().getX());
        rightLegY.setValue((int) container.armorStand.getRightLegRotation().getY());
        rightLegZ.setValue((int) container.armorStand.getRightLegRotation().getZ());
        leftArmX.setValue((int) container.armorStand.getLeftArmRotation().getX());
        leftArmY.setValue((int) container.armorStand.getLeftArmRotation().getY());
        leftArmZ.setValue((int) container.armorStand.getLeftArmRotation().getZ());
        rightArmX.setValue((int) container.armorStand.getRightArmRotation().getX());
        rightArmY.setValue((int) container.armorStand.getRightArmRotation().getY());
        rightArmZ.setValue((int) container.armorStand.getRightArmRotation().getZ());
    }

    private void updateValues() {
        container.armorStand.setSmall(baby.isChecked());
        container.armorStand.setShowArms(arms.isChecked());
        container.armorStand.setNoBasePlate(!base.isChecked());
        container.armorStand.rotationYaw = rotate.getValue();
        container.armorStand.setHeadRotation(new Rotations(headX.getValue(), headY.getValue(), headZ.getValue()));
        container.armorStand.setBodyRotation(new Rotations(bodyX.getValue(), bodyY.getValue(), bodyZ.getValue()));
        container.armorStand.setLeftLegRotation(new Rotations(leftLegX.getValue(), leftLegY.getValue(), leftLegZ.getValue()));
        container.armorStand.setRightLegRotation(new Rotations(rightLegX.getValue(), rightLegY.getValue(), rightLegZ.getValue()));
        container.armorStand.setLeftArmRotation(new Rotations(leftArmX.getValue(), leftArmY.getValue(), leftArmZ.getValue()));
        container.armorStand.setRightArmRotation(new Rotations(rightArmX.getValue(), rightArmY.getValue(), rightArmZ.getValue()));
    }

    public void drawEntityOnScreen(int posX, int posY, int scale, EntityArmorStand armorstand) {
        // setup GL
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();

        // move into place and size
        GlStateManager.translate((float) posX, (float) posY, 150.0F);
        GlStateManager.scale((float) -scale, (float) scale, (float) scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        // fix shadows/lighting angle
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

        // rotate
        GlStateManager.rotate(-rotX / 5F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-rotY / 5F, 1.0F, 0.0F, 0.0F);

        // stand upright
        GlStateManager.translate(0.0F, 0.0F, 0.0F);

        // render
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(armorstand, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);

        // cleanup GL
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        //GuiUtil.drawBlackWindow(this, x + 213, y + 25, 140, 180);
        if (mouseX > x + 213 && mouseX <= x + 353 && mouseY > y + 25 && mouseY < y + 205) {
            drag = true;
            dragX = mouseX;
            dragY = mouseY;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        drag = false;
        dragX = 0;
        dragY = 0;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (drag) {
            rotX += dragX - mouseX;
            rotY += dragY - mouseY;
            dragX = mouseX;
            dragY = mouseY;
        }
    }
}
