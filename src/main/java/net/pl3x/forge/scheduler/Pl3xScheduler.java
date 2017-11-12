package net.pl3x.forge.scheduler;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Pl3xScheduler {
    public static final Pl3xScheduler INSTANCE = new Pl3xScheduler();

    private final AtomicInteger ids = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Pl3xTask> tasks = new ConcurrentHashMap<>();
    private long currentTick = Long.MIN_VALUE;

    private int nextId() {
        return ids.incrementAndGet();
    }

    public Pl3xTask runTaskLater(Runnable runnable, long delay) {
        if (delay < 0L) {
            delay = 0;
        }
        Pl3xTask task = new Pl3xTask(runnable, nextId(), -1L);
        handle(task, delay);
        return task;
    }

    public Pl3xTask runTaskTimer(Runnable runnable, long delay, long period) {
        if (delay < 0L) {
            delay = 0;
        }
        if (period == 0L) {
            period = 1L;
        } else if (period < -1L) {
            period = -1L;
        }
        Pl3xTask task = new Pl3xTask(runnable, nextId(), period);
        handle(task, delay);
        return task;
    }

    public void cancelTask(int taskId) {
        Pl3xTask task = tasks.get(taskId);
        if (task != null) {
            task.setPeriod(-1);
            task.cancel();
        }
        tasks.remove(taskId);
    }

    public <T> Future<T> callSyncMethod(Callable<T> task) {
        Pl3xFuture<T> future = new Pl3xFuture<>(task, nextId());
        handle(future, 0L);
        return future;
    }

    private Pl3xTask handle(Pl3xTask task, long delay) {
        task.setNextRun(currentTick + delay);
        tasks.put(task.getTaskId(), task);
        return task;
    }

    public void tick() {
        currentTick++;

        Iterator<Map.Entry<Integer, Pl3xTask>> iter = tasks.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, Pl3xTask> entry = iter.next();
            if (entry.getKey() > 0 && entry.getValue().getNextRun() <= currentTick) {
                entry.getValue().run();
                if (entry.getValue().getPeriod() > 0) {
                    entry.getValue().setNextRun(currentTick + entry.getValue().getPeriod());
                } else {
                    iter.remove();
                }
            }
        }
    }
}
