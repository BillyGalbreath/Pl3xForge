package net.pl3x.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;

import java.io.IOException;

public class TitleScreen extends GuiScreen {
    private final static String branding = "Pl3xForge " + Pl3xForgeClient.version;
    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fontRenderer = mc.fontRenderer;

    @SubscribeEvent
    public void onRenderMainMenu(GuiScreenEvent event) throws IOException {
        if (event.getGui() instanceof GuiMainMenu) {
            ScaledResolution scale = new ScaledResolution(mc);
            fontRenderer.drawStringWithShadow(branding,
                    scale.getScaledWidth() - fontRenderer.getStringWidth(branding) - 2,
                    scale.getScaledHeight() - 20, 16777215);
        }
    }
}
