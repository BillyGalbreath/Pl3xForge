package net.pl3x.forge.client.listener;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.data.CapabilityProvider;
import net.pl3x.forge.client.network.PacketHandler;

public class CapabilityHandler {
    public static final ResourceLocation PLAYER_DATA_CAPABILITY = new ResourceLocation(Pl3xForgeClient.modId, "playerdata");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) {
            return;
        }
        event.addCapability(PLAYER_DATA_CAPABILITY, new CapabilityProvider());
        System.out.println("Attached Capability");
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(CapabilityProvider.CAPABILITY, null)
                .setDataFromNBT(event.getOriginal().getCapability(CapabilityProvider.CAPABILITY, null)
                        .getDataAsNBT());
        System.out.println("RE-Attached Capability");
    }

    @SubscribeEvent
    public void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return;
        }
        PacketHandler.updatePlayerData((EntityPlayerMP) event.getEntity());
    }
}
