package net.pl3x.forge.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.Pl3x;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiUtil {
    public static final ResourceLocation GUI_BG = new ResourceLocation(Pl3x.modId, "textures/gui/gui_bg.png");
    public static final ResourceLocation GUI_ELEMENTS = new ResourceLocation(Pl3x.modId, "textures/gui/gui_elements.png");

    public static final ResourceLocation COIN = new ResourceLocation(Pl3x.modId, "textures/items/coin.png");
    public static final ResourceLocation ENCHANTED_BOOK = new ResourceLocation("minecraft", "textures/items/book_enchanted.png");
    public static final ResourceLocation EXP_BOTTLE = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");

    public static void drawBG(GuiScreen gui, int x, int y, int width, int height) {
        gui.mc.getTextureManager().bindTexture(GuiUtil.GUI_BG);
        GlStateManager.color(1, 1, 1, 1);
        width = width / 2;
        height = height / 2;
        gui.drawTexturedModalRect(x, y, 0, 0, width, height);
        gui.drawTexturedModalRect(x, y + height, 0, 256 - height, width, height);
        gui.drawTexturedModalRect(x + width, y, 256 - width, 0, width, height);
        gui.drawTexturedModalRect(x + width, y + height, 256 - width, 256 - height, width, height);
    }

    public static void drawSlots(GuiScreen gui, List<Slot> slots, int x, int y) {
        gui.mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
        GlStateManager.color(1, 1, 1, 1);
        slots.forEach(slot -> gui.drawTexturedModalRect(x + slot.xPos - 1, y + slot.yPos - 1, 150, 0, 18, 18));
    }

    public static void drawBlackWindow(GuiScreen gui, int x, int y, int width, int height) {
        gui.mc.getTextureManager().bindTexture(GuiUtil.GUI_ELEMENTS);
        GlStateManager.color(1, 1, 1, 1);
        width = width / 2;
        height = height / 2;
        gui.drawTexturedModalRect(x, y, 0, 156, width, height);
        gui.drawTexturedModalRect(x, y + height, 0, 256 - height, width, height);
        gui.drawTexturedModalRect(x + width, y, 100 - width, 156, width, height);
        gui.drawTexturedModalRect(x + width, y + height, 100 - width, 256 - height, width, height);
    }

    public static float drawItem(GuiScreen gui, ItemStack stack, float partialTicks, int x, int y, int scale, float itemRot) {
        if (stack.isEmpty()) {
            return 0;
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.translate(x, y, 50);
        GlStateManager.scale(-scale, scale, scale);
        GlStateManager.rotate(180, 0, 0, 1);
        IBakedModel model = gui.mc.getRenderItem().getItemModelWithOverrides(stack, gui.mc.world, null);
        model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);
        gui.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        itemRot += partialTicks * 2;
        if (itemRot >= 360) {
            itemRot = 0;
        }
        GlStateManager.rotate(itemRot, 0, 1, 0);
        if (model.isGui3d()) {
            GlStateManager.rotate(-15, 1, 0, 0);
        }
        gui.mc.getRenderItem().renderItem(stack, model);
        GlStateManager.popMatrix();
        return itemRot;
    }

    public static void drawTexture(GuiScreen gui, ResourceLocation texture, int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
        gui.mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1, 1, 1, 1);
        Gui.drawModalRectWithCustomSizedTexture(x, y, u, v, width, height, textureWidth, textureHeight);
    }

    public static void drawTexture(GuiScreen gui, ResourceLocation texture, int x, int y, int textureX, int textureY, int width, int height) {
        gui.mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1, 1, 1, 1);
        gui.drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }
}
