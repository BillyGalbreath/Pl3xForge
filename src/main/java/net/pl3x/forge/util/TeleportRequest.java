package net.pl3x.forge.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.scheduler.Pl3xRunnable;

public abstract class TeleportRequest {
    private final EntityPlayerMP requester;
    private final EntityPlayerMP target;
    private final TeleportRequestTimeout timeoutTask;

    public TeleportRequest(EntityPlayerMP requester, EntityPlayerMP target) {
        this.requester = requester;
        this.target = target;

        this.timeoutTask = new TeleportRequestTimeout(this);
        this.timeoutTask.runTaskLater(30 * 20);
    }

    public EntityPlayerMP getRequester() {
        return requester;
    }

    public EntityPlayerMP getTarget() {
        return target;
    }

    public void accept() {
        teleport();

        Lang.send(target, Lang.INSTANCE.data.TELEPORT_REQUEST_ACCEPT_TARGET
                .replace("{requester}", requester.getName()));
        Lang.send(requester, Lang.INSTANCE.data.TELEPORT_REQUEST_ACCEPT_REQUESTER
                .replace("{target}", target.getName()));

        cancel();
    }

    public void deny() {
        Lang.send(target, Lang.INSTANCE.data.TELEPORT_REQUEST_DENIED_TARGET
                .replace("{requester}", requester.getName()));
        Lang.send(requester, Lang.INSTANCE.data.TELEPORT_REQUEST_DENIED_REQUESTER
                .replace("{target}", target.getName()));

        cancel();
    }

    protected abstract void teleport();

    public void cancel() {
        Teleport.TELEPORT_REQUESTS.remove(requester.getUniqueID());
        try {
            timeoutTask.cancel();
        } catch (IllegalStateException ignore) {
        }
    }

    private class TeleportRequestTimeout extends Pl3xRunnable {
        private final TeleportRequest request;

        TeleportRequestTimeout(TeleportRequest request) {
            this.request = request;
        }

        @Override
        public void run() {
            request.cancel();

            String message = Lang.INSTANCE.data.TELEPORT_REQUEST_TIMED_OUT;

            Lang.send(requester, message);
            Lang.send(target, message);
        }
    }
}
