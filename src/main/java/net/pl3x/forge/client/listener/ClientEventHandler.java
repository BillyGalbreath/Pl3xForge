package net.pl3x.forge.client.listener;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.gui.HUDBalance;

public class ClientEventHandler {
    private final HUDBalance hudBalance = new HUDBalance();

    @SubscribeEvent
    public void on(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            hudBalance.draw(); // only render when exp HUD is showing too
        }
    }
}
