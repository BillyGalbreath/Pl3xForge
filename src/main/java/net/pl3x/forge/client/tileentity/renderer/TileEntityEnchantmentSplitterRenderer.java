package net.pl3x.forge.client.tileentity.renderer;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.client.tileentity.TileEntityEnchantmentSplitter;

@SideOnly(Side.CLIENT)
public class TileEntityEnchantmentSplitterRenderer extends TileEntitySpecialRenderer<TileEntityEnchantmentSplitter> {
    private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
    private final ModelBook modelBook = new ModelBook();

    public void render(TileEntityEnchantmentSplitter te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F, (float) z + 0.5F);
        float f = (float) te.tickCount + partialTicks;
        GlStateManager.translate(0, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0);
        float f1;

        for (f1 = te.bookRotation - te.bookRotationPrev; f1 >= (float) Math.PI; f1 -= ((float) Math.PI * 2F)) {
        }

        while (f1 < -(float) Math.PI) {
            f1 += ((float) Math.PI * 2F);
        }

        float f2 = te.bookRotationPrev + f1 * partialTicks;
        GlStateManager.rotate(-f2 * (180F / (float) Math.PI), 0, 1, 0);
        GlStateManager.rotate(80, 0, 0, 1);
        this.bindTexture(TEXTURE_BOOK);
        float f3 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.25F;
        float f4 = te.pageFlipPrev + (te.pageFlip - te.pageFlipPrev) * partialTicks + 0.75F;
        f3 = (f3 - (float) MathHelper.fastFloor((double) f3)) * 1.6F - 0.3F;
        f4 = (f4 - (float) MathHelper.fastFloor((double) f4)) * 1.6F - 0.3F;

        if (f3 < 0) {
            f3 = 0;
        }

        if (f4 < 0) {
            f4 = 0;
        }

        if (f3 > 1) {
            f3 = 1;
        }

        if (f4 > 1) {
            f4 = 1;
        }

        float f5 = te.bookSpreadPrev + (te.bookSpread - te.bookSpreadPrev) * partialTicks;
        GlStateManager.enableCull();
        this.modelBook.render(null, f, f3, f4, f5, 0, 0.0625F);
        GlStateManager.popMatrix();
    }
}
