package net.pl3x.forge.util;

import net.pl3x.forge.scheduler.Pl3xRunnable;

public class TPSTracker extends Pl3xRunnable {
    private static final int TPS = 20;
    private static final long SEC_IN_NANO = 1000000000;

    public final RollingAverage tps1 = new RollingAverage(60);
    public final RollingAverage tps5 = new RollingAverage(60 * 5);
    public final RollingAverage tps15 = new RollingAverage(60 * 15);

    private boolean firstRun = true;
    private long lastTick = 0;
    private double tps = 20;

    @Override
    public void run() {
        if (firstRun) {
            firstRun = false;
            lastTick = System.nanoTime();
            return;
        }

        long now = System.nanoTime();
        long diff = now - lastTick;
        tps = 1E9 / diff * TPS;
        lastTick = now;

        tps1.add(tps, diff);
        tps5.add(tps, diff);
        tps15.add(tps, diff);
    }

    public double getTPS() {
        return tps > 20 ? 20 : tps;
    }

    public static class RollingAverage {
        private final int size;
        private long time;
        private double total;
        private int index = 0;
        private final double[] samples;
        private final long[] times;

        RollingAverage(int size) {
            this.size = size;
            this.time = size * SEC_IN_NANO;
            this.total = TPS * SEC_IN_NANO * size;
            this.samples = new double[size];
            this.times = new long[size];
            for (int i = 0; i < size; i++) {
                this.samples[i] = TPS;
                this.times[i] = SEC_IN_NANO;
            }
        }

        public void add(double x, long t) {
            time -= times[index];
            total -= samples[index] * times[index];
            samples[index] = x;
            times[index] = t;
            time += t;
            total += x * t;
            if (++index == size) {
                index = 0;
            }
        }

        public double getAverage() {
            double avg = total / time;
            return avg > 20 ? 20 : avg;
        }
    }
}
