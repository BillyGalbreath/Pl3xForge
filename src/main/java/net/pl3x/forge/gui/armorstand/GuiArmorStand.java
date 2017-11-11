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
import net.minecraft.inventory.EntityEquipmentSlot;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.container.ContainerArmorstand;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.gui.element.TabButton;
import net.pl3x.forge.util.gl.GuiUtil;

public class GuiArmorStand extends GuiContainer {
    private final ContainerArmorstand container;
    private final EntityPlayer player;
    private final int entityId;

    private int x;
    private int y;
    private float oldMouseX;
    private float oldMouseY;

    public GuiArmorStand(Container container, EntityPlayer player, int entityId) {
        super(container);
        this.container = (ContainerArmorstand) container;
        this.player = player;
        this.entityId = entityId;

        ySize = 202;
    }

    @Override
    public void initGui() {
        super.initGui();

        x = (width - xSize) / 2;
        y = (height - ySize) / 2;

        addButton(new TabButton(0, x + xSize - 46, y - 13, 40, 16, "items")).selected = true;
        addButton(new TabButton(1, x + xSize - 91, y - 13, 47, 16, "display"));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        if (container.armorStand == null || container.armorStand.isDead) {
            player.closeScreen();
            return;
        }

        if (container.inventory.getStackInSlot(0).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty()) {
            container.inventory.setInventorySlotContents(0, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.FEET));
        }
        if (container.inventory.getStackInSlot(1).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.LEGS).isEmpty()) {
            container.inventory.setInventorySlotContents(1, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
        }
        if (container.inventory.getStackInSlot(2).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty()) {
            container.inventory.setInventorySlotContents(2, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
        }
        if (container.inventory.getStackInSlot(3).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
            container.inventory.setInventorySlotContents(3, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
        }
        if (container.inventory.getStackInSlot(4).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()) {
            container.inventory.setInventorySlotContents(4, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
        }
        if (container.inventory.getStackInSlot(5).isEmpty() && !container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).isEmpty()) {
            container.inventory.setInventorySlotContents(5, container.armorStand.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString("ArmorStand", 110, 8, 0x404040);
        fontRenderer.drawString("Inventory", 8, 108, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GuiUtil.drawBG(this, x, y, xSize, ySize);
        GuiUtil.drawSlots(this, inventorySlots.inventorySlots, x, y);
        GuiUtil.drawBlackWindow(this, x + 86, y + 19, 75, 90);
        drawEntityOnScreen(x + 123, y + 90, x + 51 - oldMouseX, y + 25 - oldMouseY, container.armorStand);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();

        this.oldMouseX = (float) mouseX;
        this.oldMouseY = (float) mouseY;
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        // display tab
        if (button.id == 1) {
            mc.player.openGui(Pl3x.instance, ModGuiHandler.ARMOR_STAND_DISPLAY,
                    mc.player.world, 0, 0, 0, entityId);
        }
    }

    private static void drawEntityOnScreen(int posX, int posY, float mouseX, float mouseY, EntityArmorStand armorstand) {
        // setup GL
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();

        // move into place and size
        GlStateManager.translate((float) posX, (float) posY, 50.0F);
        GlStateManager.scale((float) -30, (float) 30, (float) 30);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        // fix shadows/lighting angle
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

        // follow mouse
        GlStateManager.rotate(-((float) Math.atan((mouseX + 70) / 40.0F)) * 20.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float) Math.atan((mouseY + 20) / 40.0F)) * 20.0F, 1.0F, 0.0F, 0.0F);

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
}
