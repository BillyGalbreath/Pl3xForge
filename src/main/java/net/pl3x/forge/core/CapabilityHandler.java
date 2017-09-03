package net.pl3x.forge.core;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.core.data.PlayerDataProvider;

public class CapabilityHandler {
    public static final ResourceLocation PLAYER_DATA_CAPABILITY = new ResourceLocation(Pl3xCore.modId, "playerdata");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer)) {
            return;
        }
        event.addCapability(PLAYER_DATA_CAPABILITY, new PlayerDataProvider());
    }
}
