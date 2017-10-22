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
import net.pl3x.forge.claims.Selection;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.gui.HUDBalance;
import net.pl3x.forge.icons.IconManager;

public class ClientEventHandler {
    private final HUDBalance hudBalance = new HUDBalance();

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
            GuiChat guiChatBar = (GuiChat) event.getGui();
            String text = guiChatBar.getText();
            if (!text.trim().isEmpty()) {
                String newText = IconManager.INSTANCE.translateMessage(text);
                if (!newText.equals(text)) {
                    guiChatBar.setText(newText, true);
                }
            }
        }

        // anvil input field
        else if ((event.getGui() instanceof GuiRepair)) {
            GuiRepair guiRepair = (GuiRepair) event.getGui();
            String text = guiRepair.nameField.getText();
            if (!text.trim().isEmpty()) {
                String newText = IconManager.INSTANCE.translateMessage(text);
                if (!newText.equals(text)) {
                    guiRepair.nameField.setText(newText);
                    guiRepair.renameItem();
                }
            }
        }

        // sign edit screen
        else if ((event.getGui() instanceof GuiEditSign)) {
            GuiEditSign guiEditSign = (GuiEditSign) event.getGui();
            String text = guiEditSign.tileSign.signText[guiEditSign.editLine].getUnformattedText();
            if (!text.trim().isEmpty()) {
                String newText = IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE);
                if (!newText.equals(text)) {
                    guiEditSign.tileSign.signText[guiEditSign.editLine] = new TextComponentString(newText);
                }
            }
        }

        // write in book
        else if ((event.getGui() instanceof GuiScreenBook)) {
            GuiScreenBook guiBook = (GuiScreenBook) event.getGui();
            if (guiBook.bookGettingSigned) {
                String text = guiBook.bookTitle;
                if (!text.trim().isEmpty()) {
                    String newText = IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE);
                    if (!newText.equals(text)) {
                        guiBook.bookTitle = newText;
                    }

                }
            } else {
                String text = guiBook.pageGetCurrent();
                if (!text.trim().isEmpty()) {
                    String newText = IconManager.INSTANCE.translateMessage(text, ChatColor.WHITE);
                    if (!newText.equals(text)) {
                        guiBook.pageSetCurrent(newText);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void on(RenderWorldLastEvent event) {
        if (!ClientConfig.claimVisuals.enabled) {
            return; // claim visuals disabled
        }

        Selection selection = Selection.CURRENT_SELECTION;
        if (selection == null || selection.getVisual() == null) {
            return; // nothing selected
        }

        if (selection.getDimension() != Minecraft.getMinecraft().player.dimension) {
            Selection.CURRENT_SELECTION = new Selection();
            return; // in a different dimension, clear the selection
        }

        selection.getVisual().render(event.getPartialTicks());
    }
}
