package net.pl3x.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;

import java.io.IOException;

public class TitleScreen {
    private Minecraft mc = Minecraft.getMinecraft();
    private FontRenderer fontRenderer = mc.fontRenderer;

    @SubscribeEvent
    public void onRenderMainMenu(GuiScreenEvent event) throws IOException {
        if (event.getGui() instanceof GuiMainMenu) {
            fontRenderer.drawStringWithShadow("Pl3xForge " + Pl3xForgeClient.version, 2,
                    (new ScaledResolution(mc)).getScaledHeight() - 60, 16777215);
        }
    }
}
