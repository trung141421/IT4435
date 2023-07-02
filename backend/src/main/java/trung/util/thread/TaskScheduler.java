package trung.util.thread;
import trung.util.log.DebugLogger;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskScheduler {
    private static AtomicInteger schedulerId = new AtomicInteger(0);
    private final ScheduledThreadPoolExecutor taskScheduler;
    private final String serviceName;

    public TaskScheduler(int threadPoolSize)
    {
        serviceName = (new StringBuilder("TaskScheduler-")).append(schedulerId.getAndIncrement()).toString();
        taskScheduler = new ScheduledThreadPoolExecutor(threadPoolSize);
    }

    public void destroy()
    {
        List awaitingExecution = taskScheduler.shutdownNow();
        DebugLogger.info((new StringBuilder(String.valueOf(serviceName))).append(" stopping. Tasks awaiting execution: ").append(awaitingExecution.size()).toString());
    }


    public ScheduledFuture schedule(Runnable task, int delay, TimeUnit unit)
    {
        DebugLogger.debug((new StringBuilder("Task scheduled: ")).append(task).append(", ").append(delay).append(" ").append(unit).toString());
        return taskScheduler.schedule(task, delay, unit);
    }

    public ScheduledFuture scheduleAtFixedRate(Runnable task, int initialDelay, int period, TimeUnit unit)
    {
        return taskScheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public boolean remove(Runnable task)
    {
        return taskScheduler.remove(task);
    }

    public void resizeThreadPool(int threadPoolSize)
    {
        taskScheduler.setCorePoolSize(threadPoolSize);
    }

    public int getThreadPoolSize()
    {
        return taskScheduler.getCorePoolSize();
    }
}
