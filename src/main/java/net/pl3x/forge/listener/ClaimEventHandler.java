package net.pl3x.forge.listener;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.claims.Selection;
import net.pl3x.forge.item.ModItems;
import net.pl3x.forge.item.custom.ItemClaimTool;

public class ClaimEventHandler {
    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), true);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.RightClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, true);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickBlock event) {
        ItemClaimTool.processClaimToolClick(event, event.getPos(), false);
    }

    @SubscribeEvent
    public void on(PlayerInteractEvent.LeftClickItem event) {
        ItemClaimTool.processClaimToolClick(event, null, false);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void on(net.minecraftforge.client.event.RenderWorldLastEvent event) {
        if (Selection.CURRENT_SELECTION == null || Selection.CURRENT_SELECTION.getVisual() == null) {
            return; // nothing selected
        }

        if (Selection.CURRENT_SELECTION.getDimension() != net.minecraft.client.Minecraft.getMinecraft().player.dimension) {
            Selection.CURRENT_SELECTION = new Selection();
            return; // in a different dimension, clear the selection
        }

        if (net.minecraft.client.Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() != ModItems.CLAIM_TOOL) {
            Selection.CURRENT_SELECTION = new Selection();
            return; // not holding the claim tool anymore, clear the selection
        }

        Selection.CURRENT_SELECTION.getVisual().render(event.getPartialTicks());
    }
}
