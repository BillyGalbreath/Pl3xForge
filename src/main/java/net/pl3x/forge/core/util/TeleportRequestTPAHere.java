package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.core.Location;

public class TeleportRequestTPAHere extends TeleportRequest {
    public TeleportRequestTPAHere(EntityPlayerMP requester, EntityPlayerMP target) {
        super(requester, target);
    }

    @Override
    protected void teleport() {
        Teleport.teleport(getTarget(), new Location(getRequester()));
    }
}
