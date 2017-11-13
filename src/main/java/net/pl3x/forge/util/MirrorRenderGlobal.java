package net.pl3x.forge.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;

public class MirrorRenderGlobal extends RenderGlobal {
    public MirrorRenderGlobal() {
        super(Minecraft.getMinecraft());
    }

    @Override
    public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_) {
        super.renderClouds(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
    }
}
