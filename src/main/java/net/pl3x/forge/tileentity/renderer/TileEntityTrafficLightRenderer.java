package net.pl3x.forge.tileentity.renderer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.tileentity.TileEntityTrafficLight;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityTrafficLightRenderer extends TileEntitySpecialRenderer<TileEntityTrafficLight> {
    private final static int SIDES = 20;
    private final static List<Double> SIN_POINTS = new ArrayList<>();
    private final static List<Double> COS_POINTS = new ArrayList<>();

    public TileEntityTrafficLightRenderer() {
        // calculate these once and reuse calculations
        double radius = 0.08;
        double radians = Math.toRadians(180);
        double pi2 = Math.PI * 2;
        for (int i = 0; i <= SIDES; i++) {
            double angle = (pi2 * i / SIDES) + radians;
            COS_POINTS.add(Math.cos(angle) * radius);
            SIN_POINTS.add(Math.sin(angle) * radius);
        }
    }

    @Override
    public void render(TileEntityTrafficLight te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!te.isLightOn()) {
            return; // light is not on, do not draw
        }

        // setup GL crap
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();

        // make sure we are at brightest lighting possible
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        // rotate at center of block
        GlStateManager.translate(x + 0.5, 0, z + 0.5);
        GlStateManager.rotate(te.rot, 0, 1, 0);
        GlStateManager.translate(-(x + 0.5), 0, -(z + 0.5));

        // set light position
        x += te.xOffset;
        y += te.yOffset + (te.lightState == null ? TileEntityTrafficLight.LightState.RED.y : te.lightState.y);
        z += te.zOffset;

        // draw the light
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        setColor(te.lightState);
        for (int i = 0; i <= SIDES; i++) {
            GL11.glVertex3d(x + COS_POINTS.get(i), y + SIN_POINTS.get(i), z);
        }
        GL11.glEnd();

        // clean up GL crap
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    private void setColor(TileEntityTrafficLight.LightState colorState) {
        switch (colorState != null ? colorState : TileEntityTrafficLight.LightState.RED) {
            case RED:
                GL11.glColor4f(1, 0, 0, 1);
                break;
            case YELLOW:
                GL11.glColor4f(1, 1, 0, 1);
                break;
            case GREEN:
                GL11.glColor4f(0, 1, 0, 1);
        }
    }
}
