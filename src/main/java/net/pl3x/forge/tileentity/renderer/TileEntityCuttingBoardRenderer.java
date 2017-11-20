package net.pl3x.forge.tileentity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.pl3x.forge.tileentity.TileEntityCuttingBoard;
import org.lwjgl.opengl.GL11;

public class TileEntityCuttingBoardRenderer extends TileEntitySpecialRenderer<TileEntityCuttingBoard> {
    @Override
    public void render(TileEntityCuttingBoard te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        ItemStack stack = te.inventory.getStackInSlot(0);
        if (stack.isEmpty()) {
            return;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        GlStateManager.enableBlend();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.pushMatrix();

        GlStateManager.translate(x + 0.5, y - 0.078, z + 0.5);
        IBakedModel model = ForgeHooksClient.handleCameraTransforms(
                Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, getWorld(), null),
                ItemCameraTransforms.TransformType.GROUND, false);
        GlStateManager.rotate(-90, 1, 0, 0);
        GlStateManager.rotate(te.rotY, 0, 0, 1);
        GlStateManager.scale(1, 1, 1);

        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
    }
}
