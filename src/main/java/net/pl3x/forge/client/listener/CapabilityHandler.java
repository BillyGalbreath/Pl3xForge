package net.pl3x.forge.client.listener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.PlayerDataProvider;

public class CapabilityHandler {
    public static final ResourceLocation PLAYER_DATA_CAPABILITY = new ResourceLocation(Pl3xForgeClient.modId, "playerdata");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) {
            return;
        }
        event.addCapability(PLAYER_DATA_CAPABILITY, new PlayerDataProvider());
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null)
                .setDataFromNBT(event.getOriginal().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null)
                        .getDataAsNBT());
    }
}
