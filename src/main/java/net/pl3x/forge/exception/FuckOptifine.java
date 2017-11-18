package net.pl3x.forge.exception;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraftforge.fml.client.CustomModLoadingErrorDisplayException;

public class FuckOptifine extends CustomModLoadingErrorDisplayException {
    @Override
    public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {

    }

    @Override
    public void drawScreen(GuiErrorScreen gui, FontRenderer fontRenderer, int x, int y, float partialTicks) {
        gui.drawDefaultBackground();
        gui.drawCenteredString(fontRenderer, "Illegal Mod Detected - Optifine", gui.width / 2, 85, 0xFF5555);

        gui.drawCenteredString(fontRenderer, "Optifine screws with too many internals and breaks many mod functions we use.", gui.width / 2, 100, 0xFFFFFF);
        gui.drawCenteredString(fontRenderer, "Please uninstall Optifine and try connecting again.", gui.width / 2, 110, 0xFFFFFF);
    }
}
