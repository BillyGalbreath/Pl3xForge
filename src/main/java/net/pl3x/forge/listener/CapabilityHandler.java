package net.pl3x.forge.listener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.data.CapabilityProvider;
import net.pl3x.forge.network.PacketHandler;

public class CapabilityHandler {
    private static final ResourceLocation PLAYER_DATA = new ResourceLocation(Pl3x.modId, "playerdata");

    @SubscribeEvent
    public void on(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) {
            return;
        }
        event.addCapability(PLAYER_DATA, new CapabilityProvider());
    }

    @SubscribeEvent
    public void on(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(CapabilityProvider.CAPABILITY, null)
                .setDataFromNBT(event.getOriginal().getCapability(CapabilityProvider.CAPABILITY, null)
                        .getDataAsNBT());
    }

    @SubscribeEvent
    public void on(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return;
        }
        PacketHandler.updatePlayerData((EntityPlayerMP) event.getEntity());
    }
}
