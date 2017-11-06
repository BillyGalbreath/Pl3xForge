package net.pl3x.forge.util.teleport;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.util.Location;

public class TeleportRequestTPAHere extends TeleportRequest {
    public TeleportRequestTPAHere(EntityPlayerMP requester, EntityPlayerMP target) {
        super(requester, target);
    }

    @Override
    protected void teleport() {
        Teleport.teleport(getTarget(), new Location(getRequester()));
    }
}
