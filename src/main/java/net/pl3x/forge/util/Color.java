package net.pl3x.forge.util;

public class Color {
    private float red;
    private float green;
    private float blue;
    private float alpha;

    public Color(String hex) {
        try {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            this.red = (1 / 255F) * Integer.valueOf(hex.substring(0, 2), 16);
            this.green = (1 / 255F) * Integer.valueOf(hex.substring(2, 4), 16);
            this.blue = (1 / 255F) * Integer.valueOf(hex.substring(4, 6), 16);
            this.alpha = (1 / 255F) * (hex.length() != 8 ? 255 : Integer.valueOf(hex.substring(6, 8), 16));
        } catch (Exception e) {
            e.printStackTrace();
            this.red = 1;
            this.green = 1;
            this.blue = 1;
            this.alpha = 1;
        }
    }

    public Color(Color color) {
        this(color.red, color.green, color.blue, color.alpha);
    }

    public Color(float red, float green, float blue) {
        this(red, green, blue, 1F);
    }

    public Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public float getRed() {
        return red;
    }

    public Color setRed(float red) {
        this.red = red;
        return this;
    }

    public float getGreen() {
        return green;
    }

    public Color setGreen(float green) {
        this.green = green;
        return this;
    }

    public float getBlue() {
        return blue;
    }

    public Color setBlue(float blue) {
        this.blue = blue;
        return this;
    }

    public float getAlpha() {
        return alpha;
    }

    public Color setAlpha(float alpha) {
        this.alpha = alpha;
        return this;
    }
}
