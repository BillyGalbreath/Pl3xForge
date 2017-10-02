package net.pl3x.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerShopOwner;
import org.lwjgl.opengl.GL11;

public class GuiShopOwner extends GuiContainer {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/shop_owner.png");
    private ContainerShopOwner container;
    private InventoryPlayer playerInv;
    private GuiTextField priceField;
    private GuiTextField qtyField;
    private int rotation = 0;

    public GuiShopOwner(Container container, InventoryPlayer playerInv) {
        super(container);
        this.container = (ContainerShopOwner) container;
        this.playerInv = playerInv;
        xSize = 234;
        ySize = 212;
    }

    @Override
    public void initGui() {
        super.initGui();
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        buttonList.add(new Button(0, x + 173, y + 77, 12, 12, "+"));
        buttonList.add(new Button(1, x + 215, y + 77, 12, 12, "-"));
        buttonList.add(new Button(2, x + 173, y + 102, 12, 12, "+"));
        buttonList.add(new Button(3, x + 215, y + 102, 12, 12, "-"));

        priceField = new GuiTextField(1, fontRenderer, 186, 78, 28, 10);
        priceField.setMaxStringLength(4);
        priceField.setValidator(input -> {
            if (StringUtils.isNullOrEmpty(input)) {
                return true;
            }
            try {
                return Integer.valueOf(input) != null;
            } catch (NumberFormatException e) {
                return false;
            }
        });

        qtyField = new GuiTextField(0, fontRenderer, 186, 103, 28, 10);
        qtyField.setMaxStringLength(4);
        qtyField.setValidator(input -> {
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
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040);
        fontRenderer.drawString("Quantity:", 173, 93, 0x404040);
        fontRenderer.drawString("Price:", 173, 68, 0x404040);

        priceField.drawTextBox();
        qtyField.drawTextBox();

        priceField.setText(String.valueOf(container.shop.price));
        qtyField.setText(String.valueOf(container.shop.quantity));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

        drawItem(x + 198, y + 42);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) { // price +
            container.shop.incrementPrice();
        } else if (button.id == 1) { // price -
            container.shop.decrementPrice();
        } else if (button.id == 2) { // qty +
            container.shop.incrementQuantity();
        } else if (button.id == 3) { // qty -
            container.shop.decrementQuantity();
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
        GlStateManager.scale(-70, 70, 70);
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
}
