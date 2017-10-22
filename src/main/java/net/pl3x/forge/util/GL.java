package net.pl3x.forge.util;

import org.lwjgl.opengl.GL11;

public class GL {
    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        drawLine(x1, y1, z1, x2, y2, z2, c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, c.getAlpha() / 255);
    }

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b) {
        drawLine(x1, y1, z1, x2, y2, z2, r, g, b, 1F);
    }

    public static void drawLine(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4f(r, g, b, a);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y2, z2);
        GL11.glEnd();
    }

    public static void drawSqaure(double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        drawSquare(x1, y1, z1, x2, y2, z2, c.getRed() / 255, c.getGreen() / 255, c.getBlue() / 255, c.getAlpha() / 255);
    }

    public static void drawSquare(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b) {
        drawSquare(x1, y1, z1, x2, y2, z2, r, g, b, 1F);
    }

    public static void drawSquare(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        GL11.glBegin(GL11.GL_LINE_STRIP);
        GL11.glColor4f(r, g, b, a);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glVertex3d(x2, y1, z1);
        GL11.glVertex3d(x2, y1, z2);
        GL11.glVertex3d(x1, y1, z2);
        GL11.glVertex3d(x1, y1, z1);
        GL11.glEnd();
    }

    public static void drawCube(double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        drawCube(x1, y1, z1, x2, y2, z2, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    public static void drawCube(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b) {
        drawCube(x1, y1, z1, x2, y2, z2, r, g, b, 1F);
    }

    public static void drawCube(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        drawSquare(x1, y1, z1, x2, y1, z2, r, g, b, a);
        drawSquare(x1, y2, z1, x2, y2, z2, r, g, b, a);
        drawLine(x1, y1, z1, x1, y2, z1, r, g, b, a);
        drawLine(x2, y1, z1, x2, y2, z1, r, g, b, a);
        drawLine(x2, y1, z2, x2, y2, z2, r, g, b, a);
        drawLine(x1, y1, z2, x1, y2, z2, r, g, b, a);
    }

    public static void drawCubeGrid(double x1, double y1, double z1, double x2, double y2, double z2, Color c) {
        drawCubeGrid(x1, y1, z1, x2, y2, z2, c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
    }

    public static void drawCubeGrid(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b) {
        drawCubeGrid(x1, y1, z1, x2, y2, z2, r, g, b, 1F);
    }

    public static void drawCubeGrid(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        double viewDistance = 128;
        for (double y = Math.max(y1, (y1 - (long) y1) - viewDistance) + 1; y <= y2 - 1 && y <= viewDistance; y++) {
            drawLine(x1, y, z2, x2, y, z2, r, g, b, a);
            drawLine(x1, y, z1, x2, y, z1, r, g, b, a);
            drawLine(x1, y, z1, x1, y, z2, r, g, b, a);
            drawLine(x2, y, z1, x2, y, z2, r, g, b, a);
        }
        for (double x = Math.max(x1, (x1 - (long) x1) - viewDistance) + 1; x <= x2 - 1 && x <= viewDistance; x++) {
            drawLine(x, y1, z1, x, y2, z1, r, g, b, a);
            drawLine(x, y1, z2, x, y2, z2, r, g, b, a);
            drawLine(x, y2, z1, x, y2, z2, r, g, b, a);
            drawLine(x, y1, z1, x, y1, z2, r, g, b, a);
        }
        for (double z = Math.max(z1, (z1 - (long) z1) - viewDistance) + 1; z <= z2 - 1 && z <= viewDistance; z++) {
            drawLine(x1, y1, z, x2, y1, z, r, g, b, a);
            drawLine(x1, y2, z, x2, y2, z, r, g, b, a);
            drawLine(x2, y1, z, x2, y2, z, r, g, b, a);
            drawLine(x1, y1, z, x1, y2, z, r, g, b, a);
        }
    }
}
