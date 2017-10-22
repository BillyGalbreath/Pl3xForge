package net.pl3x.forge.claims.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.pl3x.forge.claims.Claim;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.util.Color;
import net.pl3x.forge.util.GL;
import org.lwjgl.opengl.GL11;

public class ClaimVisual {
    private final int minX;
    private final int minZ;
    private final int maxX;
    private final int maxZ;

    private Color outlineColor;
    private Color gridColor;
    private float thickness;

    public ClaimVisual(Claim claim) {
        minX = claim.getMinX();
        minZ = claim.getMinZ();
        maxX = claim.getMaxX();
        maxZ = claim.getMaxZ();
        setFrame();
    }

    public ClaimVisual(int x1, int z1, int x2, int z2) {
        this.minX = Math.min(x1, x2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxZ = Math.max(z1, z2);
        setFrame();
    }

    private void setFrame() {
        outlineColor = new Color(ClientConfig.claimVisuals.outlineColor);
        gridColor = new Color(ClientConfig.claimVisuals.gridColor);
        thickness = ClientConfig.claimVisuals.thickness;
    }

    public void render(float partialTicks) {
        if (ClientConfig.claimVisuals.renderBehind) {
            render(partialTicks, true);
        }
        render(partialTicks, false);
    }

    public void render(float partialTicks, boolean showBehind) {
        GlStateManager.pushMatrix();
        if (showBehind) {
            GlStateManager.disableDepth();
        }
        GlStateManager.disableLighting();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);

        EntityPlayerSP player = Minecraft.getMinecraft().player;

        double camX = player.prevPosX + (player.posX - player.prevPosX) * partialTicks;
        double camY = player.prevPosY + (player.posY - player.prevPosY) * partialTicks;
        double camZ = player.prevPosZ + (player.posZ - player.prevPosZ) * partialTicks;

        double x1 = minX - camX;
        double y1 = 0 - camY;
        double z1 = minZ - camZ;
        double x2 = maxX - camX;
        double y2 = 255 - camY;
        double z2 = maxZ - camZ;

        GlStateManager.glLineWidth(thickness);

        GL.drawCube(x1, y1, z1, x2, y2, z2, showBehind ? new Color(outlineColor).setAlpha(0.11F) : outlineColor);
        GL.drawCubeGrid(x1, y1, z1, x2, y2, z2, showBehind ? new Color(gridColor).setAlpha(0.11F) : gridColor);

        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        if (showBehind) {
            GlStateManager.enableDepth();
        }
        GlStateManager.popMatrix();
    }
}
