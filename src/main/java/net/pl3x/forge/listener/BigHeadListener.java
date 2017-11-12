package net.pl3x.forge.listener;

import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BigHeadListener {
    public static final Set<UUID> playersWithBigHeadEnabled = new HashSet<>();

    @SubscribeEvent
    public void on(RenderPlayerEvent.Pre event) {
        event.getRenderer().getMainModel().bigHead =
                playersWithBigHeadEnabled.contains(event.getEntityPlayer().getUniqueID());
    }
}
