package net.pl3x.forge.motd;

import net.pl3x.forge.scheduler.Pl3xRunnable;

public class MOTDTask extends Pl3xRunnable {
    @Override
    public void run() {
        MOTDCache.INSTANCE.update();
    }
}
