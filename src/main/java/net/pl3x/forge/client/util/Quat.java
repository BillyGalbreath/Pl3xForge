package net.pl3x.forge.client.util;

import org.lwjgl.util.vector.Quaternion;

public class Quat {
    public static Quaternion create(float xx, float yy, float zz, float a) {
        float halfTheta = a * (float) Math.PI / 360;
        float factor = (float) Math.sin(halfTheta);
        return new Quaternion(xx * factor, yy * factor, zz * factor, (float) Math.cos(halfTheta));
    }

    public static Quaternion mul(Quaternion quat, float x, float y, float z, float w) {
        return Quaternion.mul(quat, Quat.create(x, y, z, w), quat);
    }
}
