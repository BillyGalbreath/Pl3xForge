package net.pl3x.forge.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.container.ContainerBanker;
import net.pl3x.forge.inventory.SlotBanker;
import net.pl3x.forge.util.GuiUtil;

@SideOnly(Side.CLIENT)
public class LockButton extends GuiButton {
    private final ContainerBanker container;
    private final int index;

    public LockButton(ContainerBanker container, int index, int buttonId, int x, int y) {
        super(buttonId, x, y, 18, 18, "");
        this.container = container;
        this.index = index;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!container.inventorySlots.get(index).isEnabled()) {
            hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

            mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            drawTexturedModalRect(x, y, 150 + getHoverState(hovered), 18, width, height);

            mouseDragged(mc, mouseX, mouseY);
        }
    }

    @Override
    protected int getHoverState(boolean mouseOver) {
        SlotBanker slot = (SlotBanker) container.getSlot(index);
        if (!slot.isEnabled() && !slot.isPurchasable()) {
            return 0;
        }
        if (mouseOver && slot.isPurchasable()) {
            return 18;
        }
        return 36;
    }
}
