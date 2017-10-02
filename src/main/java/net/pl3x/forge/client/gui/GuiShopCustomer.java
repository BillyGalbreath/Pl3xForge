package net.pl3x.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerShopCustomer;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiShopCustomer extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/shop_customer.png");
    private static final ResourceLocation BANKER_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/banker.png");
    private ContainerShopCustomer container;
    private InventoryPlayer playerInv;
    private int rotation = 0;

    public GuiShopCustomer(Container container, InventoryPlayer playerInv) {
        super(container);
        this.container = (ContainerShopCustomer) container;
        this.playerInv = playerInv;
        ySize = 195;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        Button purchaseButton = new Button(0, x + 92, y + 64, 77, 15, "Purchase");
        purchaseButton.enabled = false;
        buttonList.add(purchaseButton);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = container.shop.stack.getDisplayName();
        fontRenderer.drawString(name, xSize - fontRenderer.getStringWidth(name) - 6, 6, 0x404040);
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);

        fontRenderer.drawString("Price:", 93, 25, 0x404040);
        fontRenderer.drawStringWithShadow(String.valueOf(container.shop.price), 125, 25, 0xFFFFFF);

        fontRenderer.drawString("Quantity:", 93, 40, 0x404040);
        fontRenderer.drawStringWithShadow(String.valueOf(container.shop.quantity), 139, 40, 0xFFFFFF);

        String balance = String.valueOf(HUDBalance.balance);
        fontRenderer.drawStringWithShadow(balance, xSize - fontRenderer.getStringWidth(balance) - 28, 86, 0xFFFFFF);

        String error = "Insufficient Funds";
        fontRenderer.drawString(error, xSize - fontRenderer.getStringWidth(error) - 8, ySize - 94, 0xFF0000);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        drawItem(x + 45, y + 70);

        mc.getTextureManager().bindTexture(BANKER_TEXTURE);
        drawTexturedModalRect(x + xSize - 25, y + 82, xSize, 78, 16, 16);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        drawItemTooltip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {

    }

    private void drawItemTooltip(int mouseX, int mouseY) {
        if (container.shop.stack.isEmpty()) {
            return;
        }
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2; // 18, 8 / 87, 97
        if (mouseX > x + 7 && mouseX < x + 88 && mouseY > y + 17 && mouseY < y + 98) {
            drawTooltipText(container.shop.stack.getTooltip(playerInv.player, ITooltipFlag.TooltipFlags.ADVANCED), mouseX, mouseY);
        }
    }

    private void drawItem(int x, int y) {
        if (container.shop.stack.isEmpty()) {
            return;
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        GlStateManager.translate(x, y, 50.0F);
        GlStateManager.scale(-100, 100, 100);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(container.shop.stack, container.shop.getWorld(), null);
        model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (rotation++ > 359) {
            rotation = 1;
        }
        GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
        if (model.isGui3d()) {
            GlStateManager.rotate(-15.0F, 0.5F, 0.0F, 0.0F);
        }

        Minecraft.getMinecraft().getRenderItem().renderItem(container.shop.stack, model);

        GlStateManager.popMatrix();
    }

    private void drawTooltipText(List<String> textLines, int x, int y) {
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
}
