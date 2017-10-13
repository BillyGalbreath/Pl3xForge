package net.pl3x.forge.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.Location;

public class TeleportRequestTPA extends TeleportRequest {
    public TeleportRequestTPA(EntityPlayerMP requester, EntityPlayerMP target) {
        super(requester, target);
    }

    @Override
    protected void teleport() {
        Teleport.teleport(getRequester(), new Location(getTarget()));
    }
}
