package net.pl3x.forge.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.claims.Selection;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.item.ModItems;
import net.pl3x.forge.item.custom.ItemClaimTool;

public class ClaimEventHandler {
    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), true);
        // if cancelled, do not process anything else
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, true);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), false);
        // if cancelled, do not process anything else
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, false);
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

        if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() != ModItems.CLAIM_TOOL) {
            Selection.CURRENT_SELECTION = new Selection();
            return; // not holding the claim tool anymore, clear the selection
        }

        selection.getVisual().render(event.getPartialTicks());
    }
}
