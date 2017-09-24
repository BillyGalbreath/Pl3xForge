package net.pl3x.forge.client.listener;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class ServerEventHandler {
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        EntityItem item = event.getItem();
    }

    public void e(PlayerEvent.ItemPickupEvent event) {

    }
}
