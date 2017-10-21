package net.pl3x.forge.scheduler;

public class Pl3xTask implements Runnable {
    private final Runnable task;
    private final int id;
    private long nextRun;
    private long period;
    private boolean cancelled = false;

    Pl3xTask(Runnable task, int id, long period) {
        this.task = task;
        this.id = id;
        this.period = period;
    }

    @Override
    public void run() {
        if (!cancelled) {
            task.run();
        }
    }

    public int getTaskId() {
        return id;
    }

    long getPeriod() {
        return period;
    }

    void setPeriod(long period) {
        this.period = period;
    }

    long getNextRun() {
        return nextRun;
    }

    void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    public void cancel() {
        cancelled = true;
        if (period > 0) {
            Pl3xScheduler.INSTANCE.cancelTask(id);
        }
    }
}
