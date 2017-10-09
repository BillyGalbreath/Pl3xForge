package net.pl3x.forge.client.tileentity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.client.tileentity.TileEntityShop;
import net.pl3x.forge.client.util.Quat;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Quaternion;

public class TileEntityShopRenderer extends TileEntitySpecialRenderer<TileEntityShop> {
    @Override
    public void render(TileEntityShop te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!te.stack.isEmpty()) {
            if (te.model == null) {
                te.model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(te.stack, getWorld(), null);
            }

            GlStateManager.enableRescaleNormal();
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
            GlStateManager.enableBlend();
            RenderHelper.enableStandardItemLighting();
            GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GlStateManager.pushMatrix();

            Quaternion rot = Quat.create(0, 1, 0, te.facingRot);
            Quat.mul(rot, 1, 0, 0, te.display_rotateX - (!te.model.isGui3d() ? 65 : 0));
            Quat.mul(rot, 0, 1, 0, te.display_rotateY);
            Quat.mul(rot, 0, 0, 1, te.display_rotateZ);

            GlStateManager.translate(x + 0.5, y + te.display_yOffset - (te.model.isGui3d() ? 0.075 : 0), z + 0.5);
            IBakedModel model = ForgeHooksClient.handleCameraTransforms(te.model, ItemCameraTransforms.TransformType.GROUND, false);
            GlStateManager.rotate(rot);
            GlStateManager.scale(te.display_scale, te.display_scale, te.display_scale);

            Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            Minecraft.getMinecraft().getRenderItem().renderItem(te.stack, model);

            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            GlStateManager.pushMatrix();

            GlStateManager.translate(x + te.signPosX, y + te.signPosY, z + te.signPosZ);
            GlStateManager.rotate(te.signRot, 0F, 1F, 0F);
            GlStateManager.scale(-0.01F, -0.01F, 0.01F);

            String quantity = "Qty: " + te.quantity;
            String icon = "\u99F4";
            String price = String.valueOf(te.price);
            int priceWidth = getFontRenderer().getStringWidth(icon + price);

            getFontRenderer().drawString(quantity, -getFontRenderer().getStringWidth(quantity) / 2, 2, 0x000000);
            getFontRenderer().drawString(icon, -priceWidth / 2 - 1, 13, 0xffffff);
            getFontRenderer().drawString(price, -priceWidth / 2 + 9, 13, 0x000000);

            GlStateManager.popMatrix();
        }
    }
}
