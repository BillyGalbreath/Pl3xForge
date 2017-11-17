package net.pl3x.forge.tileentity.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.pl3x.forge.block.custom.decoration.BlockTV;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.tileentity.TileEntityTV;
import org.lwjgl.opengl.GL11;

public class TileEntityTVRenderer extends TileEntitySpecialRenderer<TileEntityTV> {
    @Override
    public void render(TileEntityTV te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!ClientConfig.tvOptions.useTESR || te.channel == BlockTV.EnumChannel.OFF) {
            return; // tv is not on or client disabled tesr, skip drawing
        }

        // setup GL crap
        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();

        // make sure we are at brightest lighting possible
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        // rotate on Y axis
        GlStateManager.translate(x, 0, z);
        GlStateManager.rotate(te.rot, 0, 1, 0);
        GlStateManager.translate(-x, 0.1877, -z);

        // bind our texture
        Minecraft.getMinecraft().getTextureManager().bindTexture(te.channel.getResource());

        // draw the screen (using mojang's tessellator)
        /*Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x + te.xOffset, y + 0.1877, z - te.zOffset).tex(0, te.uvMin).endVertex();
        bufferbuilder.pos(x + te.xOffset, y + 0.1877, z - te.zOffset - 1.748).tex(1, te.uvMin).endVertex();
        bufferbuilder.pos(x + te.xOffset, y + 0.1877 + 0.9375, z - te.zOffset - 1.748).tex(1, te.uvMax).endVertex();
        bufferbuilder.pos(x + te.xOffset, y + 0.1877 + 0.9375, z - te.zOffset).tex(0, te.uvMax).endVertex();
        tessellator.draw();*/

        // draw the screen (using openGL)
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2d(0, te.uvMin);
        GL11.glVertex3d(x + te.xOffset, y, z - te.zOffset);
        GL11.glTexCoord2d(1, te.uvMin);
        GL11.glVertex3d(x + te.xOffset, y, z - te.zOffset - 1.748);
        GL11.glTexCoord2d(1, te.uvMax);
        GL11.glVertex3d(x + te.xOffset, y + 0.9375, z - te.zOffset - 1.748);
        GL11.glTexCoord2d(0, te.uvMax);
        GL11.glVertex3d(x + te.xOffset, y + 0.9375, z - te.zOffset);
        GL11.glEnd();


        // clean up GL crap
        GL11.glColor4f(1, 1, 1, 1);
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
}
