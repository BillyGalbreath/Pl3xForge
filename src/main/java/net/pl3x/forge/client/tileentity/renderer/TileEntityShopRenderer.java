package net.pl3x.forge.client.tileentity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.tileentity.TileEntityShop;
import org.lwjgl.opengl.GL11;

public class TileEntityShopRenderer extends TileEntitySpecialRenderer<TileEntityShop> {
    private static final ResourceLocation COIN_TEXTURE = new ResourceLocation(Pl3xForgeClient.modId, "textures/items/coin.png");

    @Override
    public void render(TileEntityShop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!te.stack.isEmpty()) {
            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            switch (te.facing) {
                case NORTH: // sign faces to the EAST
                    GlStateManager.translate(x + 0.6, y + 0.7, z + 0.5);
                    GlStateManager.rotate(-90, 0, 1F, 0);
                    GlStateManager.rotate(90, 1F, 0, 0);
                    break;
                case SOUTH: // sign faces to the WEST
                    GlStateManager.translate(x + 0.4, y + 0.7, z + 0.5);
                    GlStateManager.rotate(90, 0, 1F, 0);
                    GlStateManager.rotate(90, 1F, 0, 0);
                    break;
                case WEST: // sign faces to the NORTH
                    GlStateManager.translate(x + 0.5, y + 0.7, z + 0.4);
                    GlStateManager.rotate(90, 1F, 0, 0);
                    break;
                case EAST: // sign faces to the SOUTH
                    GlStateManager.translate(x + 0.5, y + 0.7, z + 0.6);
                    GlStateManager.rotate(180, 0, 0, 1F);
                    GlStateManager.rotate(-90, 1F, 0, 0);
                    break;
            }
            GlStateManager.scale(0.8, 0.8, 0.8);

            IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(te.stack, te.getWorld(), null);
            model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.GROUND, false);

            if (!model.isGui3d()) {
                GlStateManager.rotate(180, 0, 1, 0);
            }

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(te.stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();

            GlStateManager.pushMatrix();
            switch (te.facing) {
                case NORTH: // sign faces to the EAST
                    GlStateManager.translate(x + 0.7501, y + 0.5, z + 0.5);
                    GlStateManager.rotate(270, 0F, 1F, 0F);
                    break;
                case SOUTH: // sign faces to the WEST
                    GlStateManager.translate(x + 0.24999, y + 0.5, z + 0.5);
                    GlStateManager.rotate(90, 0F, 1F, 0F);
                    break;
                case WEST: // sign faces to the NORTH
                    GlStateManager.translate(x + 0.5, y + 0.5, z + 0.2499);
                    break;
                case EAST: // sign faces to the SOUTH
                    GlStateManager.translate(x + 0.5, y + 0.5, z + 0.7501);
                    GlStateManager.rotate(180, 0F, 1F, 0F);
                    break;
            }

            GlStateManager.scale(-0.01F, -0.01F, 0.01F);
            FontRenderer font = getFontRenderer();

            String quantity = "Qty: " + te.quantity;
            String icon = "\u99F4";
            String price = String.valueOf(te.price);

            int priceWidth = font.getStringWidth(icon + price);

            font.drawString(quantity, -font.getStringWidth(quantity) / 2, 2, 0x000000);
            font.drawString(icon, -priceWidth / 2 - 1, 13, 0xffffff);
            font.drawString(price, -priceWidth / 2 + 9, 13, 0x000000);

            GlStateManager.popMatrix();
        }
    }
}
