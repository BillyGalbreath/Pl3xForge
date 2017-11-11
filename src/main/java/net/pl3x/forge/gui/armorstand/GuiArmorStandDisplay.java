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
import net.pl3x.forge.network.ArmorStandChangePacket;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.util.gl.GuiUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiArmorStandDisplay extends GuiContainer {
    private final ContainerArmorstand container;
    private final EntityPlayer player;
    private final int entityId;
    private final int dimension;

    private final Map<Integer, Integer> queuedPackets = new HashMap<>();

    private final List<Slot> slotsToDraw;
    private final int slotStart;

    private int x;
    private int y;

    private boolean drag = false;
    private boolean held = false;
    private int dragX;
    private int dragY;
    private int rotX;
    private int rotY;

    private final int xOffset;
    private final int doubleOffset;

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
        this.dimension = this.container.armorStand.dimension;

        this.xOffset = this.container.xOffset;
        this.doubleOffset = xOffset * 2;

        slotStart = inventorySlots.inventorySlots.size() - player.inventory.getSizeInventory() + 5;
        slotsToDraw = inventorySlots.inventorySlots.subList(slotStart, inventorySlots.inventorySlots.size());

        xSize = 176 + xOffset * 2;
        ySize = 202;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2 - xOffset;
        y = (height - ySize) / 2;

        guiLeft -= xOffset;

        addButton(new TabButton(0, x + xSize - 46, y - 13, 40, 16, "items"));
        addButton(new TabButton(1, x + xSize - 91, y - 13, 47, 16, "display")).selected = true;

        addButton(baby = new CheckBox(5, x + 10, y + 125, "Baby", false));
        addButton(arms = new CheckBox(6, x + 10, y + 145, "Arms", false));
        addButton(base = new CheckBox(7, x + 10, y + 165, "Base", true));

        int x1 = x + 8 + doubleOffset;
        int x2 = x1 + 54;
        int x3 = x2 + 54;
        int id = 8;

        // rotation
        addButton(rotate = new IntegerInput(id, x1, y + 10, 53, 10, 0, 359, 0, true, fontRenderer));

        // head
        addButton(headX = new IntegerInput(++id, x1, y + 23, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(headY = new IntegerInput(++id, x2, y + 23, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(headZ = new IntegerInput(++id, x3, y + 23, 53, 10, 0, 359, 0, true, fontRenderer));

        // body
        addButton(bodyX = new IntegerInput(++id, x1, y + 36, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(bodyY = new IntegerInput(++id, x2, y + 36, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(bodyZ = new IntegerInput(++id, x3, y + 36, 53, 10, 0, 359, 0, true, fontRenderer));

        // left leg
        addButton(leftLegX = new IntegerInput(++id, x1, y + 49, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftLegY = new IntegerInput(++id, x2, y + 49, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftLegZ = new IntegerInput(++id, x3, y + 49, 53, 10, 0, 359, 0, true, fontRenderer));

        // right leg
        addButton(rightLegX = new IntegerInput(++id, x1, y + 62, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightLegY = new IntegerInput(++id, x2, y + 62, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightLegZ = new IntegerInput(++id, x3, y + 62, 53, 10, 0, 359, 0, true, fontRenderer));

        // left arm
        addButton(leftArmX = new IntegerInput(++id, x1, y + 75, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftArmY = new IntegerInput(++id, x2, y + 75, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(leftArmZ = new IntegerInput(++id, x3, y + 75, 53, 10, 0, 359, 0, true, fontRenderer));

        // right arm
        addButton(rightArmX = new IntegerInput(++id, x1, y + 88, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightArmY = new IntegerInput(++id, x2, y + 88, 53, 10, 0, 359, 0, true, fontRenderer));
        addButton(rightArmZ = new IntegerInput(++id, x3, y + 88, 53, 10, 0, 359, 0, true, fontRenderer));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (container.armorStand == null || container.armorStand.isDead) {
            player.closeScreen();
            return;
        }

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

        for (GuiButton button : buttonList) {
            if (button instanceof Button && ((Button) button).tick()) {
                actionPerformed(button);
            }
            if (button instanceof IntegerInput && ((IntegerInput) button).tick()) {
                actionPerformed(button);
            }
        }

        if (!held) {
            queuedPackets.forEach((k, v) -> PacketHandler.INSTANCE.sendToServer(new ArmorStandChangePacket(entityId, dimension, k, v)));
            queuedPackets.clear();
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("ArmorStand", xSize - 8 - fontRenderer.getStringWidth("ArmorStand"), 8, 0x404040);
        fontRenderer.drawString("Inventory", 8 + doubleOffset, 108, 0x404040);

        fontRenderer.drawString("Rotate", 8, 12, 0x404040);
        fontRenderer.drawString("Head", 8, 25, 0x404040);
        fontRenderer.drawString("Body", 8, 38, 0x404040);
        fontRenderer.drawString("Left Leg", 8, 51, 0x404040);
        fontRenderer.drawString("Right Leg", 8, 64, 0x404040);
        fontRenderer.drawString("Left Arm", 8, 77, 0x404040);
        fontRenderer.drawString("Right Arm", 8, 90, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, slotsToDraw, x, y);
        drawEntityOnScreen(x + 275 + xOffset, y + 165, container.armorStand);
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
        // items tab
        if (button.id == 0) {
            mc.player.openGui(Pl3x.instance, ModGuiHandler.ARMOR_STAND,
                    mc.player.world, 0, 0, 0, entityId);
        }

        // checkboxes
        if (button.id == 5) { // baby
            boolean value = !container.armorStand.isSmall();
            container.armorStand.setSmall(value);
            PacketHandler.INSTANCE.sendToServer(new ArmorStandChangePacket(entityId, dimension, 5, value ? 1 : 0));
        } else if (button.id == 6) { // arms
            boolean value = !container.armorStand.getShowArms();
            container.armorStand.setShowArms(value);
            PacketHandler.INSTANCE.sendToServer(new ArmorStandChangePacket(entityId, dimension, 6, value ? 1 : 0));
        } else if (button.id == 7) { // base
            boolean value = !container.armorStand.hasNoBasePlate();
            container.armorStand.setNoBasePlate(value);
            PacketHandler.INSTANCE.sendToServer(new ArmorStandChangePacket(entityId, dimension, 7, !value ? 1 : 0));
        }

        // rotation buttons
        else if (button.id >= 8) {
            if (button.id == 8) { // rotate
                container.armorStand.rotationYaw = rotate.getValue();
                queuedPackets.put(8, rotate.getValue());
            } else if (button.id == 9) { // head x
                container.armorStand.setHeadRotation(new Rotations(headX.getValue(), headY.getValue(), headZ.getValue()));
                queuedPackets.put(9, headX.getValue());
            } else if (button.id == 10) { // head y
                container.armorStand.setHeadRotation(new Rotations(headX.getValue(), headY.getValue(), headZ.getValue()));
                queuedPackets.put(10, headY.getValue());
            } else if (button.id == 11) { // head z
                container.armorStand.setHeadRotation(new Rotations(headX.getValue(), headY.getValue(), headZ.getValue()));
                queuedPackets.put(11, headZ.getValue());
            } else if (button.id == 12) { // body x
                container.armorStand.setBodyRotation(new Rotations(bodyX.getValue(), bodyY.getValue(), bodyZ.getValue()));
                queuedPackets.put(12, bodyX.getValue());
            } else if (button.id == 13) { // body y
                container.armorStand.setBodyRotation(new Rotations(bodyX.getValue(), bodyY.getValue(), bodyZ.getValue()));
                queuedPackets.put(13, bodyY.getValue());
            } else if (button.id == 14) { // body z
                container.armorStand.setBodyRotation(new Rotations(bodyX.getValue(), bodyY.getValue(), bodyZ.getValue()));
                queuedPackets.put(14, bodyZ.getValue());
            } else if (button.id == 15) { // left leg x
                container.armorStand.setLeftLegRotation(new Rotations(leftLegX.getValue(), leftLegY.getValue(), leftLegZ.getValue()));
                queuedPackets.put(15, leftLegX.getValue());
            } else if (button.id == 16) { // left leg y
                container.armorStand.setLeftLegRotation(new Rotations(leftLegX.getValue(), leftLegY.getValue(), leftLegZ.getValue()));
                queuedPackets.put(16, leftLegY.getValue());
            } else if (button.id == 17) { // left leg z
                container.armorStand.setLeftLegRotation(new Rotations(leftLegX.getValue(), leftLegY.getValue(), leftLegZ.getValue()));
                queuedPackets.put(17, leftLegZ.getValue());
            } else if (button.id == 18) { // right leg x
                container.armorStand.setRightLegRotation(new Rotations(rightLegX.getValue(), rightLegY.getValue(), rightLegZ.getValue()));
                queuedPackets.put(18, rightLegX.getValue());
            } else if (button.id == 19) { // right leg y
                container.armorStand.setRightLegRotation(new Rotations(rightLegX.getValue(), rightLegY.getValue(), rightLegZ.getValue()));
                queuedPackets.put(19, rightLegY.getValue());
            } else if (button.id == 20) { // right leg z
                container.armorStand.setRightLegRotation(new Rotations(rightLegX.getValue(), rightLegY.getValue(), rightLegZ.getValue()));
                queuedPackets.put(20, rightLegZ.getValue());
            } else if (button.id == 21) { // left arm x
                container.armorStand.setLeftArmRotation(new Rotations(leftArmX.getValue(), leftArmY.getValue(), leftArmZ.getValue()));
                queuedPackets.put(21, leftArmX.getValue());
            } else if (button.id == 22) { // left arm y
                container.armorStand.setLeftArmRotation(new Rotations(leftArmX.getValue(), leftArmY.getValue(), leftArmZ.getValue()));
                queuedPackets.put(22, leftArmY.getValue());
            } else if (button.id == 23) { // left arm z
                container.armorStand.setLeftArmRotation(new Rotations(leftArmX.getValue(), leftArmY.getValue(), leftArmZ.getValue()));
                queuedPackets.put(23, leftArmZ.getValue());
            } else if (button.id == 24) { // right arm x
                container.armorStand.setRightArmRotation(new Rotations(rightArmX.getValue(), rightArmY.getValue(), rightArmZ.getValue()));
                queuedPackets.put(24, rightArmX.getValue());
            } else if (button.id == 25) { // right arm y
                container.armorStand.setRightArmRotation(new Rotations(rightArmX.getValue(), rightArmY.getValue(), rightArmZ.getValue()));
                queuedPackets.put(25, rightArmY.getValue());
            } else if (button.id == 26) { // right arm z
                container.armorStand.setRightArmRotation(new Rotations(rightArmX.getValue(), rightArmY.getValue(), rightArmZ.getValue()));
                queuedPackets.put(26, rightArmZ.getValue());
            }
        }
    }

    private void drawEntityOnScreen(int posX, int posY, EntityArmorStand armorstand) {
        // setup GL
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();

        // move into place and size
        GlStateManager.translate((float) posX, (float) posY, 150.0F);
        GlStateManager.scale((float) -60, (float) 60, (float) 60);
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
        held = true;
        if (mouseX > x + 213 && mouseX <= x + 353 && mouseY > y + 25 && mouseY < y + 205) {
            drag = true;
            dragX = mouseX;
            dragY = mouseY;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        held = false;
        drag = false;
        dragX = 0;
        dragY = 0;
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        held = true;
        if (drag) {
            rotX += dragX - mouseX;
            rotY += dragY - mouseY;
            dragX = mouseX;
            dragY = mouseY;
        }
    }
}
