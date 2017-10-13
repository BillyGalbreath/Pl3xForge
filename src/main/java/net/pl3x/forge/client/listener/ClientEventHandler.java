package net.pl3x.forge.client.listener;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.claims.Claim;
import net.pl3x.forge.client.configuration.ModConfig;
import net.pl3x.forge.client.gui.HUDBalance;

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
    public void on(RenderWorldLastEvent event) {
        if (!ModConfig.claimVisuals.enabled) {
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
