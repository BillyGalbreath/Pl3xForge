package net.pl3x.forge.core.util;

import net.pl3x.forge.core.scheduler.Pl3xRunnable;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;

public class TPSTracker extends Pl3xRunnable {
    private long lastTick;
    private final Deque<Long> tickIntervals;
    private final int resolution = 1200;

    public TPSTracker() {
        lastTick = System.currentTimeMillis();
        tickIntervals = new ArrayDeque<>(Collections.nCopies(resolution, 50L));
    }

    @Override
    public void run() {
        long curr = System.currentTimeMillis();
        long delta = curr - lastTick;
        lastTick = curr;
        tickIntervals.removeFirst();
        tickIntervals.addLast(delta);
    }

    public double getTPS() {
        int base = 0;
        for (long delta : tickIntervals) {
            base += delta;
        }
        double tps = 1000D / ((double) base / resolution);
        return tps > 20D ? 20D : tps;
    }
}
