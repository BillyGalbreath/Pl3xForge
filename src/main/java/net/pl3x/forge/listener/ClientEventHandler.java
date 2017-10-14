package net.pl3x.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiRepair;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.claims.Claim;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.gui.HUDBalance;
import net.pl3x.forge.icons.IconManager;

public class ClientEventHandler {
    private final HUDBalance hudBalance = new HUDBalance();
    private Claim claim;

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            hudBalance.draw(); // only render when exp HUD is showing too
        }
    }

    @SubscribeEvent
    public void on(GuiScreenEvent.KeyboardInputEvent.Post event) {
        // chat input field
        if ((event.getGui() instanceof GuiChat)) {
            GuiChat guiChat = (GuiChat) event.getGui();
            String text = guiChat.inputField.getText();
            if (!text.trim().isEmpty()) {
                guiChat.inputField.setText(IconManager.INSTANCE.translateMessage(text));
            }
        }

        // anvil input field
        else if ((event.getGui() instanceof GuiRepair)) {
            GuiRepair guiRepair = (GuiRepair) event.getGui();
            String text = guiRepair.nameField.getText();
            if (!text.trim().isEmpty()) {
                guiRepair.nameField.setText(IconManager.INSTANCE.translateMessage(text));
                guiRepair.renameItem();
            }
        }

        // sign edit screen
        else if ((event.getGui() instanceof GuiEditSign)) {
            GuiEditSign guiEditSign = (GuiEditSign) event.getGui();
            String text = guiEditSign.tileSign.signText[guiEditSign.editLine].getUnformattedText();
            if (!text.trim().isEmpty()) {
                guiEditSign.tileSign.signText[guiEditSign.editLine] =
                        new TextComponentString(IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE));
            }
        }

        // write in book
        else if ((event.getGui() instanceof GuiScreenBook)) {
            GuiScreenBook guiBook = (GuiScreenBook) event.getGui();
            if (guiBook.bookGettingSigned) {
                String text = guiBook.bookTitle;
                if (!text.trim().isEmpty()) {
                    guiBook.bookTitle = IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE);

                }
            } else {
                String text = guiBook.pageGetCurrent();
                if (!text.trim().isEmpty()) {
                    guiBook.pageSetCurrent(IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE));
                }
            }
        }
    }

    @SubscribeEvent
    public void on(RenderWorldLastEvent event) {
        if (!ClientConfig.claimVisuals.enabled) {
            return; // claim visuals disabled
        }

        int dim = Minecraft.getMinecraft().player.dimension;

        // test visual
        //if (claim == null) {
        //    claim = new Claim(dim, 199, 65, 247, 203, 68, 249);
        //}

        if (claim != null && claim.getDimension() == dim) {
            claim.getVisual().render(event.getPartialTicks());
        }
    }
}
