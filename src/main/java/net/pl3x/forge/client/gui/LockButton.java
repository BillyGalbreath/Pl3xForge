package net.pl3x.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.container.ContainerBanker;
import net.pl3x.forge.client.inventory.SlotBanker;

@SideOnly(Side.CLIENT)
public class LockButton extends GuiButton {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/gui/banker.png");
    private final ContainerBanker container;
    private final int index;

    public LockButton(ContainerBanker container, int index, int buttonId, int x, int y) {
        super(buttonId, x, y, 17, 17, "");
        this.container = container;
        this.index = index;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!container.inventorySlots.get(index).isEnabled()) {
            mc.getTextureManager().bindTexture(BG_TEXTURE);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int i = getHoverState(hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            drawTexturedModalRect(x, y, 176 + i * 17, 61, width, height);
            mouseDragged(mc, mouseX, mouseY);
        }
    }

    protected int getHoverState(boolean mouseOver) {
        SlotBanker slot = (SlotBanker) container.getSlot(index);
        if (!slot.isEnabled() && !slot.isPurchasable()) {
            return 0;
        }
        if (mouseOver && slot.isPurchasable()) {
            return 1;
        }
        return 2;
    }
}
