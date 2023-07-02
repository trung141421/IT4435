package trung.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadpoolWorker {
    public ExecutorService executor;
    public TaskScheduler scheduler;
    private static ThreadpoolWorker ins = null;

    private ThreadpoolWorker(){
        executor = Executors.newFixedThreadPool(16);
        scheduler = new TaskScheduler(16);
    }

    public static ThreadpoolWorker instance(){
        if(ins == null){
            ins = new ThreadpoolWorker();
        }
        return ins;
    }
}
